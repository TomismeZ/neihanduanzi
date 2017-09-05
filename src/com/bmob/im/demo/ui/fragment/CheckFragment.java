package com.bmob.im.demo.ui.fragment;

import cn.bmob.im.BmobUserManager;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.CheckActivity;
import com.bmob.im.demo.ui.LoginActivity;
import com.bmob.im.demo.ui.SubmitActivity;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CheckFragment extends Fragment implements OnClickListener {
	BmobUserManager userManager;
	ImageView userImage;
	ImageView contribute;

	Button check;
	User userInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_check, null);
		userImage = (ImageView) view.findViewById(R.id.i_userImage);
		contribute = (ImageView) view.findViewById(R.id.t_contribute);
		check = (Button) view.findViewById(R.id.check);
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
		contribute.setOnClickListener(this);
		check.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.i_userImage:
			if (userInfo != null) {
				Intent intent = new Intent(getActivity(),
						UserManageActivity.class);
				User userInfo = (User) userManager.getCurrentUser(User.class);
				intent.putExtra("userInfo", userInfo);
				startActivity(intent);
			} else {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.t_contribute:
			if (userInfo != null) {
				Intent intent = new Intent(getActivity(), SubmitActivity.class);
				intent.putExtra("type", 1);
				startActivity(intent);
			}else{
				Toast.makeText(getActivity(), "请先登录，才可以投稿", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.check:
			if (userInfo != null) {
			Intent intent2 = new Intent(getActivity(),CheckActivity.class);
			intent2.putExtra("userInfo", userInfo);
			startActivity(intent2);
			}else{
				Toast.makeText(getActivity(), "请先登录，才可以审核哦！", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

}
