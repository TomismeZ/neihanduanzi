package com.bmob.im.demo.adapter;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.Joke;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.CommentActivity;
import com.bmob.im.demo.ui.OtherUserInfoActivity;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//adapterview ---> viewpager listview
//viewpager--->pagerAdapter/fragmentPagerAdapter
//listview--->baseAdapter



public class JokeListAdapter extends BaseAdapter{
	List<Joke> jokes;
	Context context;
	public JokeListAdapter(List<Joke> jokes,Context context){
		this.jokes=jokes;
		this.context=context;
	}
	
	//行数
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jokes.size();//20
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
			convertView= View.inflate(context, R.layout.item_listview_joke, null);
			
			holder=new ViewHolder();//内存中的一个地址
			
			holder.avatar=(ImageView) convertView.findViewById(R.id.avatar);
			holder.userName=(TextView) convertView.findViewById(R.id.username);
			holder.content=(TextView) convertView.findViewById(R.id.content);
			holder.bury=(TextView) convertView.findViewById(R.id.bury);
			holder.comment=(TextView) convertView.findViewById(R.id.comment);
			holder.farorite=(TextView) convertView.findViewById(R.id.favorite);
			holder.share=(TextView) convertView.findViewById(R.id.share);
			
			
			convertView.setTag(holder);
			 
			
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.content.setText(jokes.get(position).getContent());
		holder.userName.setText(jokes.get(position).getUserName());
		holder.farorite.setText(jokes.get(position).getFavoriteCount()+"");
		holder.bury.setText(jokes.get(position).getBuryCount()+"");
		holder.comment.setText(jokes.get(position).getCommentCount()+"");
		holder.share.setText(jokes.get(position).getShareCount()+"");
		//图片头像
		//网址  子线程  联网  bitmap handler 显示图片
		//优化图片   内存  缓存（流量） 网络      
		//第三方xutils  picasso  fresco
		
		BitmapUtils bitmapUtils=new BitmapUtils(context);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
		bitmapUtils.display(holder.avatar, jokes.get(position).getUserAvatar(),new BitmapLoadCallBack<View>() {

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
				Joke joke=jokes.get(position);
				int favoritecount=joke.getFavoriteCount();
				joke.setFavoriteCount(favoritecount+1);
				
				joke.update(context, new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
						notifyDataSetChanged();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(context, "点赞失败", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
		});
		//评论事件
		holder.comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Joke joke=jokes.get(position);
				Intent intent=new Intent(context,CommentActivity.class);
				intent.putExtra("joke", joke);
				intent.putExtra("type", 1);
				
				Toast.makeText(context, "跳转到评论节目！", Toast.LENGTH_SHORT).show();
				context.startActivity(intent);
			}
		});
		//踩
		holder.bury.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Joke joke1=jokes.get(position);
				int burrcount=joke1.getBuryCount();
				joke1.setBuryCount(burrcount+1);
				joke1.update(context, new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(context, "踩成功", Toast.LENGTH_SHORT).show();
						notifyDataSetChanged();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(context, "踩失败", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
		});
		///分享
		holder.share.setOnClickListener(new ShareClickListener());
		
		holder.avatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Joke joke = jokes.get(position);
				
				final User currentUser = BmobUser.getCurrentUser(context, User.class);
							
				Intent intent=new Intent(context,OtherUserInfoActivity.class);
				intent.putExtra("user", currentUser);
				intent.putExtra("joke", joke);
				intent.putExtra("type", 1);
				context.startActivity(intent);
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
