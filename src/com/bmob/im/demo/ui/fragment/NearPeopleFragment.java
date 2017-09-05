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
	private double QUERY_KILOMETERS = 10;//Ĭ�ϲ�ѯ10���ﷶΧ�ڵ���
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.activity_near_people, null);
		mListView = (XListView)view. findViewById(R.id.list_near);
		
		mApplication = CustomApplcation.getInstance();
		userManager = BmobUserManager.getInstance(getActivity());
		mListView.setOnItemClickListener(this);
		// ���Ȳ�������ظ���
		mListView.setPullLoadEnable(false);
		// ��������
		mListView.setPullRefreshEnable(true);
		// ���ü�����
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
			progress.setMessage("���ڲ�ѯ��������...");
			progress.setCanceledOnTouchOutside(true);
			progress.show();
		}
		if(!mApplication.getLatitude().equals("")&&!mApplication.getLongtitude().equals("")){
			double latitude = Double.parseDouble(mApplication.getLatitude());
			double longtitude = Double.parseDouble(mApplication.getLongtitude());
			//��װ�Ĳ�ѯ�������������ҳ��ʱ isUpdateΪfalse��������ˢ�µ�ʱ������Ϊtrue���С�
			//�˷���Ĭ��ÿҳ��ѯ10������,�����ѯ����10�������ڲ�ѯ֮ǰ����BRequest.QUERY_LIMIT_COUNT���磺BRequest.QUERY_LIMIT_COUNT=20
			// �˷����������Ĳ�ѯָ��10�����ڵ��Ա�ΪŮ�Ե��û��б�Ĭ�ϰ��������б�
			//����㲻���ѯ�Ա�ΪŮ���û������Խ�equalProperty��Ϊnull����equalObj��Ϊnull����
			
			userManager.queryKiloMetersListByPage(isUpdate,0,"location", longtitude, latitude, true,QUERY_KILOMETERS,"sex",true,new FindListener<User>() {
			//�˷���Ĭ�ϲ�ѯ���д�����λ����Ϣ�����Ա�ΪŮ���û��б�����㲻����������б�Ļ�������ѯ�����е�isShowFriends����Ϊfalse����
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
							Toast.makeText(getActivity(), "���������������!", Toast.LENGTH_SHORT).show();
							
						}else{
							mListView.setPullLoadEnable(true);
						}
					}else{
						Toast.makeText(getActivity(), "���޸�������!", Toast.LENGTH_SHORT).show();
						
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
					Toast.makeText(getActivity(), "���޸�������!", Toast.LENGTH_SHORT).show();
					mListView.setPullLoadEnable(false);
					if(!isUpdate){
						progress.dismiss();
					}else{
						refreshPull();
					}
				}

			});
		}else{
			Toast.makeText(getActivity(), "���޸�������!", Toast.LENGTH_SHORT).show();
			progress.dismiss();
			refreshPull();
		}
		
	}
	/** ��ѯ����
	  * @Title: queryMoreNearList
	  * @Description: TODO
	  * @param @param page 
	  * @return void
	  * @throws
	  */
	private void queryMoreNearList(int page){
		double latitude = Double.parseDouble(mApplication.getLatitude());
		double longtitude = Double.parseDouble(mApplication.getLongtitude());
		//��ѯ10���ﷶΧ�ڵ��û��б�
		userManager.queryKiloMetersListByPage(true,page,"location", longtitude, latitude, true,QUERY_KILOMETERS,"sex",false,new FindListener<User>() {
		//��ѯȫ������λ����Ϣ���Ա�ΪŮ�Ե��û��б�
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
				Log.e("zdk", "��ѯ���฽�����˳���:"+arg1);
				
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
		//���ǲ�ѯ10���ﷶΧ���û�����
		userManager.queryKiloMetersTotalCount(User.class, "location", longtitude, latitude, true,QUERY_KILOMETERS,"sex",null,new CountListener() {
	    //���ǲ�ѯ�����������Ա�ΪŮ�Ե��û�����
//		userManager.queryNearTotalCount(User.class, "location", longtitude, latitude, true,"sex",false,new CountListener() {
			
			@Override
			public void onSuccess(int arg0) {
				// TODO Auto-generated method stub
				if(arg0 >nears.size()){
					curPage++;
					queryMoreNearList(curPage);
				}else{
					Toast.makeText(getActivity(), "���ݼ������", Toast.LENGTH_SHORT).show();
					
					mListView.setPullLoadEnable(false);
					refreshLoad();
				}
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("zdk", "��ѯ������������ʧ��"+arg1);				
				refreshLoad();
			}
		});
		
	}
	

}
