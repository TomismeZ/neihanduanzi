package com.bmob.im.demo.ui;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.FansPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FansActivity extends FragmentActivity {
	ViewPager viewPager;
	RadioGroup radioGroup;
	ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.fragment_fans);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager)findViewById(R.id.vp_fans);
		radioGroup = (RadioGroup)findViewById(R.id.rg_fans);
		back=(ImageView) findViewById(R.id.i_back);
		viewPager.setAdapter(new FansPagerAdapter(this.getSupportFragmentManager()));
		radioGroup.setOnCheckedChangeListener(new FansRadioListener());
		viewPager.setOnPageChangeListener(new FansPagerListener());
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
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
					
//					System.out.println();
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
