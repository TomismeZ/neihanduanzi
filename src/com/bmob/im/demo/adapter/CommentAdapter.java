package com.bmob.im.demo.adapter;

import java.util.List;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.Comment;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private List<Comment> comments;
	Context context;
	public CommentAdapter(Context context, List<Comment> comments) {
		// TODO Auto-generated constructor stub
		this.comments = comments;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_comment, null);
			holder = new ViewHolder();// 内存中的一个地址
			holder.imgViewproFile = (ImageView) convertView
					.findViewById(R.id.comment_profile_img);
			holder.txtContent = (TextView) convertView
					.findViewById(R.id.comment_content);
			holder.txtCreateTime = (TextView) convertView
					.findViewById(R.id.comment_create_time);
			holder.txtProFileNick = (TextView) convertView
					.findViewById(R.id.comment_profile_nick);
			holder.favorite=(TextView) convertView.findViewById(R.id.favorite);
			holder.bury=(TextView) convertView.findViewById(R.id.bury);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
	
			final Comment comment = comments.get(position);
			Log.i("CommentAdapter", comment.getUserName().toString());
			if (comment != null) {
				holder.txtProFileNick.setText(comment.getUserName());
				holder.txtContent.setText(comment.getText());
				holder.favorite.setText(comment.getDiggCount()+"");
				holder.bury.setText(comment.getBuryCount()+"");
			} else {
				holder.txtProFileNick.setText("未知用户");
				holder.txtContent.setText("加载评论失败！");
			}

//			Date data = new Date(comment.getCreateTime());
			holder.txtCreateTime.setText(comment.getCreateTime());
			holder.imgViewproFile.setAdjustViewBounds(true);
			holder.imgViewproFile.setMaxWidth(100);
			holder.imgViewproFile.setMaxHeight(100);
			BitmapUtils bitmapUtils = new BitmapUtils(context);
			bitmapUtils
					.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
			bitmapUtils.display(holder.imgViewproFile, comments.get(position)
					.getUserProfileImageUrl(), new BitmapLoadCallBack<View>() {

				@Override
				public void onLoadCompleted(View arg0, String arg1,
						Bitmap arg2, BitmapDisplayConfig arg3,
						BitmapLoadFrom arg4) {
					// TODO Auto-generated method stub

					Bitmap bitmap = BitmapUtil.getRoundedCornerBitmap(arg2);

					((ImageView) arg0).setImageBitmap(bitmap);

				}

				@Override
				public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
					// TODO Auto-generated method stub

				}
			});
		holder.favorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				comment.setDiggCount(comment.getDiggCount()+1);
				comment.update(context);
				notifyDataSetChanged();
			}
		});
		holder.bury.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				comment.setBuryCount(comment.getBuryCount()+1);
				comment.update(context);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	class ViewHolder {	
		private ImageView imgViewproFile;
		private TextView txtProFileNick;
		private TextView txtCreateTime;
		private TextView txtContent;
		private TextView favorite;
		private TextView bury;
	}

}
