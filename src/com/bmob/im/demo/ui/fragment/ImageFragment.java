package com.bmob.im.demo.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.ImageListAdapter;
import com.bmob.im.demo.adapter.JokeListAdapter;
import com.bmob.im.demo.bean.Image;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.ui.fragment.JokeFragment.MyXListListener;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ImageFragment extends Fragment {
	private static final int PAGE_SIZE = 5;
	XListView listView;
	String time;
	int currentIndex = 0;
	ImageListAdapter adapter;
	// 作用：发消息 收消息
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

		View view = inflater.inflate(R.layout.fragment_image, null);
		listView = (XListView) view.findViewById(R.id.lv_image);

		listView.setPullLoadEnable(true);
		// 给你的xlistview 添加一个刷新监听
		listView.setXListViewListener(new MyXListListener());
		getDBdata(true);// 10
		return view;
	}

	public void getDBdata(final boolean isFirst) {

		BmobQuery<ImageInfo> query = new BmobQuery<ImageInfo>();
//		query.setLimit(PAGE_SIZE);
//		query.setSkip(PAGE_SIZE * (currentIndex));
//		currentIndex = currentIndex + 5;
		query.order("-createdAt");

		query.findObjects(getActivity(), new FindListener<ImageInfo>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("zdk", "查询失败");
			}

			@Override
			public void onSuccess(List<ImageInfo> list) {
				// TODO Auto-generated method stub
				if (list != null && list.size() > 0) {
					System.out.println("查询成功" + currentIndex);

					stopLoad();
					if (isFirst) {
						adapter = new ImageListAdapter(list, ImageFragment.this
								.getActivity());
						listView.setAdapter(adapter);
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
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(time);
	}

	class MyXListListener implements IXListViewListener {

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			getNetData();
		}

		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			// 数据库
			getDBdata(false);
		}

	}

	public void getNetData() {
		HttpUtils httpUtils = new HttpUtils();

		// http://ic.snssdk.com/2/essay/zone/category/data/?category_id=2&level=6&count=30
		httpUtils
				.send(HttpMethod.GET,
						"http://ic.snssdk.com/2/essay/zone/category/data/?category_id=2&level=6&count=30",
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// TODO Auto-generated method stub

								String result = arg0.result;
								System.out.println(result);
								// gson 映射
								// {}--->类
								// []--->list<类>

								Gson gson = new Gson();
								Image imageData = gson.fromJson(result,
										Image.class);

								// listview 图片/gif 自带的刷新
								ImageInfo image = new ImageInfo();

								for (int i = 0; i < 10; i++) {
									if (imageData.data.data.get(i).group != null) {
										image.parseImage(imageData, i);
										System.out.println("imageInfo的值："
												+ image.getUserName() + "第" + i);
										image.save(getActivity(),
												new SaveListener() {

													@Override
													public void onSuccess() {
														// TODO Auto-generated
														// method stub
														System.out
																.println("保存成功");
													}

													@Override
													public void onFailure(
															int arg0,
															String arg1) {
														// TODO Auto-generated
														// method stub

													}
												});
									}
								}
								// listView.setAdapter(new ImageListAdapter(
								// imageData, getActivity()));
								// handler消息机制
								handler.sendEmptyMessage(1);
							}
						});
	}

}
