package com.bmob.im.demo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.HotBarListAdapter;
import com.bmob.im.demo.bean.HotBar;
import com.bmob.im.demo.ui.HotBarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BookFragment extends Fragment implements OnItemClickListener{
	private ListView lv;
	private HotBarListAdapter adapter;
	private List<HotBar> hotbars;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.fragment_book, null);
		lv=(ListView) view.findViewById(R.id.lv);
		hotbars=new ArrayList<HotBar>();
		getDBdata();
		lv.setOnItemClickListener(this);
		return view;
	}
	private void getDBdata() {
		// TODO Auto-generated method stub
		BmobQuery<HotBar> hotQuery=new BmobQuery<HotBar>();
		hotQuery.findObjects(getActivity(), new FindListener<HotBar>() {
			
			@Override
			public void onSuccess(List<HotBar> list) {
				// TODO Auto-generated method stub				
				for (HotBar hotBar : list) {
					if(hotBar.isSubscribe()){
						hotbars.add(hotBar);
					}
				}
				adapter = new HotBarListAdapter(hotbars, BookFragment.this
						.getActivity());
				lv.setAdapter(adapter);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("zdk", "查询失败");
			}
		});
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		Toast.makeText(getActivity(), "Item被点击了"+position, Toast.LENGTH_SHORT).show();
		HotBar hotbar=hotbars.get(position);
//		Log.e("zdk", hotbar.toString());
		Intent intent=new Intent(getActivity(),HotBarActivity.class);
		intent.putExtra("hotbar", hotbar);
		startActivity(intent);
	}

}
