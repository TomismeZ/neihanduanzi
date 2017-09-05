package com.bmob.im.demo.ui;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.RecommendPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RecommendActivity extends FragmentActivity {
	ViewPager viewPager;
	RadioGroup radioGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recomment);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		viewPager=(ViewPager) findViewById(R.id.vp_recommend);
		radioGroup=(RadioGroup) findViewById(R.id.rg_recommend);
		viewPager.setAdapter(new RecommendPagerAdapter(this.getSupportFragmentManager()));

		radioGroup.setOnCheckedChangeListener(new RecommendRadioListener());
		viewPager.setOnPageChangeListener(new HomePagerListener());
	}
	// 你的viewpager的点击事件
		class HomePagerListener implements OnPageChangeListener {

			// 状态改变监听
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			// 滑动中
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

				// System.out.println();
				Log.i("xxx", arg0 + "      " + arg1 + "        " + arg2);

			}

			// 滑动后
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
			}

		}

		// 你的radiogroup的点击事件
		class RecommendRadioListener implements OnCheckedChangeListener {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch (checkedId) {				
				case R.id.rb_video:
					viewPager.setCurrentItem(0, false);
					break;
				case R.id.rb_image:
					viewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_joke:
					viewPager.setCurrentItem(2, false);
					break;			
				default:
					break;
				}

			}

		}
}
