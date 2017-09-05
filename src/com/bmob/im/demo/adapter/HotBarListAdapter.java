package com.bmob.im.demo.adapter;

import java.util.List;

import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.HotBar;
import com.bmob.im.demo.bean.Joke;
import com.bmob.im.demo.ui.CommentActivity;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//adapterview ---> viewpager listview
//viewpager--->pagerAdapter/fragmentPagerAdapter
//listview--->baseAdapter



public class HotBarListAdapter extends BaseAdapter{
	List<HotBar> hotbars;
	Context context;

	public HotBarListAdapter(List<HotBar> hotbars,Context context){
		this.hotbars=hotbars;
		this.context=context;
		
	}
	
	//����
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hotbars.size();//20
	}

	//��ȡĳһ�У����Բ�д��
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return hotbars.get(position);
	}
	//��ȡĳһ�е�id�����Բ�д��
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	//ÿһ�еĲ��֣����ӣ�������  30   ��ǰ��Ļ��ʾ �ļ��� ��ִ���ļ��е� getvieww
	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder  holder=null;
//		converView  ���ݸ���
		if (convertView==null) {
			//context=activity/application
			convertView= View.inflate(context, R.layout.item_listview_hot, null);
			
			holder=new ViewHolder();//�ڴ��е�һ����ַ
			holder.avatar=(ImageView) convertView.findViewById(R.id.avatar);
			holder.name=(TextView) convertView.findViewById(R.id.name);
			holder.describe=(TextView) convertView.findViewById(R.id.describe);
			holder.subscribe=(Button) convertView.findViewById(R.id.subscribe);
			holder.subscribe_count=(TextView) convertView.findViewById(R.id.subscribe_count);
			holder.noteCount=(TextView) convertView.findViewById(R.id.noteCount);
			convertView.setTag(holder);
			 
			
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final HotBar hotbar=hotbars.get(position);
		holder.name.setText(hotbar.getName());
		holder.describe.setText(hotbar.getDescribe());
		holder.subscribe_count.setText(hotbar.getSubscribeCount()+"����|");
		holder.noteCount.setText("������"+hotbar.getNoteCount());
		Picasso.with(context).load(hotbar.getAvatar()).placeholder(R.drawable.user_image).into(holder.avatar);		
		if(hotbar.isSubscribe()){
			holder.subscribe.setVisibility(View.GONE);
			
		}
		//�����¼�
		holder.subscribe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hotbar.setSubscribe(true);
				hotbar.setSubscribeCount(hotbar.getSubscribeCount()+1);
				hotbar.update(context);
				hotbars.remove(position);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	
	
	
	class ViewHolder {
		ImageView avatar;
		Button subscribe;
		TextView name;
		TextView describe;
		TextView subscribe_count;
		TextView noteCount;				
	}

	
}
