package com.bmob.im.demo.ui.fragment;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.FansListAdapter;
import com.bmob.im.demo.bean.Fans;
import com.bmob.im.demo.bean.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FansListFragment extends Fragment {

	private ListView lv;
	private FansListAdapter adapter;
	private User user;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_fans_list, null);
		lv = (ListView) view.findViewById(R.id.lv);
		user=BmobUser.getCurrentUser(getActivity(), User.class);
		getUserFansList();
		return view;
	}

	/**
	 * 获取用户粉丝列表
	 */
	public void getUserFansList() {
		BmobQuery<Fans> query = new BmobQuery<Fans>();
		query.order("-createdAt");
		
		query.addWhereEqualTo("userId", user.getObjectId());
		query.findObjects(getActivity(), new FindListener<Fans>() {

			@Override
			public void onSuccess(List<Fans> list) {
				// TODO Auto-generated method stub
				if (list.size() != 0 && list.get(list.size() - 1) != null) {
//					User newUser=new User();
//					newUser.setFansCount(list.size()+1);
//					newUser.setObjectId(user.getObjectId());
//					newUser.update(getActivity());
					adapter = new FansListAdapter(getActivity(), list);
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
