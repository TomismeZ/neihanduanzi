package com.bmob.im.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bmob.im.demo.ui.fragment.BookFragment;
import com.bmob.im.demo.ui.fragment.ImageFragment;
import com.bmob.im.demo.ui.fragment.JokeFragment;
import com.bmob.im.demo.ui.fragment.RecommentFragment;
import com.bmob.im.demo.ui.fragment.VideoFragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
//FragmentPagerAdapter  PagerAdapter父子类
public class HomePagerAdapter extends FragmentPagerAdapter {
	
	List<Fragment> fragments=new  ArrayList<Fragment>();

	//构造方法    初始化一些数据    传参
	public HomePagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		//初始化5个 fragment
		RecommentFragment recommentFragment=new RecommentFragment();
		VideoFragment videoFragment=new VideoFragment();
		ImageFragment imageFragment=new ImageFragment();
		JokeFragment jokeFragment=new JokeFragment();
		BookFragment bookFragment=new BookFragment();
		
		fragments.add(recommentFragment);
		fragments.add(videoFragment);
		fragments.add(imageFragment);
		fragments.add(jokeFragment);
		fragments.add(bookFragment);
		
		
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
