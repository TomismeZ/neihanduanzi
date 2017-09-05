package com.bmob.im.demo.ui;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.Fans;
import com.bmob.im.demo.bean.Focus;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.Joke;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.bean.Video;

import com.bmob.im.demo.ui.fragment.UserCollectFragment;
import com.bmob.im.demo.ui.fragment.UserCommentFragment;

import com.bmob.im.demo.ui.fragment.UserSubmitFragment;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OtherUserInfoActivity extends FragmentActivity implements
		OnClickListener {
	RadioGroup group;
	UserSubmitFragment submitFragment;
	UserCollectFragment collectFragment;
	UserCommentFragment commentFragment;
	private ImageView userImage;
	private TextView username,fansCount,focusCount;
	private ImageView sex,i_back;
	Button focusBut;
	private boolean focused = false; // �Ƿ��ע
	User user, otheruser;
	Intent intent;
	private ArrayList<User> focusList;
	Video video;
	ImageInfo image;
	Joke joke;
	private String focusObjectId;
	private String fansObjectId;
	private String contentId;
	private String userimageuri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_other_userinfo);
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
		focusBut = (Button) findViewById(R.id.user_is_focus);
		userImage = (ImageView) findViewById(R.id.i_userImage);
		username = (TextView) findViewById(R.id.userName);
		sex=(ImageView) findViewById(R.id.sex);
		i_back=(ImageView) findViewById(R.id.i_back);
		fansCount=(TextView) findViewById(R.id.fansCount);
		focusCount=(TextView) findViewById(R.id.focusCount);
		focusBut.setOnClickListener(this);
		i_back.setOnClickListener(this);
		focusList = new ArrayList<User>();
		intent = getIntent();
		// user = (User) intent.getSerializableExtra("user");
		user = BmobUser.getCurrentUser(this, User.class);
		if (intent.getIntExtra("type", 0) == 1) {
			joke = (Joke) intent.getSerializableExtra("joke");
			contentId = joke.getGroupId() + "";
			userimageuri = joke.getUserAvatar();
			username.setText(joke.getUserName());
			userImage.setAdjustViewBounds(true);
			userImage.setMaxWidth(300);
			userImage.setMaxHeight(300);
			userImage.setPadding(5, 5, 5, 5); // ����ImageView���ڱ߾�
			BitmapUtils bitmapUtils = new BitmapUtils(this);
			bitmapUtils
					.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
			bitmapUtils.display(userImage, userimageuri,
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
		if (intent.getIntExtra("type", 0) == 2) {
			image = (ImageInfo) intent.getSerializableExtra("image");
			contentId = image.getObjectId();
			userimageuri = image.getUserAvatar();
			username.setText(image.getUserName());
			userImage.setAdjustViewBounds(true);
			userImage.setMaxWidth(300);
			userImage.setMaxHeight(300);
			userImage.setPadding(5, 5, 5, 5); // ����ImageView���ڱ߾�
			BitmapUtils bitmapUtils = new BitmapUtils(this);
			bitmapUtils
					.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
			bitmapUtils.display(userImage, userimageuri,
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
		if (intent.getIntExtra("type", 0) == 3) {
			video = (Video) intent.getSerializableExtra("video");
			contentId = video.getObjectId();
			userimageuri = video.getUserAvatar();
			username.setText(video.getUserName());
			userImage.setAdjustViewBounds(true);
			userImage.setMaxWidth(300);
			userImage.setMaxHeight(300);
			userImage.setPadding(5, 5, 5, 5); // ����ImageView���ڱ߾�
			BitmapUtils bitmapUtils = new BitmapUtils(this);
			bitmapUtils
					.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
			bitmapUtils.display(userImage, userimageuri,
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
		if (intent.getIntExtra("type", 0) == 4) {
			Log.e("zdk", "otheruser---------!");
			otheruser = (User) intent.getSerializableExtra("otheruser");
			if (otheruser.getSex()) {
				sex.setImageResource(R.drawable.ic_sex_women);
			}else{
				sex.setImageResource(R.drawable.ic_sex_men);
			}
			fansCount.setText(otheruser.getFansCount()+"");
			focusCount.setText(otheruser.getFocusCount()+"");
			contentId = otheruser.getObjectId();
			userimageuri = otheruser.getUserImage();
			username.setText(otheruser.getUsername());
			userImage.setAdjustViewBounds(true);
			userImage.setMaxWidth(300);
			userImage.setMaxHeight(300);
			userImage.setPadding(5, 5, 5, 5); // ����ImageView���ڱ߾�
			BitmapUtils bitmapUtils = new BitmapUtils(this);
			bitmapUtils
					.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
			bitmapUtils.display(userImage, userimageuri,
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
		// ���ù�ע״̬
		if (focusList != null && focusList.size() > 0) {
			if (userIsFocus(focusList)) {
				focusBut.setText("ȡ����ע");
				focused = true;
			}
		}
		getDBdata();
	}

	/**
	 * ��ǰ�û��Ƿ��ע���û�
	 * 
	 * @return
	 */
	private boolean userIsFocus(ArrayList<User> list) {
		boolean isFocus = false;
		for (int i = 0; i < list.size(); i++) {
			if (user.getObjectId().equals(list.get(i).getObjectId())) {
				isFocus = true;
				break;
			}
		}
		return isFocus;
	}

	private void getDBdata() {
		BmobQuery<Focus> query = new BmobQuery<Focus>();
		// query.addWhereEndsWith("contentId", video.getObjectId());
		query.addWhereEqualTo("contentId", contentId);
		query.findObjects(this, new FindListener<Focus>() {

			@Override
			public void onSuccess(List<Focus> list) {
				// TODO Auto-generated method stub

				if (list != null && list.size() > 0) {
					System.out.println("��ѯ�ɹ�----" + list.get(0).toString());
					focusBut.setText("ȡ����ע");
					focused = true;
					focusObjectId = list.get(0).getObjectId();
					Log.d("zdk", "---------focusId-------" + focusObjectId);
				} else {
					System.out.println("��ѯʧ��----");
					focusBut.setText("��ע");
					focused = false;
				}

			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

				System.out.println("��ѯʧ��----");
			}
		});
	}

	// ��˿�б�
	private void getFansDBdata() {
		BmobQuery<Fans> query = new BmobQuery<Fans>();
		query.addWhereEqualTo("userId", contentId);
		query.findObjects(getApplicationContext(), new FindListener<Fans>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<Fans> list) {
				// TODO Auto-generated method stub
				if (list != null && list.size() > 0) {
					System.out.println("fans��ѯ�ɹ�----" + list.get(0).toString());

					fansObjectId = list.get(0).getObjectId();

					Log.d("zdk", "fansId------" + fansObjectId);
					Fans fans = new Fans();
					fans.setObjectId(fansObjectId);
					fans.delete(getApplicationContext(), new DeleteListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Log.e("zdk", "delete fans success");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Log.e("zdk", "delete fans fail");
						}
					});
				} else {
					System.out.println("fans��ѯʧ��----");

				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_is_focus:
			if (focused) { // / �ѹ�ע�������ȡ����ע
				Toast.makeText(this, "ȡ����ע������δ��ɣ�", Toast.LENGTH_SHORT).show();
				Log.e("zdk", "-----focused true-----");
				Focus focus = new Focus();
				focus.setObjectId(focusObjectId);
				// focus.setUserId(user.getObjectId());
				focus.delete(this, new DeleteListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						System.out.println("delete focus success");
						focusBut.setText("��ע");
						focused = false;
						User newUser = new User();

						newUser.setObjectId(user.getObjectId());
						newUser.setFocusCount(user.getFocusCount() - 1);
						if (intent.getIntExtra("type", 0) == 4) {
							getFansDBdata();
							newUser.setFansCount(user.getFansCount() - 1);
						}
						newUser.update(getApplicationContext());
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						System.out.println("delete fail");
					}
				});

			} else { // δ��ע��������ע
				Log.e("zdk", "-----focused false-----");
				Focus focus = new Focus();
				focus.setUserId(user.getObjectId());
				focus.setUsername(username.getText().toString());
				focus.setUserimage(userimageuri);
				focus.setContentId(contentId);
				focus.save(this, new SaveListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						System.out.println("focus success");
						focused = true;
						focusBut.setText("ȡ����ע");
						final User newUser = new User();

						newUser.setObjectId(user.getObjectId());
						newUser.setFocusCount(user.getFocusCount() + 1);

						if (intent.getIntExtra("type", 0) == 4) {
							Fans fans = new Fans();
							fans.setUserId(otheruser.getObjectId());
							fans.setUsername(user.getUsername());
							fans.setUserimage(user.getUserImage());
							fans.setContentId(user.getObjectId());
							fans.save(getApplicationContext(),
									new SaveListener() {

										@Override
										public void onSuccess() {
											// TODO Auto-generated method stub
											System.out.println("��˿��ӳɹ�------��");
											newUser.setFansCount(user
													.getFansCount() + 1);
										}

										@Override
										public void onFailure(int arg0,
												String arg1) {
											// TODO Auto-generated method stub
											System.out.println("��˿���ʧ��------��");
										}
									});
						}
						newUser.update(getApplicationContext());
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						System.out.println("focus fail");
					}
				});
				// userFocusOther(user);
				getDBdata();

			}
			break;
		case R.id.i_back:
			finish();
			break;
		default:
			break;
		}
	}

	// private void userFocusOther(final User user2) {
	// // TODO Auto-generated method stub
	// final User currentUser = BmobUser.getCurrentUser(this, User.class);
	// BmobRelation relation = new BmobRelation();
	// relation.add(user2);
	// currentUser.setFocus(relation);
	// // �ڵ�ǰ�û��Ĺ�ע����ӹ�����ϵ user
	// currentUser.update(this, new UpdateListener() {
	//
	// @Override
	// public void onSuccess() {
	// // TODO Auto-generated method stub
	// Log.i("zdk", "------userFocusOther update onSuccess--------");
	// // �ڵ�ǰ�û����focus�����ɹ��󣬰ѵ�ǰ�û���ӵ�user�û���fans������ϵ�С�
	// // ���������û���id
	// // �����ƶ˴��� �������û�user��fans������ϵ��
	// AsyncCustomEndpoints endpoints = new AsyncCustomEndpoints();
	// JSONObject jsonObject = new JSONObject();
	// try {
	// jsonObject.put("objectId", user2.getObjectId());
	// jsonObject.put("objectId1", currentUser.getObjectId());
	//
	// // endpoints.callEndpoint(this, "addFans", jsonObject, new
	// // CloudCodeListener() {
	// //
	// // @Override
	// // public void onSuccess(Object arg0) {
	// // // TODO Auto-generated method stub
	// //
	// // }
	// //
	// // @Override
	// // public void onFailure(int arg0, String arg1) {
	// // // TODO Auto-generated method stub
	// //
	// // }
	// // });
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// }
	//
	// @Override
	// public void onFailure(int arg0, String arg1) {
	// // TODO Auto-generated method stub
	//
	// }
	// });
	// }

	// radiogroup����¼�
	class MyRadioListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			// ��ȡһ����Ƭ������
			FragmentManager fragmentManager = OtherUserInfoActivity.this
					.getSupportFragmentManager();
			// �������� ����
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

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

}
