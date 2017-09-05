package com.bmob.im.demo.ui.fragment;

import cn.bmob.im.BmobUserManager;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.FansActivity;
import com.bmob.im.demo.ui.LoginActivity;
import com.bmob.im.demo.ui.SlidingUserActivity;
import com.bmob.im.demo.ui.UserManageActivity;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MessageFragment extends Fragment implements OnClickListener{
	BmobUserManager userManager;
	ImageView userImage;
	LinearLayout header_fans,header_other_user;
	User userInfo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.fragment_message, null);
		userImage = (ImageView) view.findViewById(R.id.i_userImage);
		header_other_user=(LinearLayout) view.findViewById(R.id.header_other_user);
		header_fans=(LinearLayout) view.findViewById(R.id.header_fans);
		
		userManager = BmobUserManager.getInstance(getActivity());
		userInfo = (User) userManager.getCurrentUser(User.class);
		userImage.setAdjustViewBounds(true);
		userImage.setMaxWidth(80);
		userImage.setMaxHeight(80);
		userImage.setPadding(5, 5, 5, 5); // 设置ImageView的内边距
		if(userInfo!=null){
		BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
		bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
		bitmapUtils.display(userImage, userInfo.getUserImage(),
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
		}
		userImage.setOnClickListener(this);
		header_other_user.setOnClickListener(this);
		header_fans.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.header_other_user:
			if (userInfo != null) {
				Intent intent=new Intent(getActivity(),SlidingUserActivity.class);
				startActivity(intent);
				}else{
					Toast.makeText(getActivity(), "请先登录，才能查看哦！", Toast.LENGTH_LONG).show();
				}
			break;
		case R.id.header_fans:
			if (userInfo != null) {
			Intent intent=new Intent(getActivity(),FansActivity.class);
			startActivity(intent);
			}else{
				Toast.makeText(getActivity(), "请先登录，才查看哦！", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.i_userImage:
			if (userInfo != null) {
				Intent intent1 = new Intent(getActivity(),
						UserManageActivity.class);
				User userInfo = (User) userManager.getCurrentUser(User.class);
				intent1.putExtra("userInfo", userInfo);
				startActivity(intent1);
			} else {
				Intent intent2 = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent2);
			}
			break;
		
		default:
			break;
		}
	}

}
