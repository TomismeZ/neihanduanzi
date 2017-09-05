package com.bmob.im.demo.ui.fragment;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.FocusListAdapter;
import com.bmob.im.demo.bean.Focus;
import com.bmob.im.demo.bean.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FocusListFragment extends Fragment {

	private ListView lv;
	private FocusListAdapter adapter;
	private User user;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_fans_list, null);
		lv = (ListView) view.findViewById(R.id.lv);
		user=BmobUser.getCurrentUser(getActivity(), User.class);
		getUserFocusList();
		return view;
	}

	/**
	 * 获取用户关注列表
	 */
	public void getUserFocusList() {
		BmobQuery<Focus> query = new BmobQuery<Focus>();
		query.order("-createdAt");
		
		query.addWhereEqualTo("userId", user.getObjectId());
		query.findObjects(getActivity(), new FindListener<Focus>() {

			@Override
			public void onSuccess(List<Focus> list) {
				// TODO Auto-generated method stub
				if (list.size() != 0 && list.get(list.size() - 1) != null) {
//					User newUser=new User();
//					newUser.setFocusCount(list.size());
//					newUser.setObjectId(user.getObjectId());
//					newUser.update(getActivity());
					adapter = new FocusListAdapter(getActivity(), list);
					lv.setAdapter(adapter);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

}
