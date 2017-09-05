package com.bmob.im.demo.ui;

import java.io.File;
import java.io.FileNotFoundException;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.AllInfo;
import com.bmob.im.demo.bean.HotBar;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.bean.Video;
import com.bmob.im.demo.db.OperateDb;
import com.squareup.picasso.Picasso;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SumPathEffect;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitActivity extends Activity implements
		OnCheckedChangeListener, OnClickListener {
	private static final int REQUESTCODE_2 = 0x12;
	private static final int REQUESTCODE_1 = 0x11;
	private RadioGroup group;
	private RadioButton s_joke;
	private RadioButton s_image, s_video;
	private EditText s_content;
	private TextView s_submit;
	private ImageView sg_delete, sg_image, back;
	BmobUserManager userManager;
	OperateDb operateDb;
	User userInfo;
	Intent intent;
	HotBar hotBar;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		group = (RadioGroup) findViewById(R.id.rg_submit);
		s_joke = (RadioButton) findViewById(R.id.s_joke);
		s_image = (RadioButton) findViewById(R.id.s_image);
		s_video = (RadioButton) findViewById(R.id.s_video);
		s_content = (EditText) findViewById(R.id.s_content);
		s_submit = (TextView) findViewById(R.id.s_submit);
		sg_image = (ImageView) findViewById(R.id.sg_image);
		sg_delete = (ImageView) findViewById(R.id.sg_delete);
		back = (ImageView) findViewById(R.id.i_back);
		group.setOnCheckedChangeListener(this);
		s_submit.setOnClickListener(this);
		s_image.setOnClickListener(this);
		s_video.setOnClickListener(this);
		sg_delete.setOnClickListener(this);
		operateDb = new OperateDb(this);
		back.setOnClickListener(this);
		userManager = BmobUserManager.getInstance(this);

		userInfo = userManager.getCurrentUser(User.class);
		intent = getIntent();
		if (intent.getIntExtra("type", 0) == 2) {
			hotBar = (HotBar) intent.getSerializableExtra("hotBar");
			s_content.setHint(hotBar.getDescribe());
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.s_submit:
			AllInfo allInfo = new AllInfo();
			if (intent.getIntExtra("type", 0) == 1) {
				if (s_joke.isChecked()) {
					int groupId = 1;
					String sql = "insert into joke (avatar , username, content,favorite, bury , comment,share,shareCount,groupId) "
							+ "values (?,?,?,?,?,?,?,?,?)";
					operateDb.updateDB(sql, userInfo.getUserImage(), userInfo
							.getUsername(), s_content.getText().toString(), 0,
							0, 0, "path", 0, groupId);

					Toast.makeText(this, "文字投稿成功", Toast.LENGTH_LONG).show();
				}
				allInfo.setUserId(userInfo.getObjectId());
				allInfo.setUserAvatar(userInfo.getUserImage());
				allInfo.setUserName(userInfo.getUsername());
				allInfo.setContent(s_content.getText().toString());
				if (s_image.isChecked()) {
					ImageInfo image = new ImageInfo();
					image.setUserAvatar(userInfo.getUserImage());
					image.setUserName(userInfo.getUsername());
					image.setContent(s_content.getText().toString());
					image.setMiddleImageUri(path);
					image.save(this);

					allInfo.setMiddleImageUri(path);
				}
				if (s_video.isChecked()) {
					Video video = new Video();
					video.setUserAvatar(userInfo.getUserImage());
					video.setUserName(userInfo.getUsername());
					video.setTitle(s_content.getText().toString());
					video.setVideoUrl(path);
					video.setCoverUrl(path);
					video.save(this);

					allInfo.setVideoUrl(path);
					allInfo.setCoverUrl(path);
				}
				allInfo.save(getApplicationContext());
			} else if (intent.getIntExtra("type", 0) == 2) {
				AllInfo info = new AllInfo();

				info.setHotBarId(hotBar.getObjectId());
				info.setUserId(userInfo.getObjectId());
				info.setUserAvatar(userInfo.getUserImage());
				info.setUserName(userInfo.getUsername());
				info.setContent(s_content.getText().toString());
				if (s_image.isChecked()) {
					info.setMiddleImageUri(path);
				}
				if (s_video.isChecked()) {
					info.setVideoUrl(path);
					info.setCoverUrl(path);
				}
				info.save(this);
			}
			Intent intent2 = new Intent(SubmitActivity.this, MainActivity.class);
			startActivity(intent2);
			break;
		case R.id.s_image:
			Intent intent1 = new Intent();
			/* 开启Pictures画面Type设定为image */
			intent1.setType("image/*");
			/* 使用Intent.ACTION_GET_CONTENT这个Action */
			intent1.setAction(Intent.ACTION_GET_CONTENT);
			/* 取得相片后返回本画面 */
			startActivityForResult(intent1, REQUESTCODE_1);
			break;
		case R.id.s_video:
			
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("video/*");
			Intent wrapperIntent = Intent.createChooser(intent, null);
			startActivityForResult(wrapperIntent, REQUESTCODE_2);
			break;
		case R.id.sg_delete:
			sg_image.setVisibility(View.GONE);
			sg_delete.setVisibility(View.GONE);
			s_joke.setChecked(true);
			s_image.setChecked(false);
			s_video.setChecked(false);
			break;
		case R.id.i_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.s_image:

			break;
		case R.id.s_video:

			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE_1 && resultCode == RESULT_OK) { // 判断是否为待处理的结果
			Uri uri = data.getData();
			path = getImagePath(uri, null);

			ContentResolver cr = this.getContentResolver();
			try {
				Log.e("zdk", path.toString());
				Bitmap bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(uri));
				sg_image.setVisibility(View.VISIBLE);
				sg_delete.setVisibility(View.VISIBLE);
				sg_image.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e("zdk", e.getMessage(), e);
			}

		}
		if (requestCode == REQUESTCODE_2 && resultCode == RESULT_OK) {
			Uri uri = data.getData();
			path = getVideoPath(uri, null);
			Log.i("zdk", "-----" + path.toString());
			File file = new File(path);
			MediaMetadataRetriever mmr = new MediaMetadataRetriever();// 实例化MediaMetadataRetriever对象
			mmr.setDataSource(file.getAbsolutePath());
			Bitmap bitmap = mmr.getFrameAtTime();// 获得视频第一帧的Bitmap对象
			String duration = mmr
					.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);// 时长(毫秒)
			Log.d("ddd", "duration==" + duration);
			int int_duration = Integer.parseInt(duration);
			if (int_duration > 300000) {
				Toast.makeText(getApplicationContext(), "视频时长已超过5分钟，请重新选择",
						Toast.LENGTH_SHORT).show();
			}

			sg_image.setVisibility(View.VISIBLE);
			sg_delete.setVisibility(View.VISIBLE);
			sg_image.setImageBitmap(bitmap);
			

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

	private String getVideoPath(Uri uri, String seletion) {
		String path = null;

		Cursor cursor = this.getContentResolver().query(uri, null, null, null,
				MediaStore.Video.Media.DEFAULT_SORT_ORDER);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
			}
			cursor.close();

		}
		return path;
	}

}
