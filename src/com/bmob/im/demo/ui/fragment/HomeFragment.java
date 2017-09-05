package com.bmob.im.demo.ui.fragment;

import cn.bmob.im.BmobUserManager;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.HomePagerAdapter;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.LoginActivity;
import com.bmob.im.demo.ui.SlidingUserActivity;
import com.bmob.im.demo.ui.SubmitActivity;
import com.bmob.im.demo.ui.UserManageActivity;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.slidingmenu.lib.SlidingMenu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

//support v4 1.6 v7 2.2  v13 3.0 v21 5.0  所支持的最低Android版本号是第几版
public class HomeFragment extends Fragment implements OnClickListener {

	ViewPager viewPager;
	RadioGroup radioGroup;
	BmobUserManager userManager;
	ImageView userImage;
	ImageView contribute;
	User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// inflater填充器 作用：把xml文件填充到java类中
		userManager = BmobUserManager.getInstance(getActivity());

		View view = inflater.inflate(R.layout.fragment_home, null);
		// 实例化viewpager
		viewPager = (ViewPager) view.findViewById(R.id.vp_home);
		radioGroup = (RadioGroup) view.findViewById(R.id.rg_home);
		contribute = (ImageView) view.findViewById(R.id.t_contribute);
		userImage = (ImageView) view.findViewById(R.id.i_userImage);
		
		// 给viewpager中填充内容--》fragment
		// 我们 需要5个fragment 用来给viewpager做内容
		// adapter适配器 adapterview
		// getFragmentManager getChildFragmentManager
		viewPager.setAdapter(new HomePagerAdapter(this
				.getChildFragmentManager()));

		radioGroup.setOnCheckedChangeListener(new HomeRadioListener());
		viewPager.setOnPageChangeListener(new HomePagerListener());

		user = (User) userManager.getCurrentUser(User.class);

		userImage.setAdjustViewBounds(true);
		userImage.setMaxWidth(80);
		userImage.setMaxHeight(80);
		userImage.setPadding(5, 5, 5, 5); // 设置ImageView的内边距
		if (user != null) {
			BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
			bitmapUtils
					.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
			bitmapUtils.display(userImage, user.getUserImage(),
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
		
		}
		userImage.setOnClickListener(this);
		contribute.setOnClickListener(this);
	
		return view;
	}

	// 你的viewpager的点击事件
	class HomePagerListener implements OnPageChangeListener {

		// 状态改变监听
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		// 滑动中
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

			// System.out.println();
			Log.i("xxx", arg0 + "      " + arg1 + "        " + arg2);

		}

		// 滑动后
		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
		}

	}

	// 你的radiogroup的点击事件
	class HomeRadioListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub

			switch (checkedId) {
			case R.id.rb_recomment:
				viewPager.setCurrentItem(0, false);
				break;
			case R.id.rb_video:
				viewPager.setCurrentItem(1, false);
				break;
			case R.id.rb_image:
				viewPager.setCurrentItem(2, false);
				break;
			case R.id.rb_joke:
				viewPager.setCurrentItem(3, false);
				break;
			case R.id.rb_book:
				viewPager.setCurrentItem(4, false);
				break;

			default:
				break;
			}

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.i_userImage:
			if (user != null) {
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
		case R.id.t_contribute:
			if (user != null) {
				Intent intent = new Intent(getActivity(), SubmitActivity.class);
				intent.putExtra("type", 1);
				startActivity(intent);
			}else{
				Toast.makeText(getActivity(), "请先登录，才可以投稿", Toast.LENGTH_LONG).show();
			}
			break;
		
		default:
			break;
		}
	}

}
