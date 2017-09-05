package com.bmob.im.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bmob.im.demo.ui.fragment.BookFragment;
import com.bmob.im.demo.ui.fragment.FansListFragment;
import com.bmob.im.demo.ui.fragment.FocusListFragment;
import com.bmob.im.demo.ui.fragment.ImageFragment;
import com.bmob.im.demo.ui.fragment.JokeFragment;
import com.bmob.im.demo.ui.fragment.RecommentFragment;
import com.bmob.im.demo.ui.fragment.VideoFragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
//FragmentPagerAdapter  PagerAdapter������
public class FansPagerAdapter extends FragmentPagerAdapter {
	
	List<Fragment> fragments=new  ArrayList<Fragment>();

	//���췽��    ��ʼ��һЩ����    ����
	public FansPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		//��ʼ��2�� fragment
		FansListFragment fansListFragment=new FansListFragment();
		FocusListFragment focusListFragment=new FocusListFragment();
			
		fragments.add(fansListFragment);
		fragments.add(focusListFragment);
		
		
	}

	
	//�õ�ĳһ��fragment
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return fragments.get(position);
	}

	//ҳ��
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();//5
	}

}
