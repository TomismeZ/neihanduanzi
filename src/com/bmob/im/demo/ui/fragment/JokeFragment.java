package com.bmob.im.demo.ui.fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.JokeListAdapter;
import com.bmob.im.demo.adapter.VideoListAdapter;
import com.bmob.im.demo.bean.Comment;
import com.bmob.im.demo.bean.Joke;
import com.bmob.im.demo.db.OperateDb;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import android.database.Cursor;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class JokeFragment extends Fragment {
//	private static final int PAGE_SIZE = 10;
	// 做一个集合用来放解析出来的joke
	List<Joke> jokes = new ArrayList<Joke>();
	XListView listView;
	String time;
	OperateDb operateDb;
	int currentIndex = 0;
	JokeListAdapter adapter;
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
//	private int jokePageNum=5;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		operateDb = new OperateDb(getActivity());
		View view = inflater.inflate(R.layout.fragment_joke, null);
		listView = (XListView) view.findViewById(R.id.lv_joke);
		// 首先不允许加载更多
		listView.setPullLoadEnable(true);

		// 给你的xlistview 添加一个刷新监听
		listView.setXListViewListener(new MyXListListener());

		getDBdata(true);// 10

		// getNetData();

		return view;
	}
	
	public void getDBdata(final boolean isFirst) {
		String sql = "select *  from joke order by id desc limit ?,10";
		Cursor cursor = operateDb.queryDB(sql, currentIndex + "");
		currentIndex += 10;
		while (cursor.moveToNext()) {
			String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
			String username = cursor.getString(cursor
					.getColumnIndex("username"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			int favorite = cursor.getInt(cursor.getColumnIndex("favorite"));
			int bury = cursor.getInt(cursor.getColumnIndex("bury"));
			int comment = cursor.getInt(cursor.getColumnIndex("comment"));
			int shareCount = cursor.getInt(cursor.getColumnIndex("shareCount"));
			String share = cursor.getString(cursor.getColumnIndex("share"));
			long groupId = cursor.getLong(cursor.getColumnIndex("groupId"));
			// 封装数据

			Joke joke = new Joke();
			joke.setBuryCount(bury);
			joke.setCommentCount(comment);
			joke.setContent(content);
			joke.setFavoriteCount(favorite);
			joke.setShareUrl(share);
			joke.setUserAvatar(avatar);
			joke.setUserName(username);
			joke.setShareCount(shareCount);
			joke.setGroupId(groupId);
			jokes.add(joke);// 10

		}

		stopLoad();
		if (isFirst) {
			adapter = new JokeListAdapter(jokes,
					JokeFragment.this.getActivity());
			listView.setAdapter(adapter);
		} else {

			adapter.notifyDataSetChanged();
		}
		
//		BmobQuery<Joke> query=new BmobQuery<Joke>();
//		query.setLimit(PAGE_SIZE);
//		query.setSkip(PAGE_SIZE*(jokePageNum));
//		query.order("-createdAt");// 依照数据排序时间排序
//		jokePageNum=jokePageNum+5;
//		query.findObjects(getActivity(), new FindListener<Joke>() {
//
//			@Override
//			public void onError(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onSuccess(List<Joke> list) {
//				// TODO Auto-generated method stub
//				if (list != null && list.size() > 0) {
//					stopLoad();
//					if (isFirst) {
//						adapter = new JokeListAdapter(list, JokeFragment.this
//								.getActivity());
//						listView.setAdapter(adapter);
//					} else {
//
//						adapter.notifyDataSetChanged();
//					}
//				} else {
//					Log.i("zdk", "查询成功，无数据返回");
//				}
//			}
//		});

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

	// NetworkOnMainThreadException
	// 联网等比较耗时的操作必须放到子线程 中，否则会影响主线程 控制界面
	// 联网 获取网络数据
	public void getNetData() {
		// httpUrlConnection httpClient（6.0没了 apache）
		// 第三方xutils okhttp volley asyncHttpClient

		// 子线程
		new Thread() {
			public void run() {
				try {
					URL url = new URL(
							"http://ic.snssdk.com/2/essay/zone/category/data/?category_id=1&level=6&count=30");
					HttpURLConnection httpURLConnection = (HttpURLConnection) url
							.openConnection();
					// httpURLConnection.setRequestMethod(method)
					// httpURLConnection.setReadTimeout(timeoutMillis)
					// httpURLConnection.setConnectTimeout(timeoutMillis)
					httpURLConnection.setDoOutput(true);
					httpURLConnection.setDoInput(true);
					// 动词
					httpURLConnection.connect();
					// 得到服务器给的数据流 低级字节流
					InputStream inputStream = httpURLConnection
							.getInputStream();
					// httpURLConnection.getOutputStream();
					// 高级字符流
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(inputStream));

					String s = null;
					// 可追加字符串
					StringBuffer buffer = new StringBuffer();
					while ((s = bufferedReader.readLine()) != null) {
						buffer.append(s);
					}

					// System.out.println(buffer.toString());
					parseJson(buffer.toString());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();

	}

	// json解析
	public void parseJson(String json) {

		jokes.clear();
		// {}---》jsonobject
		// []---》jsonarray
		try {
			JSONObject jsonObject = new JSONObject(json);
			// jsonObject.getString("message");
			JSONObject jsonObject2 = jsonObject.getJSONObject("data");
			// jsonObject2.getBoolean("has_more");
			String tip = jsonObject2.getString("tip");
			JSONArray jsonArray = jsonObject2.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				if (jsonObject3.has("group")) {
					JSONObject jsonObject4 = jsonObject3.getJSONObject("group");
					String content = jsonObject4.getString("content");
					int favoriteCount = jsonObject4.getInt("favorite_count");
					JSONObject jsonObject5 = jsonObject4.getJSONObject("user");
					String shareUrl = jsonObject4.getString("share_url");
					int commentCount = jsonObject4.getInt("comment_count");
					int buryCount = jsonObject4.getInt("bury_count");
					int shareCount = jsonObject4.getInt("share_count");
					String userName = jsonObject5.getString("name");
					String userAvatar = jsonObject5.getString("avatar_url");
					long groupId = jsonObject4.getLong("group_id");
					System.out.println(userName);
//插入到SQlite数据库
					String sql = "insert into joke (avatar , username, content,favorite, bury , comment,share,shareCount,groupId) "
							+ "values (?,?,?,?,?,?,?,?,?)";
					operateDb.updateDB(sql, userAvatar, userName, content,
							favoriteCount, buryCount, commentCount, shareUrl,
							shareCount, groupId);

//					 插入bmob数据库
					 Joke joke = new Joke();
					 joke.setBuryCount(buryCount);
					 joke.setCommentCount(commentCount);
					 joke.setContent(content);
					 joke.setFavoriteCount(favoriteCount);
					 joke.setShareUrl(shareUrl);
					 joke.setUserAvatar(userAvatar);
					 joke.setShareCount(shareCount);
					 joke.setUserName(userName);
					 joke.setGroupId(groupId);
					 joke.save(getActivity(), new SaveListener() {
					
					 @Override
					 public void onSuccess() {
					 // TODO Auto-generated method stub
					 System.out.println("添加数据成功");
					 }
					
					 @Override
					 public void onFailure(int arg0, String arg1) {
					 // TODO Auto-generated method stub
					 System.out.println("添加数据失败");
					 }
					 });

				}
			

			}
			// android中规定：不允许在子线程中更改界面

			// handler消息机制
			handler.sendEmptyMessage(1);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
