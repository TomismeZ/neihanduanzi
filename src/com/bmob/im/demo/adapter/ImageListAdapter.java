package com.bmob.im.demo.adapter;

import java.util.List;


import cn.bmob.v3.BmobUser;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.bmob.im.demo.R;

import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.bean.Video;

import com.bmob.im.demo.ui.CommentActivity;
import com.bmob.im.demo.ui.OtherUserInfoActivity;
import com.bmob.im.demo.ui.ShowImageActivity;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageListAdapter extends BaseAdapter {

	List<ImageInfo> images;
	Context context;
	private boolean isFirst = true;
	public ImageListAdapter(List<ImageInfo> images, Context context) {
		this.images = images;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			// context=activity/application
			convertView = View.inflate(context, R.layout.item_listview_image,
					null);

			holder = new ViewHolder();// 内存中的一个地址

			holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
			holder.userName = (TextView) convertView
					.findViewById(R.id.username);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.bury = (TextView) convertView.findViewById(R.id.bury);
			holder.comment = (TextView) convertView.findViewById(R.id.comment);
			holder.farorite = (TextView) convertView.findViewById(R.id.favorite);
			holder.share = (TextView) convertView.findViewById(R.id.share);
			holder.image = (ImageView) convertView
					.findViewById(R.id.image_content);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}


		
			// 修改 內容
			holder.content
					.setText(images.get(position).getContent());
			holder.userName.setText(images.get(position).getUserName());
			
			
			holder.farorite.setText(images.get(position).getFavoriteCount()+"");
			holder.comment
					.setText(images.get(position).getCommentCount()+"");
			holder.bury
					.setText(images.get(position).getBuryCount()+"");
			holder.share
					.setText(images.get(position).getShareCount()+"");
			BitmapUtils bitmapUtils = new BitmapUtils(context);
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
			bitmapUtils.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
			bitmapUtils.display(holder.avatar,images.get(position).getUserAvatar(),
					new BitmapLoadCallBack<View>() {

						@Override
						public void onLoadCompleted(View arg0, String arg1,
								Bitmap arg2, BitmapDisplayConfig arg3,
								BitmapLoadFrom arg4) {
							// TODO Auto-generated method stub

							Bitmap bitmap = BitmapUtil
									.getRoundedCornerBitmap(arg2);

							((ImageView) arg0).setImageBitmap(bitmap);

						}

						@Override
						public void onLoadFailed(View arg0, String arg1,
								Drawable arg2) {
							// TODO Auto-generated method stub

						}
					});
			
			if(images.get(position).getLargeImageUri()==null||images.get(position).getLargeImageUri().equals(""))
			{			
				Bitmap bm = BitmapFactory.decodeFile(images.get(position).getMiddleImageUri());						
				holder.image.setAdjustViewBounds(true);
				holder.image.setMaxWidth(260);
				holder.image.setMaxHeight(300);
				holder.image.setImageBitmap(bm);
			}else{
			// 修改圖片
				String uri = "http://pb2.pstatp.com/"
					+ images.get(position).getMiddleImageUri();
			Picasso.with(context).load(uri).placeholder(R.drawable.ic_launcher)
			.into(holder.image);
			}
			
			// 你的圖片的點擊事件
			holder.image.setOnClickListener(new MyListener(position));
			// /分享
			holder.share.setOnClickListener(new ShareClickListener());
			
			// 点赞事件
			holder.farorite.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isFirst) {
						ImageInfo image=images.get(position);
						int favoriteCount = image.getFavoriteCount();
						image.setFavoriteCount(favoriteCount + 1);
						image.update(context);
						Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
						notifyDataSetChanged();
						isFirst=false;
					}else{
						Toast.makeText(context, "您已经点赞过", Toast.LENGTH_SHORT).show();
					}
				}
			});
			//踩事件
			holder.bury.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isFirst) {
						ImageInfo image=images.get(position);
						int buryCount = image.getBuryCount();
						image.setFavoriteCount(buryCount + 1);
						image.update(context);
						Toast.makeText(context, "踩成功", Toast.LENGTH_SHORT).show();
						notifyDataSetChanged();
						isFirst=false;
					}else{
						Toast.makeText(context, "您已经踩过", Toast.LENGTH_SHORT).show();
					}
				}
			});
			//评论事件
			holder.comment.setOnClickListener(new CommentClickListener(position));
			holder.avatar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ImageInfo image = images.get(position);
					
					final User currentUser = BmobUser.getCurrentUser(context, User.class);
								
					Intent intent=new Intent(context,OtherUserInfoActivity.class);
					intent.putExtra("user", currentUser);
					intent.putExtra("image", image);
					intent.putExtra("type", 2);
					context.startActivity(intent);
				}
			});
		return convertView;
	}
	class CommentClickListener implements OnClickListener{
		int p;

		public CommentClickListener(int p) {
			this.p = p;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ImageInfo image=images.get(p);
			Intent intent=new Intent(context,CommentActivity.class);
			intent.putExtra("image", image);
			intent.putExtra("type", 2);
			context.startActivity(intent);
		}
		
	}

	// 你的分享 点击事件
	class ShareClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			showShare();
		}

	}

	class MyListener implements OnClickListener {
		int p;

		public MyListener(int p) {
			this.p = p;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// 跳转到新的activity
			Intent intent = new Intent();// 意图
			intent.setClass(context, ShowImageActivity.class);
			intent.putExtra("uri", "http://pb2.pstatp.com/"
					+ images.get(p).getLargeImageUri());
			intent.putExtra("isgif", images.get(p).getIs_gif());
			context.startActivity(intent);

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
	}

	private void showShare() {
		ShareSDK.initSDK(context);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("xxx");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
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
