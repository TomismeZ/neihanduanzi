package com.bmob.im.demo.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.bmob.im.bean.BmobChatUser;

import com.bmob.im.demo.R;

import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.fragment.CheckFragment;
import com.bmob.im.demo.ui.fragment.DiscoveryFragment;
import com.bmob.im.demo.ui.fragment.HomeFragment;
import com.bmob.im.demo.ui.fragment.MessageFragment;
import com.slidingmenu.lib.SlidingMenu;

//activity fragmentActivity������
public class MainActivity extends FragmentActivity implements OnClickListener{
	TextView sliding_alluser,sliding_check,sliding_recomment;
	RadioGroup group;
	HomeFragment homeFragment;
	DiscoveryFragment discoveryFragment;
	CheckFragment checkFragment;
	MessageFragment messageFragment;
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
			
	}

	private void initView() {
		// TODO Auto-generated method stub
		group = (RadioGroup) findViewById(R.id.radiobutton_main);
		
		user=BmobChatUser.getCurrentUser(this, User.class);
		
		// ��radiogroup��� ����¼�
		// �����ڲ���
		group.setOnCheckedChangeListener(new MyRadioListener());
		//����Ĭ����ҳ
		homeFragment = new HomeFragment();
		this.getSupportFragmentManager().beginTransaction().add(R.id.container, homeFragment).commit();
		if(user!=null&&user.getAdmin()){
			// �໬�˵�
			SlidingMenu slidingMenu = new SlidingMenu(this);
			// ���
			View view=View.inflate(this, R.layout.sliding_menu, null);	
			sliding_alluser=(TextView)view.findViewById(R.id.sliding_alluser);
			sliding_check=(TextView) view.findViewById(R.id.sliding_check);
			sliding_recomment=(TextView) view.findViewById(R.id.sliding_recomment);
			sliding_alluser.setOnClickListener(this);
			sliding_check.setOnClickListener(this);
			sliding_recomment.setOnClickListener(this);
			//Ϊ�໬�˵����ò��� 
			slidingMenu.setMenu(view);
			slidingMenu.setMode(SlidingMenu.LEFT);
			
			 // ���ô�����Ļ��ģʽ 
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//			 slidingMenu.setShadowWidthRes(R.dimen.shadow_width);  
//			 slidingMenu.setShadowDrawable(R.drawable.shadow); 
			 // ���û����˵���ͼ�Ŀ��  
//	        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);  
	        // ���ý��뽥��Ч����ֵ  
//	        slidingMenu.setFadeDegree(0.35f);
			slidingMenu.setBehindOffset(100);
			 
			slidingMenu.attachToActivity(this,
					SlidingMenu.SLIDING_WINDOW);
		}
		
	}

	// ���radiogroup����¼�
	class MyRadioListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
//			this.get
			//һ�� ����ֻ���ύһ��
			//��ȡһ����Ƭ������
			FragmentManager fragmentManager=MainActivity.this.getSupportFragmentManager();
			//��������  ����
			FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
			
//			fragmentTransaction.add(arg0, arg1);
//			fragmentTransaction.remove(arg0);
//			fragmentTransaction.show(arg0);
//			fragmentTransaction.hide(arg0);
//			fragmentTransaction.replace(arg0, arg1);//�滻  fragment�е�����ÿ�λ����¼���
			
			
			//���ĸ���Ƭ��������
			if (homeFragment!=null) {
				fragmentTransaction.hide(homeFragment);
			}
			if (discoveryFragment!=null) {
				fragmentTransaction.hide(discoveryFragment);
			}
			if (checkFragment!=null) {
				fragmentTransaction.hide(checkFragment);
			}
			if (messageFragment!=null) {
				fragmentTransaction.hide(messageFragment);
			}
			
			
			// ͨ��switch�����ĸ�radiobutton
			switch (checkedId) {
			case R.id.rb_home:
				//��ʾ����Ӧ�Ľ���  fragment
				
				if (homeFragment==null) {
					//ʵ����һ�� fragment
					homeFragment=new HomeFragment();
					fragmentTransaction.add(R.id.container, homeFragment);		
					
				}else {
					fragmentTransaction.show(homeFragment);
				}
				
				
				
				break;
			case R.id.rb_discovery:

				if (discoveryFragment==null) {
					discoveryFragment=new DiscoveryFragment();
					fragmentTransaction.add(R.id.container, discoveryFragment);
				}else {
					fragmentTransaction.show(discoveryFragment);
				}
				
				
				break;
			case R.id.rb_check:
				if (checkFragment==null) {
					checkFragment=new CheckFragment();
					fragmentTransaction.add(R.id.container, checkFragment);
				}else {
					fragmentTransaction.show(checkFragment);
				}
				break;
			case R.id.rb_message:
				if (messageFragment==null) {
					messageFragment=new MessageFragment();
					fragmentTransaction.add(R.id.container, messageFragment);
				}else {
					fragmentTransaction.show(messageFragment);
				}
				break;

			default:
				break;
			}

			fragmentTransaction.commit();//�ύ������û���κ�Ч��
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sliding_alluser:
			Intent intent=new Intent(MainActivity.this,SlidingUserActivity.class);
			startActivity(intent);		
			break;
		case R.id.sliding_check:
			Intent intent2=new Intent(MainActivity.this,CheckContentActivity.class);
			intent2.putExtra("type", 1);
			startActivity(intent2);
			break;
		case R.id.sliding_recomment:
			Intent intent3=new Intent(MainActivity.this,RecommendActivity.class);
			intent3.putExtra("type", 2);
			startActivity(intent3);
			break;
		default:
			break;
		}
	}



}
