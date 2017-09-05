package com.bmob.im.demo.ui;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class UserInfoModify extends Activity implements OnClickListener {
	ImageView userImage;
	EditText m_username, m_password, email;
	RadioButton r_male, r_female;
	Button b_modifycontent, b_cancel;
	User userInfo;
	private Button b_exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_modify);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		userImage = (ImageView) findViewById(R.id.user_image);
		m_username = (EditText) findViewById(R.id.m_username);
		m_password = (EditText) findViewById(R.id.m_password);
		email = (EditText) findViewById(R.id.email);

		r_male = (RadioButton) findViewById(R.id.r_male);
		r_female = (RadioButton) findViewById(R.id.r_female);
		b_modifycontent = (Button) findViewById(R.id.b_modifycontent);
		b_cancel = (Button) findViewById(R.id.b_cancel);
		b_exit = (Button)findViewById(R.id.btn_logout);
		userInfo = BmobChatUser.getCurrentUser(this, User.class);
		m_username.setHint(userInfo.getUsername());
		m_password.setHint(userInfo.getPassword());
		if (email != null)
			email.setHint(userInfo.getEmail());

		if (userInfo.getSex()) {
			r_female.setChecked(true);
		} else {
			r_male.setChecked(true);
		}
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
		b_modifycontent.setOnClickListener(this);
		b_cancel.setOnClickListener(this);
		b_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.b_modifycontent:
			User user = new User();
			user.setUsername(m_username.getText().toString());
			user.setPassword(m_password.getText().toString());
			user.setEmail(email.getText().toString());
			user.setFansCount(userInfo.getFansCount());
			user.setFocusCount(userInfo.getFocusCount());
			if (r_male.isChecked()) {
				user.setSex(false);
			} else if (r_female.isChecked()) {
				user.setSex(true);
			}
			BmobUser bmobUser = BmobUser.getCurrentUser(this);

			user.update(this, bmobUser.getObjectId(), new UpdateListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					System.out.println("修改信息成功！");

					setResult(RESULT_OK);
					finish();
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					System.out.println("修改信息失败！");
				}
			});
			break;
		case R.id.b_cancel:
			finish();
			break;
		case R.id.btn_logout:
			CustomApplcation.getInstance().logout();
			finish();
			startActivity(new Intent(this, LoginActivity.class));
			break;
		default:
			break;
		}
	}
}
