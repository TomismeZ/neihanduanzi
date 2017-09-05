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
	// 当前正在播放item的position，-1 表示当前没有item在播放
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
	
	//行数
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();//20
	}

	//获取某一行（可以不写）
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	//获取某一行的id（可以不写）
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	//每一行的布局（样子）和内容  30   当前屏幕显示 哪几行 就执行哪几行的 getvieww
	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder  holder=null;
//		converView  内容复用
		if (convertView==null) {
			//context=activity/application
			convertView= View.inflate(context, R.layout.item_listview_all, null);
			
			holder=new ViewHolder();//内存中的一个地址
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
			// 修改圖片
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
//			MediaMetadataRetriever mmr = new MediaMetadataRetriever();// 实例化MediaMetadataRetriever对象
//			mmr.setDataSource(file.getAbsolutePath());
//			Bitmap bitmap = mmr.getFrameAtTime();// 获得视频第一帧的Bitmap对象
//			String duration = mmr
//					.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);// 时长(毫秒)
//			Log.d("ddd", "duration==" + duration);
//			int int_duration = Integer.parseInt(duration);
//			if (int_duration > 300000) {
//				Toast.makeText(context, "视频时长已超过5分钟，请重新选择",
//						Toast.LENGTH_SHORT).show();
//			}
//
//			holder.cover.setImageBitmap(bitmap);
//		}
		//图片头像
		//网址  子线程  联网  bitmap handler 显示图片
		//优化图片   内存  缓存（流量） 网络      
		//第三方xutils  picasso  fresco
		
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
		//点赞事件
		holder.farorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int favoritecount=allInfo.getFavoriteCount();
				allInfo.setFavoriteCount(favoritecount+1);
				
				allInfo.update(context);
				Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
				notifyDataSetChanged();
				
			}
		});
		//评论事件
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
		//踩
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
		///分享
		holder.share.setOnClickListener(new ShareClickListener());
		
		Object tag = holder.cover.getTag();
		// 判断正在播放的视频的item是否滑出屏幕
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

		// 图片的点击事件
		holder.cover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {				
				playResume.setVisibility(View.GONE);
				
				Integer index = (Integer) v.getTag();
				// 设置当前播放位置
				currentPosition = index;
				if (player != null && player.isPlaying()) {
					player.stop();

				}
				// 重走getview方法
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	
	//你的分享 点击事件
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
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 

		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle( "xxx");
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("我是分享文本");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://sharesdk.cn");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite("http://www.baidu.com");
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		 oks.show(context);
		 }
}
