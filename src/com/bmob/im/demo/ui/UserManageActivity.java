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
		// ��radiogroup��� ����¼�
		// �����ڲ���
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
		userImage.setPadding(5, 5, 5, 5); // ����ImageView���ڱ߾�

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

	// radiogroup����¼�
	class MyRadioListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			// ��ȡһ����Ƭ������
			FragmentManager fragmentManager = UserManageActivity.this
					.getSupportFragmentManager();
			// �������� ����
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			// ��������Ƭ��������

			if (submitFragment != null) {
				fragmentTransaction.hide(submitFragment);
			}
			if (collectFragment != null) {
				fragmentTransaction.hide(collectFragment);
			}
			if (commentFragment != null) {
				fragmentTransaction.hide(commentFragment);
			}
			// ͨ��switch�����ĸ�radiobutton
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
			fragmentTransaction.commit();// �ύ������û���κ�Ч��
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
			// ��ʼ����ͼSdk
			SDKInitializer.initialize(this);
			// ��ȡλ��
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
		// ��ȡ����λ�ù�����
		mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
		initLocation();
		mLocationClient.registerLocationListener(new MyLocationListener()); // ע���������
		mLocationClient.start();
		mLocationClient.requestLocation();
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸)
		option.setCoorType("gcj02");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		// int span = 1000;
		// option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		mLocationClient.setLocOption(option);
		mLocationClient.start();// ��ʼ��λ
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			// Receive Location
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("��ǰʱ�� : ");
			sb.append(location.getTime());
			sb.append("\n������ : ");
			sb.append(location.getLocType());
			sb.append("\nγ�� : ");
			sb.append(location.getLatitude());
			sb.append("\n���� : ");
			sb.append(location.getLongitude());
			sb.append("\n�뾶 : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\n�ٶ� : ");
				sb.append(location.getSpeed());
				sb.append("\n������ : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\n��ַ : ");
				sb.append(location.getAddrStr());
			}

			Log.d("zdk", "onReceiveLocation " + sb.toString());
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			System.out.println("location----" + location.getCity() + "--"
					+ latitude + "--" + longitude);
			// ��ȡ����
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
								System.out.println("�޸���Ϣ�ɹ���");
							}

							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub
								System.out.println("�޸���Ϣʧ�ܣ�");
							}
						});
			}
			Log.e("zdk", "���е�λ��---" + city);
		}

	}

}
