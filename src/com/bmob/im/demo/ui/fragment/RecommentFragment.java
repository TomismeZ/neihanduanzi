package com.bmob.im.demo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.AllInfoListAdapter;
import com.bmob.im.demo.bean.AllInfo;
import com.bmob.im.demo.bean.HotBar;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.Joke;
import com.bmob.im.demo.bean.Video;
import com.bmob.im.demo.ui.HotBarActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RecommentFragment extends Fragment {
	AllInfoListAdapter adapter;
	ListView listView;
	List<AllInfo> allInfos;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.fragment_recomment, null);
		listView = (ListView)view. findViewById(R.id.lv);
		allInfos=new ArrayList<AllInfo>();
		getDBdata();
		return view;
	}
	private void getDBdata() {
		// TODO Auto-generated method stub
		BmobQuery<ImageInfo> query = new BmobQuery<ImageInfo>();
		query.order("-createdAt");// 依照数据排序时间排序
		query.findObjects(getActivity(), new FindListener<ImageInfo>() {
			@Override
			public void onSuccess(List<ImageInfo> list) {
				// TODO Auto-generated method stub
				for (ImageInfo imageInfo : list) {
					AllInfo allInfo=new AllInfo();
					if(imageInfo.isIs_recomment()){
						allInfo.setObjectId(imageInfo.getObjectId());
						allInfo.setUserAvatar(imageInfo.getUserAvatar());
						allInfo.setUserName(imageInfo.getUserName());
						allInfo.setContent(imageInfo.getContent());
						allInfo.setFavoriteCount(imageInfo.getFavoriteCount());
						allInfo.setBuryCount(imageInfo.getBuryCount());
						allInfo.setCommentCount(imageInfo.getCommentCount());
						allInfo.setShareCount(imageInfo.getShareCount());
						allInfo.setMiddleImageUri(imageInfo.getMiddleImageUri());
						allInfo.setLargeImageUri(imageInfo.getLargeImageUri());
						allInfo.setIs_recomment(imageInfo.isIs_recomment());
						
						allInfos.add(allInfo);
					}
					
				}
							
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
		BmobQuery<Video> query1 = new BmobQuery<Video>();
		query1.order("-createdAt");// 依照数据排序时间排序
		query1.findObjects(getActivity(), new FindListener<Video>() {
			@Override
			public void onSuccess(List<Video> list) {
				// TODO Auto-generated method stub
				for (Video video : list) {
					AllInfo allInfo=new AllInfo();
					if(video.isIs_recomment()){
						allInfo.setObjectId(video.getObjectId());
						allInfo.setUserAvatar(video.getUserAvatar());
						allInfo.setUserName(video.getUserName());
						allInfo.setContent(video.getTitle());
						allInfo.setFavoriteCount(video.getFavoriteCount());
						allInfo.setBuryCount(video.getBuryCount());
						allInfo.setCommentCount(video.getCommentCount());
						allInfo.setShareCount(video.getShareCount());
						allInfo.setCoverUrl(video.getCoverUrl());
						allInfo.setVideoUrl(video.getVideoUrl());
						allInfo.setWidth(video.getWidth());
						allInfo.setHeight(video.getHeight());
						allInfo.setIs_recomment(video.isIs_recomment());
						allInfos.add(allInfo);
					}
					
				}
							
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
		BmobQuery<Joke> query2 = new BmobQuery<Joke>();
		query2.order("-createdAt");// 依照数据排序时间排序
		query2.findObjects(getActivity(), new FindListener<Joke>() {
			@Override
			public void onSuccess(List<Joke> list) {
				// TODO Auto-generated method stub
				for (Joke joke : list) {
					AllInfo allInfo=new AllInfo();
					if(joke.isIs_recomment()){
						allInfo.setObjectId(joke.getGroupId()+"");
						allInfo.setUserAvatar(joke.getUserAvatar());
						allInfo.setUserName(joke.getUserName());
						allInfo.setContent(joke.getContent());
						allInfo.setFavoriteCount(joke.getFavoriteCount());
						allInfo.setBuryCount(joke.getBuryCount());
						allInfo.setCommentCount(joke.getCommentCount());
						allInfo.setShareCount(joke.getShareCount());
						
						allInfo.setIs_recomment(joke.isIs_recomment());
						allInfos.add(allInfo);
					}
					
				}
							
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
		adapter = new AllInfoListAdapter(allInfos, getActivity());
		listView.setAdapter(adapter);

	}


}
