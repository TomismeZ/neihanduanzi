package com.bmob.im.demo.adapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.AllInfo;
import com.bmob.im.demo.bean.Joke;
import com.bmob.im.demo.ui.CommentActivity;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//adapterview ---> viewpager listview
//viewpager--->pagerAdapter/fragmentPagerAdapter
//listview--->baseAdapter



public class AllInfoListAdapter extends BaseAdapter{
	List<AllInfo> infos;
	Context context;
	ImageView playResume;
	private MediaPlayer player;
	// ��ǰ���ڲ���item��position��-1 ��ʾ��ǰû��item�ڲ���
	private int currentPosition = -1;
	public AllInfoListAdapter(List<AllInfo> infos,Context context){
		this.infos=infos;
		this.context=context;
		player = new MediaPlayer();
		player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
			}
		});
	}
	
	//����
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();//20
	}

	//��ȡĳһ�У����Բ�д��
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	//��ȡĳһ�е�id�����Բ�д��
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	//ÿһ�еĲ��֣����ӣ�������  30   ��ǰ��Ļ��ʾ �ļ��� ��ִ���ļ��е� getvieww
	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder  holder=null;
//		converView  ���ݸ���
		if (convertView==null) {
			//context=activity/application
			convertView= View.inflate(context, R.layout.item_listview_all, null);
			
			holder=new ViewHolder();//�ڴ��е�һ����ַ
			playResume=(ImageView) convertView.findViewById(R.id.play_resume);
			holder.avatar=(ImageView) convertView.findViewById(R.id.avatar);
			holder.userName=(TextView) convertView.findViewById(R.id.username);
			holder.content=(TextView) convertView.findViewById(R.id.content);
			holder.bury=(TextView) convertView.findViewById(R.id.bury);
			holder.comment=(TextView) convertView.findViewById(R.id.comment);
			holder.farorite=(TextView) convertView.findViewById(R.id.favorite);
			holder.share=(TextView) convertView.findViewById(R.id.share);
			holder.image = (ImageView) convertView.findViewById(R.id.image_content);
			holder.cover = (ImageView) convertView.findViewById(R.id.cover);
			holder.surfaceView = (SurfaceView) convertView
					.findViewById(R.id.surface_view);
			holder.frameLayout=(FrameLayout) convertView.findViewById(R.id.frame_layout);
			convertView.setTag(holder);
			 			
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final AllInfo allInfo=infos.get(position);
		holder.content.setText(allInfo.getContent());
		holder.userName.setText(allInfo.getUserName());
		holder.farorite.setText(allInfo.getFavoriteCount()+"");
		holder.bury.setText(allInfo.getBuryCount()+"");
		holder.comment.setText(allInfo.getCommentCount()+"");
		holder.share.setText(allInfo.getShareCount()+"");
		if(allInfo.getLargeImageUri()==null)
		{
			holder.image.setVisibility(View.VISIBLE);
			Bitmap bm = BitmapFactory.decodeFile(allInfo.getMiddleImageUri());						
			holder.image.setAdjustViewBounds(true);
			holder.image.setMaxWidth(150);
			holder.image.setMaxHeight(150);
			holder.image.setImageBitmap(bm);
//			Picasso.with(context).load(allInfo.getMiddleImageUri()).placeholder(R.drawable.ic_launcher).into(holder.image);	
		}else{
			// �޸ĈDƬ
			holder.frameLayout.setVisibility(View.GONE);
			holder.image.setVisibility(View.VISIBLE);
			String uri = "http://pb2.pstatp.com/"
				+ allInfo.getMiddleImageUri();
		Picasso.with(context).load(uri).placeholder(R.drawable.ic_launcher)
		.into(holder.image);
		}
		if(allInfo.getVideoUrl()!=null&&allInfo.getMiddleImageUri()==null){			
			holder.frameLayout.setVisibility(View.VISIBLE);
			ViewGroup.LayoutParams params = holder.cover.getLayoutParams();
			params.width = allInfo.getWidth();
			params.height = allInfo.getHeight();
			holder.cover.requestLayout();
			holder.surfaceView.setLayoutParams(params);		
			Picasso.with(context).load(allInfo.getCoverUrl())
			.placeholder(R.drawable.ic_launcher).into(holder.cover);
		}
//		if(allInfo.getVideoUrl()!=null&&allInfo.getCoverUrl()==null){
//			File file = new File(allInfo.getVideoUrl());
//			MediaMetadataRetriever mmr = new MediaMetadataRetriever();// ʵ����MediaMetadataRetriever����
//			mmr.setDataSource(file.getAbsolutePath());
//			Bitmap bitmap = mmr.getFrameAtTime();// �����Ƶ��һ֡��Bitmap����
//			String duration = mmr
//					.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);// ʱ��(����)
//			Log.d("ddd", "duration==" + duration);
//			int int_duration = Integer.parseInt(duration);
//			if (int_duration > 300000) {
//				Toast.makeText(context, "��Ƶʱ���ѳ���5���ӣ�������ѡ��",
//						Toast.LENGTH_SHORT).show();
//			}
//
//			holder.cover.setImageBitmap(bitmap);
//		}
		//ͼƬͷ��
		//��ַ  ���߳�  ����  bitmap handler ��ʾͼƬ
		//�Ż�ͼƬ   �ڴ�  ���棨������ ����      
		//������xutils  picasso  fresco
		
		BitmapUtils bitmapUtils=new BitmapUtils(context);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
		bitmapUtils.display(holder.avatar,allInfo.getUserAvatar(),new BitmapLoadCallBack<View>() {

			@Override
			public void onLoadCompleted(View arg0, String arg1, Bitmap arg2,
					BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
				// TODO Auto-generated method stub
							
				Bitmap bitmap=BitmapUtil.getRoundedCornerBitmap(arg2);
 
				((ImageView)arg0).setImageBitmap(bitmap);
				
				
			}

			@Override
			public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
				// TODO Auto-generated method stub
				
			}
		});
		//�����¼�
		holder.farorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int favoritecount=allInfo.getFavoriteCount();
				allInfo.setFavoriteCount(favoritecount+1);
				
				allInfo.update(context);
				Toast.makeText(context, "���޳ɹ�", Toast.LENGTH_SHORT).show();
				notifyDataSetChanged();
				
			}
		});
		//�����¼�
		holder.comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub			
				Intent intent=new Intent(context,CommentActivity.class);
				intent.putExtra("allInfo", allInfo);
				intent.putExtra("type", 4);					
				context.startActivity(intent);
			}
		});
		//��
		holder.bury.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int burrcount=allInfo.getBuryCount();
				allInfo.setBuryCount(burrcount+1);
				allInfo.update(context);
				notifyDataSetChanged();
				
			}
		});
		///����
		holder.share.setOnClickListener(new ShareClickListener());
		
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
				player.setDataSource(allInfo.getVideoUrl());
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
				playResume.setVisibility(View.GONE);
				
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
		return convertView;
	}
	
	//��ķ��� ����¼�
	class ShareClickListener  implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			showShare();
		}
		
	}
	
	class ViewHolder {		
		public ImageView avatar;
		public TextView userName;
		public TextView content;
		public TextView farorite;
		public TextView bury;
		public TextView comment;
		public TextView share;
		public ImageView image;
		SurfaceView surfaceView;
		ImageView cover;
		FrameLayout frameLayout;
	}

	
	private void showShare() {
		 ShareSDK.initSDK(context);
		 OnekeyShare oks = new OnekeyShare();
		 //�ر�sso��Ȩ
		 oks.disableSSOWhenAuthorize(); 

		// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		 oks.setTitle( "xxx");
		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		 oks.setText("���Ƿ����ı�");
		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		 oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		 oks.setUrl("http://sharesdk.cn");
		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		 oks.setComment("���ǲ��������ı�");
		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		 oks.setSite("http://www.baidu.com");
		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		 oks.setSiteUrl("http://sharesdk.cn");

		// ��������GUI
		 oks.show(context);
		 }
}
