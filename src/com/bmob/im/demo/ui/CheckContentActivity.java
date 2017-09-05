package com.bmob.im.demo.ui;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.CheckPagerAdapter;
import com.bmob.im.demo.adapter.HomePagerAdapter;
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

public class CheckContentActivity extends FragmentActivity {
	ViewPager viewPager;
	RadioGroup radioGroup;
	ImageView i_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_content);
		initView();
		
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		viewPager=(ViewPager) findViewById(R.id.vp_check);
		radioGroup=(RadioGroup) findViewById(R.id.rg_check);
		i_back=(ImageView) findViewById(R.id.i_back);
		viewPager.setAdapter(new CheckPagerAdapter(this.getSupportFragmentManager()));

		radioGroup.setOnCheckedChangeListener(new CheckRadioListener());
		viewPager.setOnPageChangeListener(new CheckPagerListener());
		i_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	// ���viewpager�ĵ���¼�
	class CheckPagerListener implements OnPageChangeListener {

		// ״̬�ı����
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		// ������
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

			// System.out.println();
			Log.i("xxx", arg0 + "      " + arg1 + "        " + arg2);

		}

		// ������
		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
		}

	}

	// ���radiogroup�ĵ���¼�
	class CheckRadioListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub

			switch (checkedId) {
			case R.id.rb_user:
				viewPager.setCurrentItem(0, false);
				break;
			case R.id.rb_video:
				viewPager.setCurrentItem(1, false);
				break;
			case R.id.rb_image:
				viewPager.setCurrentItem(2, false);
				break;
			case R.id.rb_joke:
				viewPager.setCurrentItem(3, false);
				break;
			
			default:
				break;
			}

		}

	}
}
