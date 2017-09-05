package com.bmob.im.demo.ui.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.ImageListAdapter;
import com.bmob.im.demo.adapter.VideoListAdapter;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.Video;
import com.bmob.im.demo.ui.fragment.ImageFragment.MyXListListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class VideoFragment extends Fragment {
	private static final int PAGE_SIZE = 10;
	private int videoPageNum = 0 ; 
	private XListView lv;
	String time;
	private VideoListAdapter adapter;
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			// date calendar
			// Calendar calendar=Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
			time = dateFormat.format(new Date());
			// 停止 刷新
			stopLoad();

			getDBdata(false);
			// listview adapter jokes
			// listView.setAdapter(new JokeListAdapter(jokes,
			// JokeFragment.this.getActivity()));
		};

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_video, null);
		lv = (XListView) view.findViewById(R.id.lv);
		lv.setPullLoadEnable(true);
		// 给你的xlistview 添加一个刷新监听
		lv.setXListViewListener(new MyXListListener());
		getDBdata(true);// 10

		return view;
	}

	public void getDBdata(final boolean isFirst) {
		BmobQuery<Video> query = new BmobQuery<Video>();
//		query.setLimit(PAGE_SIZE);
//		query.setSkip(PAGE_SIZE*(videoPageNum++));
		query.order("-createdAt");// 依照数据排序时间排序

		query.findObjects(getActivity(), new FindListener<Video>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("zdk", "视频查询失败");
			}

			@Override
			public void onSuccess(List<Video> list) {
				// TODO Auto-generated method stub
				if (list != null && list.size() > 0) {
					stopLoad();
					if (isFirst) {
						adapter = new VideoListAdapter(list, VideoFragment.this
								.getActivity());
						lv.setAdapter(adapter);
					} else {

						adapter.notifyDataSetChanged();
					}
				} else {
					Log.i("zdk", "查询成功，无数据返回");
				}
			}
		});
	}

	// 停止刷新
	private void stopLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		lv.setRefreshTime(time);
	}

	class MyXListListener implements IXListViewListener {

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			initData();
		}

		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			// 数据库
			getDBdata(false);
		}

	}

	private void initData() {
		try {
			StringBuffer result = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					getActivity().getAssets().open("video1.json")));
			String str;
			// 读取文件数据
			while ((str = br.readLine()) != null) {
				result.append(str);
			}
			br.close();
			// 解析json数据
			parseJson(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseJson(String json) {
		// list = new ArrayList<Video>();
		try {
			JSONArray dataArr = new JSONObject(json).getJSONObject("data")
					.getJSONArray("data");
			for (int i = 0; i < dataArr.length(); i++) {
				try {
					JSONObject group = dataArr.getJSONObject(i).getJSONObject(
							"group");
					// String p360Video =
					// group.getJSONObject("360p_video").getJSONArray("url_list").getJSONObject(0).getString("url");
					// String title = group.getString("title");
					// int video_height = group.getInt("video_height");
					// int favoriteCount =group.getInt("favorite_count");
					// int commentCount =group.getInt("comment_count");
					// int buryCount =group.getInt("bury_count");
					// int shareCount =group.getInt("share_count");
					// String shareUrl =group.getString("share_url");
					// String userName
					// =group.getJSONObject("user").getString("name");
					// String userAvatar
					// =group.getJSONObject("user").getString("avatar_url");
					// String coverUrl =
					// group.getJSONObject("medium_cover").getJSONArray("url_list").getJSONObject(0).getString("url");
					// int video_width = group.getInt("video_width");
					Video video = new Video();
					video.parseJson(group);
					video.save(getActivity(), new SaveListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							System.out.println("视频保存成功");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							System.out.println("视频保存失败");
						}
					});
					// list.add(new Video(userAvatar, userName, p360Video,
					// coverUrl, video_width, video_height, title,
					// favoriteCount, buryCount, commentCount, shareUrl,
					// shareCount));
				} catch (JSONException e) {
					// e.printStackTrace();
				}
			}
			handler.sendEmptyMessage(1);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
