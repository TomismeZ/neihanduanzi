package com.bmob.im.demo.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.util.CollectionUtils;


/** ����
  * @ClassName: BaseActivity
  * @Description: TODO
  */
public class BaseActivity extends FragmentActivity {

	BmobUserManager userManager;
	BmobChatManager manager;
	
	CustomApplcation mApplication;
	
	protected int mScreenWidth;
	protected int mScreenHeight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userManager = BmobUserManager.getInstance(this);
		manager = BmobChatManager.getInstance(this);
		mApplication = CustomApplcation.getInstance();
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
	}

	Toast mToast;

	public void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mToast == null) {
						mToast = Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_LONG);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});
			
		}
	}

	public void ShowToast(final int resId) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mToast == null) {
					mToast = Toast.makeText(BaseActivity.this.getApplicationContext(), resId,
							Toast.LENGTH_LONG);
				} else {
					mToast.setText(resId);
				}
				mToast.show();
			}
		});
	}

	/** ��Log
	  * ShowLog
	  * @return void
	  * @throws
	  */
	public void ShowLog(String msg){
		Log.i("life",msg);
	}
	




	
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}
	
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}
	/** ���ڵ�½�����Զ���½����µ��û����ϼ��������ϵļ�����
	  * @Title: updateUserInfos
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	public void updateUserInfos(){
		//���µ���λ����Ϣ
		updateUserLocation();
		//��ѯ���û��ĺ����б�(��������б���ȥ���������û���Ŷ),Ŀǰ֧�ֵĲ�ѯ���Ѹ���Ϊ100�������޸����ڵ����������ǰ����BmobConfig.LIMIT_CONTACTS���ɡ�
		//����Ĭ�ϲ�ȡ���ǵ�½�ɹ�֮�󼴽������б�洢�����ݿ��У������µ���ǰ�ڴ���,
		userManager.queryCurrentContactList(new FindListener<BmobChatUser>() {

					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						if(arg0==BmobConfig.CODE_COMMON_NONE){
							ShowLog(arg1);
						}else{
							ShowLog("��ѯ�����б�ʧ�ܣ�"+arg1);
						}
					}

					@Override
					public void onSuccess(List<BmobChatUser> arg0) {
						// TODO Auto-generated method stub
						// ���浽application�з���Ƚ�
						CustomApplcation.getInstance().setContactList(CollectionUtils.list2map(arg0));
					}
				});
	}
	
	/** �����û��ľ�γ����Ϣ
	  * @Title: uploadLocation
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	public void updateUserLocation(){
		if(CustomApplcation.lastPoint!=null){
			String saveLatitude  = mApplication.getLatitude();
			String saveLongtitude = mApplication.getLongtitude();
			
			String newLat = String.valueOf(CustomApplcation.lastPoint.getLatitude());
			String newLong = String.valueOf(CustomApplcation.lastPoint.getLongitude());
			ShowLog("saveLatitude ="+saveLatitude+",saveLongtitude = "+saveLongtitude);
			ShowLog("newLat ="+newLat+",newLong = "+newLong);
			if(!saveLatitude.equals(newLat)|| !saveLongtitude.equals(newLong)){//ֻ��λ���б仯�͸��µ�ǰλ�ã��ﵽʵʱ���µ�Ŀ��
				User u = (User) userManager.getCurrentUser(User.class);
				Log.e("zdk", u.toString());
				final User user = new User();
				user.setLocation(CustomApplcation.lastPoint);
				user.setObjectId(u.getObjectId());
				user.update(this,new UpdateListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						CustomApplcation.getInstance().setLatitude(String.valueOf(user.getLocation().getLatitude()));
						CustomApplcation.getInstance().setLongtitude(String.valueOf(user.getLocation().getLongitude()));
						Log.e("zdk", "��γ�ȸ��³ɹ�");

					}
					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						ShowLog("��γ�ȸ��� ʧ��:"+msg);
					}
				});
			}else{
//				ShowLog("�û�λ��δ�������仯");
			}
		}
	}
}
