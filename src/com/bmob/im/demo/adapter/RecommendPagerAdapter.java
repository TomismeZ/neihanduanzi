package com.bmob.im.demo.adapter;

import java.util.ArrayList;
import java.util.List;
import com.bmob.im.demo.ui.fragment.RecommendImageFragment;
import com.bmob.im.demo.ui.fragment.RecommendJokeFragment;
import com.bmob.im.demo.ui.fragment.RecommendVideoFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//FragmentPagerAdapter  PagerAdapter父子类
public class RecommendPagerAdapter extends FragmentPagerAdapter {
	
	List<Fragment> fragments=new  ArrayList<Fragment>();

	//构造方法    初始化一些数据    传参
	public RecommendPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		//初始化3个 fragment
		RecommendVideoFragment videoFragment=new RecommendVideoFragment();
		RecommendImageFragment imageFragment=new RecommendImageFragment();
		RecommendJokeFragment jokeFragment=new RecommendJokeFragment();
		
		fragments.add(videoFragment);
		fragments.add(imageFragment);
		fragments.add(jokeFragment);
	
		
	}

	
	//得到某一个fragment
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return fragments.get(position);
	}

	//页数
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();//5
	}

}
