package com.bmob.im.demo.ui.fragment;

import java.util.List;

import me.maxwin.view.XListView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.FansListAdapter;
import com.bmob.im.demo.adapter.FansPagerAdapter;
import com.bmob.im.demo.adapter.HomePagerAdapter;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.fragment.HomeFragment.HomePagerListener;
import com.bmob.im.demo.ui.fragment.HomeFragment.HomeRadioListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FansFragment extends Fragment {

	ViewPager viewPager;
	RadioGroup radioGroup;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_fans, null);
		viewPager = (ViewPager) view.findViewById(R.id.vp_fans);
		radioGroup = (RadioGroup) view.findViewById(R.id.rg_fans);
		viewPager.setAdapter(new FansPagerAdapter(this
				.getChildFragmentManager()));

		radioGroup.setOnCheckedChangeListener(new FansRadioListener());
		viewPager.setOnPageChangeListener(new FansPagerListener());
		return view;
	}
	
	//你的viewpager的点击事件
		class FansPagerListener implements OnPageChangeListener{

			//状态改变监听
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
			//滑动中
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
//				System.out.println();
				Log.i("xxx",arg0+"      "+arg1+"        "+arg2);
				
			
				
			}
			//滑动后
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				((RadioButton)radioGroup.getChildAt(position)).setChecked(true);
			}
			
		}
		
	//你的radiogroup的点击事件
		class FansRadioListener implements OnCheckedChangeListener{

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				
				switch (checkedId) {
				case R.id.rb_fans:
					viewPager.setCurrentItem(0,false);
					break;
				case R.id.rb_focus:
					viewPager.setCurrentItem(1,false);
					break;
				
				default:
					break;
				}
				
			}
			
		}

}
