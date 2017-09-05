package com.bmob.im.demo.ui;

import java.io.FileNotFoundException;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.config.BmobConstants;
import com.bmob.im.demo.util.CommonUtils;

public class RegisterActivity extends BaseActivity {

	Button btn_register;
	EditText et_username, et_password, et_email;
	BmobChatUser currentUser;
	private ImageView userImage, i_back;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// initTopBarForLeft("注册");

		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		et_email = (EditText) findViewById(R.id.et_email);
		userImage = (ImageView) findViewById(R.id.user_image);
		i_back = (ImageView) findViewById(R.id.i_back);
		i_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		userImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent1 = new Intent();
				/* 开启Pictures画面Type设定为image */
				intent1.setType("image/*");
				/* 使用Intent.ACTION_GET_CONTENT这个Action */
				intent1.setAction(Intent.ACTION_GET_CONTENT);
				/* 取得相片后返回本画面 */
				 startActivityForResult(intent1, 1);
			
			}
		});
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				register();
			}
		});
		checkUser();
	}

	private void checkUser() {
		BmobQuery<User> query = new BmobQuery<User>();
		query.addWhereEqualTo("username", "zdk");
		query.findObjects(this, new FindListener<User>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<User> arg0) {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.size() > 0) {
					User user = arg0.get(0);
					user.setPassword("1234567");
					user.update(RegisterActivity.this, new UpdateListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							userManager.login("zdk", "1234567",
									new SaveListener() {

										@Override
										public void onSuccess() {
											// TODO Auto-generated method stub
											Log.i("zdk", "登陆成功");
										}

										@Override
										public void onFailure(int code,
												String msg) {
											// TODO Auto-generated method stub
											Log.i("zdk", "登陆失败：" + code
													+ ".msg = " + msg);
										}
									});
						}

						@Override
						public void onFailure(int code, String msg) {
							// TODO Auto-generated method stub

						}
					});
				}
			}
		});
	}

	private void register() {
		String name = et_username.getText().toString();
		String password = et_password.getText().toString();
		String pwd_again = et_email.getText().toString();

		if (TextUtils.isEmpty(name)) {
			ShowToast(R.string.toast_error_username_null);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			ShowToast(R.string.toast_error_password_null);
			return;
		}
		if (!pwd_again.equals(password)) {
			ShowToast(R.string.toast_error_comfirm_password);
			return;
		}

		boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
		if (!isNetConnected) {
			ShowToast(R.string.network_tips);
			return;
		}

		final ProgressDialog progress = new ProgressDialog(
				RegisterActivity.this);
		progress.setMessage("正在注册...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		// 由于每个应用的注册所需的资料都不一样，故IM sdk未提供注册方法，用户可按照bmod SDK的注册方式进行注册。
		// 注册的时候需要注意两点：1、User表中绑定设备id和type，2、设备表中绑定username字段
		
		final User bu = new User();
		bu.setUsername(name);
		bu.setPassword(password);
		// 将user和设备id进行绑定aa

		bu.setDeviceType("android");
		bu.setUserImage(path);
		bu.setSex(false);
		bu.setAdmin(false);
		bu.setPass(false);
		bu.setInstallId(BmobInstallation.getInstallationId(this));
		bu.signUp(RegisterActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				progress.dismiss();
				ShowToast("注册成功");
				// 将设备与username进行绑定
				userManager.bindInstallationForRegister(bu.getUsername());
				// 更新地理位置信息
				updateUserLocation();
				// 发广播通知登陆页面退出
				sendBroadcast(new Intent(
						BmobConstants.ACTION_REGISTER_SUCCESS_FINISH));
				// 启动主页
				Intent intent = new Intent(RegisterActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				BmobLog.i(arg1);
				ShowToast("注册失败:" + arg1);
				progress.dismiss();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // 判断是否为待处理的结果
			Uri uri = data.getData();
			path = getImagePath(uri, null);
			
			ContentResolver cr = this.getContentResolver();
			try {
				Log.e("zdk", path.toString());
				Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				ImageView res_head = (ImageView) findViewById(R.id.user_image); // 获取布局文件中添加的ImageView组件
				res_head.setAdjustViewBounds(true);
				res_head.setMaxWidth(150);
				res_head.setMaxHeight(150);
				/* 将Bitmap设定到ImageView */
				res_head.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e("zdk", e.getMessage(), e);
			}
		}
	}

	private String getImagePath(Uri uri, String seletion) {
		String path = null;
		Cursor cursor = getContentResolver().query(uri, null, seletion, null,
				null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.DATA));
			}
			cursor.close();

		}
		return path;

	}

}
