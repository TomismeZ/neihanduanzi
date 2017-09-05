package com.bmob.im.demo.ui;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.AllInfoListAdapter;
import com.bmob.im.demo.bean.AllInfo;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckActivity extends Activity implements OnClickListener {
	ImageView userImage, image_content;
	ImageView contribute, digdown, digupicon;
	User userInfo;
	Intent intent;
	List<AllInfo> allInfos;
	TextView content;
	int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check);

		initView();
		getDBdata();
	}

	private void getDBdata() {
		// TODO Auto-generated method stub
		BmobQuery<AllInfo> query = new BmobQuery<AllInfo>();
		query.order("-createdAt");// 依照数据排序时间排序
		query.findObjects(this, new FindListener<AllInfo>() {

			@Override
			public void onSuccess(List<AllInfo> list) {
				// TODO Auto-generated method stub

				for (AllInfo allInfo : list) {
					allInfos.add(allInfo);
				}
				content.setText(allInfos.get(index).getContent());
				if (allInfos.get(index).getMiddleImageUri() != null
						&& allInfos.get(index).getLargeImageUri() == null) {
					image_content.setVisibility(View.VISIBLE);
					Bitmap bm = BitmapFactory.decodeFile(allInfos.get(index)
							.getMiddleImageUri());
					image_content.setAdjustViewBounds(true);
					image_content.setMaxWidth(150);
					image_content.setMaxHeight(150);
					image_content.setImageBitmap(bm);
				} else if (allInfos.get(index).getMiddleImageUri() != null
						&& allInfos.get(index).getLargeImageUri() != null) {
					image_content.setVisibility(View.VISIBLE);
					String uri = "http://pb2.pstatp.com/"
							+ allInfos.get(index).getMiddleImageUri();
					Picasso.with(getApplicationContext()).load(uri)
							.placeholder(R.drawable.ic_launcher)
							.into(image_content);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("search fail!");
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		userImage = (ImageView) findViewById(R.id.i_userImage);
		contribute = (ImageView) findViewById(R.id.t_contribute);
		content = (TextView) findViewById(R.id.content);
		image_content = (ImageView) findViewById(R.id.image_content);
		digdown = (ImageView) findViewById(R.id.digdown);

		intent = getIntent();
		userInfo = (User) intent.getSerializableExtra("userInfo");
		userImage.setAdjustViewBounds(true);
		userImage.setMaxWidth(80);
		userImage.setMaxHeight(80);
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
		allInfos = new ArrayList<AllInfo>();
		userImage.setOnClickListener(this);
		contribute.setOnClickListener(this);
		digdown.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.i_userImage:
			Intent intent1 = new Intent(CheckActivity.this,
					UserManageActivity.class);
			intent1.putExtra("userInfo", userInfo);
			startActivity(intent1);
			break;
		case R.id.t_contribute:
			Intent intent2 = new Intent(CheckActivity.this,
					SubmitActivity.class);
			intent2.putExtra("userInfo", userInfo);
			startActivity(intent2);
			break;
		case R.id.digdown:

			allInfos.get(index).setBuryCount(
					allInfos.get(index).getBuryCount() + 1);
			// allInfos.get(index).update(this);
			if (index < allInfos.size()) {
				index++;
			}else if(index==allInfos.size()){
				index=0;
			}
			content.setText(allInfos.get(index).getContent());
			if (allInfos.get(index).getMiddleImageUri() != null
					&& allInfos.get(index).getLargeImageUri() == null) {
				image_content.setVisibility(View.VISIBLE);
				Bitmap bm = BitmapFactory.decodeFile(allInfos.get(index)
						.getMiddleImageUri());
				image_content.setAdjustViewBounds(true);
				image_content.setMaxWidth(150);
				image_content.setMaxHeight(150);
				image_content.setImageBitmap(bm);
			} else if (allInfos.get(index).getMiddleImageUri() != null
					&& allInfos.get(index).getLargeImageUri() != null) {
				image_content.setVisibility(View.VISIBLE);
				String uri = "http://pb2.pstatp.com/"
						+ allInfos.get(index).getMiddleImageUri();
				Picasso.with(getApplicationContext()).load(uri)
						.placeholder(R.drawable.ic_launcher)
						.into(image_content);
			}
			break;
		default:
			break;
		}
	}
}
