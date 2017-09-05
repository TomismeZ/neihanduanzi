package com.bmob.im.demo.ui;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.AllInfoListAdapter;
import com.bmob.im.demo.bean.AllInfo;
import com.bmob.im.demo.bean.HotBar;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HotBarActivity extends Activity implements OnClickListener {
	TextView name;
	ImageView avatar;
	Button subscribe;
	TextView describe;
	TextView subscribeCount;
	TextView noteCount;
	ImageView contribute,i_back;
	BmobUserManager userManager;
	Intent intent;
	HotBar hotBar;
	ListView listView;
	List<AllInfo> allInfos;
	AllInfoListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotbar);
		initView();
		getDBdata();
	}

	private void initView() {
		// TODO Auto-generated method stub
		name = (TextView) findViewById(R.id.name);
		avatar = (ImageView) findViewById(R.id.avatar);
		subscribe = (Button) findViewById(R.id.subscribe);
		describe = (TextView) findViewById(R.id.describe);
		subscribeCount = (TextView) findViewById(R.id.subscribe_count);
		noteCount = (TextView) findViewById(R.id.noteCount);
		contribute = (ImageView) findViewById(R.id.t_contribute);
		listView = (ListView) findViewById(R.id.lv);
		i_back=(ImageView) findViewById(R.id.i_back);
		userManager = BmobUserManager.getInstance(this);
		allInfos = new ArrayList<AllInfo>();
		intent = getIntent();
		hotBar = (HotBar) intent.getSerializableExtra("hotbar");
		// Log.i("zdk", hotBar.toString());
		name.setText(hotBar.getName());
		describe.setText(hotBar.getDescribe());
		subscribeCount.setText(hotBar.getSubscribeCount() + "人参与 |");
		noteCount.setText(hotBar.getNoteCount() + "贴子");
		if (hotBar.isSubscribe()) {
			subscribe.setText("取消订阅");
		} else {
			subscribe.setText("订阅");
		}
		Picasso.with(this).load(hotBar.getAvatar())
				.placeholder(R.drawable.user_image).into(avatar);
		subscribe.setOnClickListener(this);
		contribute.setOnClickListener(this);
		i_back.setOnClickListener(this);
	}

	private void getDBdata() {
		// TODO Auto-generated method stub
		BmobQuery<AllInfo> query = new BmobQuery<AllInfo>();
		query.order("-createdAt");// 依照数据排序时间排序
		query.addWhereEqualTo("hotBarId", hotBar.getObjectId());
		query.findObjects(this, new FindListener<AllInfo>() {

			@Override
			public void onSuccess(List<AllInfo> list) {
				// TODO Auto-generated method stub
				
				for (AllInfo allInfo : list) {
					allInfos.add(allInfo);
				}
				hotBar.setNoteCount(allInfos.size());
				hotBar.update(getApplicationContext());
				adapter = new AllInfoListAdapter(allInfos, HotBarActivity.this);
				listView.setAdapter(adapter);
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.subscribe:

			break;
		case R.id.t_contribute:
			Intent intent1 = new Intent(HotBarActivity.this,
					SubmitActivity.class);

			intent1.putExtra("hotBar", hotBar);
			intent1.putExtra("type", 2);
			startActivity(intent1);
			break;
		case R.id.i_back:
			finish();
			break;
		default:
			break;
		}
	}

}
