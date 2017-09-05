package com.bmob.im.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.ImageListAdapter.CommentClickListener;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.bean.Video;
import com.bmob.im.demo.ui.CommentActivity;
import com.bmob.im.demo.ui.OtherUserInfoActivity;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/9 0009.
 */
public class VideoListAdapter extends BaseAdapter {

	private List<Video> videos;
	private Context context;
	private LayoutInflater inflater;
	private MediaPlayer player;
	// ��ǰ���ڲ���item��position��-1 ��ʾ��ǰû��item�ڲ���
	private int currentPosition = -1;

	public VideoListAdapter(List<Video> list, Context context) {
		this.videos = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
		player = new MediaPlayer();
		player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
			}
		});
	}

	@Override
	public int getCount() {
		return videos.size();
	}

	@Override
	public Object getItem(int position) {
		return videos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_listview_video,
					parent, false);
			holder = new ViewHolder();
			holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
			holder.userName = (TextView) convertView
					.findViewById(R.id.username);
			holder.title = (TextView) convertView.findViewById(R.id.content);
			holder.cover = (ImageView) convertView.findViewById(R.id.cover);
			holder.surfaceView = (SurfaceView) convertView
					.findViewById(R.id.surface_view);
			holder.bury = (TextView) convertView.findViewById(R.id.bury);
			holder.comment = (TextView) convertView.findViewById(R.id.comment);
			holder.farorite = (TextView) convertView
					.findViewById(R.id.favorite);
			holder.share = (TextView) convertView.findViewById(R.id.share);
			holder.playResume = (ImageView) convertView
					.findViewById(R.id.play_resume);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Video videoBean = videos.get(position);
		ViewGroup.LayoutParams params = holder.cover.getLayoutParams();
		holder.userName.setText(videoBean.getUserName());
		holder.farorite.setText(videoBean.getFavoriteCount() + "");
		holder.bury.setText(videoBean.getBuryCount() + "");
		holder.comment.setText(videoBean.getCommentCount() + "");
		holder.share.setText(videoBean.getShareCount() + "");
		params.width = videoBean.getWidth();
		params.height = videoBean.getHeight();
		holder.cover.requestLayout();
		holder.surfaceView.setLayoutParams(params);
		if(videoBean.getCoverUrl()==null){
			File file = new File(videoBean.getVideoUrl());
			MediaMetadataRetriever mmr = new MediaMetadataRetriever();// ʵ����MediaMetadataRetriever����
			mmr.setDataSource(file.getAbsolutePath());
			Bitmap bitmap = mmr.getFrameAtTime();// �����Ƶ��һ֡��Bitmap����
			String duration = mmr
					.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);// ʱ��(����)
			Log.d("ddd", "duration==" + duration);
			int int_duration = Integer.parseInt(duration);
			if (int_duration > 300000) {
				Toast.makeText(context, "��Ƶʱ���ѳ���5���ӣ�������ѡ��",
						Toast.LENGTH_SHORT).show();
			}

			holder.cover.setImageBitmap(bitmap);
		}
		Picasso.with(context).load(videoBean.getCoverUrl())
				.placeholder(R.drawable.ic_launcher).into(holder.cover);
		// Picasso.with(context).load(videoBean.getCoverUrl()).into(holder.cover);
		holder.title.setText(videoBean.getTitle());

		// �����¼�
		holder.comment.setOnClickListener(new CommentClickListener(position));
		// �����¼�
		holder.farorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!videoBean.isFirst()) {
					int favoriteCount = videoBean.getFavoriteCount();
					videoBean.setFavoriteCount(favoriteCount + 1);
					videoBean.update(context);
					Toast.makeText(context, "���޳ɹ�", Toast.LENGTH_SHORT).show();
					notifyDataSetChanged();
					videoBean.setFirst(true);
				}else{
					Toast.makeText(context, "���Ѿ����޹�", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//���¼�
		holder.bury.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!videoBean.isFirst()) {
					int buryCount = videoBean.getBuryCount();
					videoBean.setFavoriteCount(buryCount + 1);
					videoBean.update(context);
					Toast.makeText(context, "�ȳɹ�", Toast.LENGTH_SHORT).show();
					notifyDataSetChanged();
					videoBean.setFirst(true);
				}else{
					Toast.makeText(context, "���Ѿ��ȹ�", Toast.LENGTH_SHORT).show();
				}
			}
		});
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
		bitmapUtils.display(holder.avatar, videoBean.getUserAvatar(),
				new BitmapLoadCallBack<View>() {

					@Override
					public void onLoadCompleted(View arg0, String arg1,
							Bitmap arg2, BitmapDisplayConfig arg3,
							BitmapLoadFrom arg4) {
						// TODO Auto-generated method stub

						Bitmap bitmap = BitmapUtil.getRoundedCornerBitmap(arg2);

						((ImageView) arg0).setImageBitmap(bitmap);

					}

					@Override
					public void onLoadFailed(View arg0, String arg1,
							Drawable arg2) {
						// TODO Auto-generated method stub

					}
				});

		Object tag = holder.cover.getTag();
		// �ж����ڲ��ŵ���Ƶ��item�Ƿ񻬳���Ļ
		if (tag != null) {
			Integer tag1 = (Integer) tag;
			if (tag1 == currentPosition && tag1 != position) {
				player.stop();
				currentPosition = -1;
			}
		}

		holder.cover.setTag(position);

		if (currentPosition == position) {
			holder.surfaceView.setVisibility(View.VISIBLE);
			holder.cover.setVisibility(View.INVISIBLE);

			player.reset();

			try {
				player.setDisplay(holder.surfaceView.getHolder());
				player.setDataSource(videoBean.getVideoUrl());
				player.prepareAsync();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			holder.cover.setVisibility(View.VISIBLE);
			holder.surfaceView.setVisibility(View.INVISIBLE);
		}

		// ͼƬ�ĵ���¼�
		holder.cover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				holder.playResume.setVisibility(View.GONE);
				Integer index = (Integer) v.getTag();
				// ���õ�ǰ����λ��
				currentPosition = index;
				if (player != null && player.isPlaying()) {
					player.stop();

				}
				// ����getview����
				notifyDataSetChanged();
			}
		});

		holder.avatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Video video = videos.get(position);

				final User currentUser = BmobUser.getCurrentUser(context,
						User.class);

				Intent intent = new Intent(context, OtherUserInfoActivity.class);
				intent.putExtra("user", currentUser);
				intent.putExtra("video", video);
				intent.putExtra("type", 3);
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	class ViewHolder {
		public ImageView avatar;
		public TextView userName;
		SurfaceView surfaceView;
		ImageView cover;
		TextView title;
		public TextView farorite;
		public TextView bury;
		public TextView comment;
		public TextView share;
		public ImageView playResume;
	}

	class CommentClickListener implements OnClickListener {
		int p;

		public CommentClickListener(int p) {
			this.p = p;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Video video = videos.get(p);
			Intent intent = new Intent(context, CommentActivity.class);
			intent.putExtra("video", video);
			intent.putExtra("type", 3);
			context.startActivity(intent);
		}

	}

}
