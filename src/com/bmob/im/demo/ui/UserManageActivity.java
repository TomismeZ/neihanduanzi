package com.bmob.im.demo.ui;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

import cn.bmob.v3.listener.UpdateListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.fragment.UserCollectFragment;
import com.bmob.im.demo.ui.fragment.UserCommentFragment;
import com.bmob.im.demo.ui.fragment.UserSubmitFragment;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class UserManageActivity extends FragmentActivity implements
		OnClickListener {

	private static final int REQUESTCODE_1 = 0x01;
	RadioGroup group;
	UserSubmitFragment submitFragment;
	UserCollectFragment collectFragment;
	UserCommentFragment commentFragment;
	private ImageView userImage;
	private TextView username, fansCount, focusCount, textView1;
	private ImageView sex, i_back, btn_set;

	LinearLayout linearLayout;
	User userInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_manage);
		group = (RadioGroup) findViewById(R.id.radiobutton_main);
		// 给radiogroup添加 点击事件
		// 匿名内部类
		group.setOnCheckedChangeListener(new MyRadioListener());
		submitFragment = new UserSubmitFragment();
		this.getSupportFragmentManager().beginTransaction()
				.add(R.id.container, submitFragment).commit();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		userImage = (ImageView) findViewById(R.id.i_userImage);
		username = (TextView) findViewById(R.id.userName);
		sex = (ImageView) findViewById(R.id.sex);
		i_back = (ImageView) findViewById(R.id.i_back);
		btn_set = (ImageView) findViewById(R.id.btn_set);
		fansCount = (TextView) findViewById(R.id.fansCount);
		focusCount = (TextView) findViewById(R.id.focusCount);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		textView1 = (TextView) findViewById(R.id.textView1);

		// Intent intent = getIntent();
		// userInfo = (User) intent.getSerializableExtra("userInfo");
		userInfo = BmobUser.getCurrentUser(getApplicationContext(), User.class);
		if (userInfo.getSex()) {
			sex.setImageResource(R.drawable.ic_sex_women);
		}
		username.setText(userInfo.getUsername());
		fansCount.setText(userInfo.getFansCount() + "");
		focusCount.setText(userInfo.getFocusCount() + "");
		userImage.setAdjustViewBounds(true);
		userImage.setMaxWidth(300);
		userImage.setMaxHeight(300);
		userImage.setPadding(5, 5, 5, 5); // 设置ImageView的内边距

		BitmapUtils bitmapUtils = new BitmapUtils(this);
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

		i_back.setOnClickListener(this);
		btn_set.setOnClickListener(this);
		linearLayout.setOnClickListener(this);

	}

	// radiogroup点击事件
	class MyRadioListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			// 获取一个碎片管理器
			FragmentManager fragmentManager = UserManageActivity.this
					.getSupportFragmentManager();
			// 启动事务 交易
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			// 把三个碎片都藏起来

			if (submitFragment != null) {
				fragmentTransaction.hide(submitFragment);
			}
			if (collectFragment != null) {
				fragmentTransaction.hide(collectFragment);
			}
			if (commentFragment != null) {
				fragmentTransaction.hide(commentFragment);
			}
			// 通过switch区分四个radiobutton
			switch (checkedId) {

			case R.id.rb_submit:

				if (submitFragment == null) {
					submitFragment = new UserSubmitFragment();
					fragmentTransaction.add(R.id.container, submitFragment);
				} else {
					fragmentTransaction.show(submitFragment);
				}

				break;
			case R.id.rb_collect:

				if (collectFragment == null) {
					collectFragment = new UserCollectFragment();
					fragmentTransaction.add(R.id.container, collectFragment);
				} else {
					fragmentTransaction.show(collectFragment);
				}

				break;
			case R.id.rb_commit:

				if (commentFragment == null) {
					commentFragment = new UserCommentFragment();
					fragmentTransaction.add(R.id.container, commentFragment);
				} else {
					fragmentTransaction.show(commentFragment);
				}

				break;

			default:
				break;
			}
			fragmentTransaction.commit();// 提交，否则没有任何效果
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.i_back:
			finish();
			break;
		case R.id.btn_set:
			Intent intent = new Intent(UserManageActivity.this,
					UserInfoModify.class);
			intent.putExtra("userInfo", userInfo);
			startActivityForResult(intent, REQUESTCODE_1);
			break;
		case R.id.linearLayout:
			// 初始化地图Sdk
			SDKInitializer.initialize(this);
			// 获取位置
			getLocation();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE_1 && resultCode == RESULT_OK) {
			initView();
		}
	}

	LocationClient mLocationClient = null;

	private void getLocation() {
		// 获取地理位置管理器
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		initLocation();
		mLocationClient.registerLocationListener(new MyLocationListener()); // 注册监听函数
		mLocationClient.start();
		mLocationClient.requestLocation();
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备)
		option.setCoorType("gcj02");// 可选，默认gcj02，设置返回的定位结果坐标系
		// int span = 1000;
		// option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		mLocationClient.setLocOption(option);
		mLocationClient.start();// 开始定位
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			// Receive Location
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("当前时间 : ");
			sb.append(location.getTime());
			sb.append("\n错误码 : ");
			sb.append(location.getLocType());
			sb.append("\n纬度 : ");
			sb.append(location.getLatitude());
			sb.append("\n经度 : ");
			sb.append(location.getLongitude());
			sb.append("\n半径 : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\n速度 : ");
				sb.append(location.getSpeed());
				sb.append("\n卫星数 : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\n地址 : ");
				sb.append(location.getAddrStr());
			}

			Log.d("zdk", "onReceiveLocation " + sb.toString());
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			System.out.println("location----" + location.getCity() + "--"
					+ latitude + "--" + longitude);
			// 获取城市
			String city = location.getCity();
			textView1.setText(city);
			if (city != null) {
				User user = new User();
				BmobGeoPoint location1 = new BmobGeoPoint(longitude, latitude);
				user.setLocation(location1);
				BmobUser bmobUser = BmobUser
						.getCurrentUser(getApplicationContext());
				user.update(getApplicationContext(), bmobUser.getObjectId(),
						new UpdateListener() {
							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								System.out.println("修改信息成功！");
							}

							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub
								System.out.println("修改信息失败！");
							}
						});
			}
			Log.e("zdk", "城市的位置---" + city);
		}

	}

}
