package com.bmob.im.demo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.AllInfoListAdapter;
import com.bmob.im.demo.bean.AllInfo;
import com.bmob.im.demo.bean.User;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UserSubmitFragment extends Fragment {
	ListView listView;
	List<AllInfo> allInfos;
	AllInfoListAdapter adapter;
	Intent intent;
	User otheruser,currentUser;
	String userObjectId;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_user_submit, null);
		listView = (ListView) view.findViewById(R.id.lv_userSubmit);
		allInfos=new ArrayList<AllInfo>();
		intent = getActivity().getIntent();
		currentUser = BmobUser.getCurrentUser(getActivity(), User.class);
		userObjectId = currentUser.getObjectId();
		if (intent.getIntExtra("type", 0) == 4) {
			otheruser = (User) intent.getSerializableExtra("otheruser");
			userObjectId=otheruser.getObjectId();
			Log.d("zdk", "用户信息-------------" + otheruser.toString());
		}
		getDBdata();
		return view;
	}

	private void getDBdata() {
		// TODO Auto-generated method stub
		BmobQuery<AllInfo> query = new BmobQuery<AllInfo>();
		query.order("-createdAt");// 依照数据排序时间排序
		query.addWhereEqualTo("userId", userObjectId);
		query.findObjects(getActivity(), new FindListener<AllInfo>() {

			@Override
			public void onSuccess(List<AllInfo> list) {
				// TODO Auto-generated method stub

				for (AllInfo allInfo : list) {
					allInfos.add(allInfo);
				}

				adapter = new AllInfoListAdapter(allInfos, getActivity());
				listView.setAdapter(adapter);
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

}
