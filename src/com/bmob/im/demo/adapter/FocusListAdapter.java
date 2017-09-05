package com.bmob.im.demo.adapter;

import java.util.List;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.Focus;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FocusListAdapter extends BaseAdapter {

	private Context context;
	private List<Focus> list;
	private LayoutInflater mInflater;

	public FocusListAdapter(Context context, List<Focus> list) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.list = list;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Focus focus = list.get(position);

		String avatarUrl = focus.getUserimage();
		
		BitmapUtils bitmapUtils=new BitmapUtils(context);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
		bitmapUtils.display(viewHolder.userIcon, avatarUrl,new BitmapLoadCallBack<View>() {

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
//		Picasso.with(context).load(avatarUrl)
//				.placeholder(R.drawable.ic_launcher).into(viewHolder.userIcon);
		viewHolder.userName.setText(focus.getUsername());
		
		return convertView;
	}

	public static class ViewHolder {
		// public CircleImageView userIcon;
		public ImageView userIcon;
		public TextView userName;
		
	}

}
