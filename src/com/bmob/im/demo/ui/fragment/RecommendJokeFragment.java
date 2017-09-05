package com.bmob.im.demo.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.JokeListAdapter;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.Joke;
import com.bmob.im.demo.bean.Video;
import com.bmob.im.demo.db.OperateDb;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecommendJokeFragment extends Fragment implements OnClickListener {
	List<Joke> jokes;
	TextView content;
	ImageView digdown, digupicon;
	RelativeLayout relativeLayout;
	OperateDb operateDb;
	int index = 0;
	Intent intent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.activity_check, null);
		relativeLayout = (RelativeLayout) view
				.findViewById(R.id.relativeLayout);
		content = (TextView) view.findViewById(R.id.content);

		digdown = (ImageView) view.findViewById(R.id.digdown);
		digupicon = (ImageView) view.findViewById(R.id.digupicon);

		relativeLayout.setVisibility(View.GONE);
		jokes = new ArrayList<Joke>();
		intent = getActivity().getIntent();
		digdown.setOnClickListener(this);
		digupicon.setOnClickListener(this);
		operateDb = new OperateDb(getActivity());
		getDBdata();
		return view;
	}

	private void getDBdata() {
		// TODO Auto-generated method stub
		BmobQuery<Joke> query = new BmobQuery<Joke>();
		query.order("-createdAt");// 依照数据排序时间排序
		query.findObjects(getActivity(), new FindListener<Joke>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("search fail!");
			}

			@Override
			public void onSuccess(List<Joke> list) {
				// TODO Auto-generated method stub
				for (Joke joke : list) {
					jokes.add(joke);
				}
				showImageContent();
				
			}

		});

	}

	private void showImageContent() {
		// TODO Auto-generated method stub
		content.setText(jokes.get(index).getContent());

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.digdown:
			if (intent.getIntExtra("type", 0) == 2) {
				jokes.get(index).setIs_recomment(false);
			}else if(intent.getIntExtra("type", 0) == 1) {
				System.out.println("-----------no pass---------!");
			}
			
			jokes.get(index).update(getActivity());
			if (index <= jokes.size()) {
				index++;
			}
			showImageContent();
			break;
		case R.id.digupicon:
			if (intent.getIntExtra("type", 0) == 2) {
				jokes.get(index).setIs_recomment(true);
			}else if(intent.getIntExtra("type", 0) == 1) {
				System.out.println("-----------pass---------!");
			}
			
			jokes.get(index).update(getActivity());
			if (index <= jokes.size()) {
				index++;
			}
			showImageContent();
			break;
		default:
			break;
			
		}
	}

}
