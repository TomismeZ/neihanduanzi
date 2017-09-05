package com.bmob.im.demo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.im.BmobUserManager;
import cn.bmob.im.task.BRequest;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.NearPeopleAdapter;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.util.CollectionUtils;
import com.bmob.im.demo.view.xlist.XListView;
import com.bmob.im.demo.view.xlist.XListView.IXListViewListener;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class NearPeopleFragment extends Fragment implements IXListViewListener,OnItemClickListener{
	XListView mListView;
	NearPeopleAdapter adapter;
	CustomApplcation mApplication;
	BmobUserManager userManager;
	String from = "";
	List<User> nears = new ArrayList<User>();
	private double QUERY_KILOMETERS = 10;//默认查询10公里范围内的人
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.activity_near_people, null);
		mListView = (XListView)view. findViewById(R.id.list_near);
		
		mApplication = CustomApplcation.getInstance();
		userManager = BmobUserManager.getInstance(getActivity());
		mListView.setOnItemClickListener(this);
		// 首先不允许加载更多
		mListView.setPullLoadEnable(false);
		// 允许下拉
		mListView.setPullRefreshEnable(true);
		// 设置监听器
		mListView.setXListViewListener(this);
		//
		mListView.pullRefreshing();
		
		adapter = new NearPeopleAdapter(getActivity(), nears);
		mListView.setAdapter(adapter);
		initNearByList(false);
		return view;
	}
	int curPage = 0;
	ProgressDialog progress ;
	private void initNearByList(final boolean isUpdate){
		if(!isUpdate){
			progress = new ProgressDialog(getActivity());
			progress.setMessage("正在查询附近的人...");
			progress.setCanceledOnTouchOutside(true);
			progress.show();
		}
		if(!mApplication.getLatitude().equals("")&&!mApplication.getLongtitude().equals("")){
			double latitude = Double.parseDouble(mApplication.getLatitude());
			double longtitude = Double.parseDouble(mApplication.getLongtitude());
			//封装的查询方法，当进入此页面时 isUpdate为false，当下拉刷新的时候设置为true就行。
			//此方法默认每页查询10条数据,若想查询多于10条，可在查询之前设置BRequest.QUERY_LIMIT_COUNT，如：BRequest.QUERY_LIMIT_COUNT=20
			// 此方法是新增的查询指定10公里内的性别为女性的用户列表，默认包含好友列表
			//如果你不想查询性别为女的用户，可以将equalProperty设为null或者equalObj设为null即可
			
			userManager.queryKiloMetersListByPage(isUpdate,0,"location", longtitude, latitude, true,QUERY_KILOMETERS,"sex",true,new FindListener<User>() {
			//此方法默认查询所有带地理位置信息的且性别为女的用户列表，如果你不想包含好友列表的话，将查询条件中的isShowFriends设置为false就行
//			userManager.queryNearByListByPage(isUpdate,0,"location", longtitude, latitude, true,"sex",false,new FindListener<User>() {

				@Override
				public void onSuccess(List<User> arg0) {
					// TODO Auto-generated method stub
					if (CollectionUtils.isNotNull(arg0)) {
						if(isUpdate){
							nears.clear();
						}
						adapter.addAll(arg0);
						if(arg0.size()<BRequest.QUERY_LIMIT_COUNT){
							mListView.setPullLoadEnable(false);
							Toast.makeText(getActivity(), "附近的人搜索完成!", Toast.LENGTH_SHORT).show();
							
						}else{
							mListView.setPullLoadEnable(true);
						}
					}else{
						Toast.makeText(getActivity(), "暂无附近的人!", Toast.LENGTH_SHORT).show();
						
					}
					
					if(!isUpdate){
						progress.dismiss();
					}else{
						refreshPull();
					}
				}
				
				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "暂无附近的人!", Toast.LENGTH_SHORT).show();
					mListView.setPullLoadEnable(false);
					if(!isUpdate){
						progress.dismiss();
					}else{
						refreshPull();
					}
				}

			});
		}else{
			Toast.makeText(getActivity(), "暂无附近的人!", Toast.LENGTH_SHORT).show();
			progress.dismiss();
			refreshPull();
		}
		
	}
	/** 查询更多
	  * @Title: queryMoreNearList
	  * @Description: TODO
	  * @param @param page 
	  * @return void
	  * @throws
	  */
	private void queryMoreNearList(int page){
		double latitude = Double.parseDouble(mApplication.getLatitude());
		double longtitude = Double.parseDouble(mApplication.getLongtitude());
		//查询10公里范围内的用户列表
		userManager.queryKiloMetersListByPage(true,page,"location", longtitude, latitude, true,QUERY_KILOMETERS,"sex",false,new FindListener<User>() {
		//查询全部地理位置信息且性别为女性的用户列表
//		userManager.queryNearByListByPage(true,page, "location", longtitude, latitude, true,"sex",false,new FindListener<User>() {

			@Override
			public void onSuccess(List<User> arg0) {
				// TODO Auto-generated method stub
				if (CollectionUtils.isNotNull(arg0)) {
					adapter.addAll(arg0);
				}
				refreshLoad();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("zdk", "查询更多附近的人出错:"+arg1);
				
				mListView.setPullLoadEnable(false);
				refreshLoad();
			}

		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		User user = (User) adapter.getItem(position-1);
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		initNearByList(true);
	}

	private void refreshLoad(){
		if (mListView.getPullLoading()) {
			mListView.stopLoadMore();
		}
	}
	
	private void refreshPull(){
		if (mListView.getPullRefreshing()) {
			mListView.stopRefresh();
		}
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		double latitude = Double.parseDouble(mApplication.getLatitude());
		double longtitude = Double.parseDouble(mApplication.getLongtitude());
		//这是查询10公里范围内用户总数
		userManager.queryKiloMetersTotalCount(User.class, "location", longtitude, latitude, true,QUERY_KILOMETERS,"sex",null,new CountListener() {
	    //这是查询附近的人且性别为女性的用户总数
//		userManager.queryNearTotalCount(User.class, "location", longtitude, latitude, true,"sex",false,new CountListener() {
			
			@Override
			public void onSuccess(int arg0) {
				// TODO Auto-generated method stub
				if(arg0 >nears.size()){
					curPage++;
					queryMoreNearList(curPage);
				}else{
					Toast.makeText(getActivity(), "数据加载完成", Toast.LENGTH_SHORT).show();
					
					mListView.setPullLoadEnable(false);
					refreshLoad();
				}
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("zdk", "查询附近的人总数失败"+arg1);				
				refreshLoad();
			}
		});
		
	}
	

}
