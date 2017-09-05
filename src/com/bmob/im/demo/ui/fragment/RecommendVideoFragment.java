package com.bmob.im.demo.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.Video;
import com.squareup.picasso.Picasso;

import android.content.Intent;
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

public class RecommendVideoFragment extends Fragment implements OnClickListener {
	List<Video> videos;
	TextView content;
	SurfaceView surfaceView;
	ImageView cover, playResume;
	ImageView digdown, digupicon;
	ViewGroup.LayoutParams params;
	RelativeLayout relativeLayout;
	int index = 0;
	FrameLayout frame_layout;
	Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.activity_check, null);
		relativeLayout = (RelativeLayout) view
				.findViewById(R.id.relativeLayout);
		content = (TextView) view.findViewById(R.id.content);
		frame_layout = (FrameLayout) view.findViewById(R.id.frame_layout);
		surfaceView = (SurfaceView) view.findViewById(R.id.surface_view);
		cover = (ImageView) view.findViewById(R.id.cover);
		playResume = (ImageView) view.findViewById(R.id.play_resume);
		digdown = (ImageView) view.findViewById(R.id.digdown);
		digupicon = (ImageView) view.findViewById(R.id.digupicon);
		params = cover.getLayoutParams();
		frame_layout.setVisibility(View.VISIBLE);
		relativeLayout.setVisibility(View.GONE);
		videos = new ArrayList<Video>();
		intent = getActivity().getIntent();
		digdown.setOnClickListener(this);
		digupicon.setOnClickListener(this);
		getDBdata();
		return view;
	}

	private void getDBdata() {
		// TODO Auto-generated method stub
		BmobQuery<Video> query = new BmobQuery<Video>();
		query.order("-createdAt");// 依照数据排序时间排序
		query.findObjects(getActivity(), new FindListener<Video>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("search fail!");
			}

			@Override
			public void onSuccess(List<Video> list) {
				// TODO Auto-generated method stub
				for (Video video : list) {
					videos.add(video);
				}
				showVideoContent();

			}

		});
	}

	private void showVideoContent() {
		// TODO Auto-generated method stub
		content.setText(videos.get(index).getTitle());
		params.height = videos.get(index).getHeight();
		params.width = videos.get(index).getWidth();
		cover.requestLayout();
		surfaceView.setLayoutParams(params);
		Picasso.with(getActivity()).load(videos.get(index).getCoverUrl())
				.placeholder(R.drawable.ic_launcher).into(cover);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.digdown:
			if (intent.getIntExtra("type", 0) == 2) {
				videos.get(index).setIs_recomment(false);
			}else if(intent.getIntExtra("type", 0) == 1) {
				System.out.println("-----------no pass---------!");
			}
			videos.get(index).update(getActivity());
			if (index <= videos.size()) {
				index++;
			}
			showVideoContent();
			break;
		case R.id.digupicon:
			if (intent.getIntExtra("type", 0) == 2) {
				videos.get(index).setIs_recomment(true);
			}else if(intent.getIntExtra("type", 0) == 1) {
				System.out.println("-----------pass---------!");
			}
			videos.get(index).update(getActivity());
			if (index <= videos.size()) {
				index++;
			}
			showVideoContent();
			break;
		default:
			break;
		}
	}

}
