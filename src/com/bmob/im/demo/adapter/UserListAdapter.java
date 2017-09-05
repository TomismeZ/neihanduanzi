package com.bmob.im.demo.adapter;

import java.util.List;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.User;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserListAdapter extends BaseAdapter {

	private Context context;
	private List<User> users;
	private LayoutInflater mInflater;

	public UserListAdapter(Context context, List<User> users) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.users = users;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return users.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_listview_fans, null);
			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.fans_item_user_name);
			// viewHolder.userIcon =
			// (CircleImageView)view.findViewById(R.id.fans_item_user_icon);
			viewHolder.userIcon = (ImageView) convertView
					.findViewById(R.id.fans_item_user_icon);
			viewHolder.pass=(Button) convertView.findViewById(R.id.user_pass);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		User user = users.get(position);

		String avatarUrl = user.getUserImage();
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
		bitmapUtils.display(viewHolder.userIcon,avatarUrl,
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
//		Picasso.with(context).load(avatarUrl)
//				.placeholder(R.drawable.ic_launcher).into(viewHolder.userIcon);
		viewHolder.userName.setText(user.getUsername());
		if(!user.getPass()){
			viewHolder.pass.setVisibility(View.VISIBLE);
		}
		viewHolder.pass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				User newuser=users.get(position);
//				user.setObjectId(users.get(position).getObjectId());
				newuser.setPass(true);
				
				newuser.update(context,new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						users.remove(position);
						notifyDataSetChanged();
						System.out.println("update success£¡");
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						System.out.println("update fail£¡"+arg1.toString());
					}
				});
				
			}
		});
		viewHolder.userIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				User user=users.get(position);
				
				Intent intent = new Intent(context, OtherUserInfoActivity.class);
				intent.putExtra("otheruser", user);
				intent.putExtra("type", 4);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	public static class ViewHolder {
		// public CircleImageView userIcon;
		public ImageView userIcon;
		public TextView userName;
		public Button pass;
	}

}
