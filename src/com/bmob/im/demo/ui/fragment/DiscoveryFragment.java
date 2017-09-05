package com.bmob.im.demo.ui.fragment;

import cn.bmob.im.BmobUserManager;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.DiscoveryPagerAdapter;
import com.bmob.im.demo.adapter.HomePagerAdapter;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.LoginActivity;
import com.bmob.im.demo.ui.UserManageActivity;
import com.bmob.im.demo.ui.fragment.HomeFragment.HomePagerListener;
import com.bmob.im.demo.ui.fragment.HomeFragment.HomeRadioListener;
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
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class DiscoveryFragment extends Fragment implements OnClickListener{
	BmobUserManager userManager;
	ViewPager viewPager;
	RadioGroup radioGroup;
	TextView nearperson;
	ImageView userImage;
	User user;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_discovery, null);
		viewPager=(ViewPager) view.findViewById(R.id.vp_discovery);
		radioGroup=(RadioGroup) view.findViewById(R.id.rg_discovery);
		nearperson=(TextView) view.findViewById(R.id.t_near);
		userImage = (ImageView) view.findViewById(R.id.i_userImage);
		viewPager.setAdapter(new  DiscoveryPagerAdapter(this.getChildFragmentManager()));
		radioGroup.setOnCheckedChangeListener(new DiscoveryRadioListener());
		viewPager.setOnPageChangeListener(new DiscoveryPagerListener());
		userManager = BmobUserManager.getInstance(getActivity());
		user = (User) userManager.getCurrentUser(User.class);
		userImage.setAdjustViewBounds(true);
		userImage.setMaxWidth(80);
		userImage.setMaxHeight(80);
		userImage.setPadding(5, 5, 5, 5); // 设置ImageView的内边距
		if(user!=null){
		BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
		bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
		bitmapUtils.display(userImage, user.getUserImage(),
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
		nearperson.setOnClickListener(this);
		// TODO Auto-generated method stub
		
		return view;
	}
	
	//你的viewpager的点击事件
		class DiscoveryPagerListener implements OnPageChangeListener{

			//状态改变监听
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
			//滑动中
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
//				System.out.println();
				Log.i("xxx",arg0+"      "+arg1+"        "+arg2);
				
			
				
			}
			//滑动后
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				((RadioButton)radioGroup.getChildAt(position)).setChecked(true);
			}
			
		}
		
	//你的radiogroup的点击事件
		class DiscoveryRadioListener implements OnCheckedChangeListener{

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
				switch (checkedId) {
				case R.id.dis_reba:
					viewPager.setCurrentItem(0,false);
					break;
				
				case R.id.t_near:
					viewPager.setCurrentItem(1,false
							);
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
		case R.id.t_near:
			if(user!=null){
//				Intent intent = new Intent(getActivity(), NearPeopleActivity.class);
//				startActivity(intent);
			}else{
				Toast.makeText(getActivity(), "请先登录，才可以查看附件的人", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

}
