package com.bmob.im.demo.ui.fragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.UserListAdapter;
import com.bmob.im.demo.bean.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class CheckUserFragment extends Fragment{
	ListView lv;
	UserListAdapter adapter;
	private EditText et_lookup;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.sliding_alluser_activity, null);	
		initView(view);
		getUserList();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		lv = (ListView) view.findViewById(R.id.lv_user);
		et_lookup = (EditText)view.findViewById(R.id.et_lookup);
		et_lookup.setVisibility(View.GONE);
	}
	
		// 获取用户列表
		public void getUserList() {
			BmobQuery<User> query = new BmobQuery<User>();
			query.order("-createdAt");
			query.addWhereEqualTo("pass", false);
//			query.addWhereNotEqualTo("admin", true);
			query.findObjects(getActivity(), new FindListener<User>() {

				@Override
				public void onSuccess(List<User> users) {
					// TODO Auto-generated method stub
					adapter = new UserListAdapter(getActivity(), users);
					lv.setAdapter(adapter);
				}

				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub

				}
			});
		}


}
