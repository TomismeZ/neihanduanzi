package com.bmob.im.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bmob.im.demo.ui.fragment.CheckUserFragment;
import com.bmob.im.demo.ui.fragment.RecommendImageFragment;
import com.bmob.im.demo.ui.fragment.RecommendJokeFragment;
import com.bmob.im.demo.ui.fragment.RecommendVideoFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//FragmentPagerAdapter  PagerAdapter������
public class CheckPagerAdapter extends FragmentPagerAdapter {
	
	List<Fragment> fragments=new  ArrayList<Fragment>();

	//���췽��    ��ʼ��һЩ����    ����
	public CheckPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		//��ʼ��4�� fragment
		CheckUserFragment userFragment=new CheckUserFragment();
		RecommendVideoFragment videoFragment=new RecommendVideoFragment();
		RecommendImageFragment imageFragment=new RecommendImageFragment();
		RecommendJokeFragment jokeFragment=new RecommendJokeFragment();
		fragments.add(userFragment);
		fragments.add(videoFragment);
		fragments.add(imageFragment);
		fragments.add(jokeFragment);
	
		
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
