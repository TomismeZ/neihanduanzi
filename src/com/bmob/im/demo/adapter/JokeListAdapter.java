package com.bmob.im.demo.adapter;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.Joke;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.ui.CommentActivity;
import com.bmob.im.demo.ui.OtherUserInfoActivity;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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



public class JokeListAdapter extends BaseAdapter{
	List<Joke> jokes;
	Context context;
	public JokeListAdapter(List<Joke> jokes,Context context){
		this.jokes=jokes;
		this.context=context;
	}
	
	//����
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jokes.size();//20
	}

	//��ȡĳһ�У����Բ�д��
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	//��ȡĳһ�е�id�����Բ�д��
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	//ÿһ�еĲ��֣����ӣ�������  30   ��ǰ��Ļ��ʾ �ļ��� ��ִ���ļ��е� getvieww
	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder  holder=null;
//		converView  ���ݸ���
		if (convertView==null) {
			//context=activity/application
			convertView= View.inflate(context, R.layout.item_listview_joke, null);
			
			holder=new ViewHolder();//�ڴ��е�һ����ַ
			
			holder.avatar=(ImageView) convertView.findViewById(R.id.avatar);
			holder.userName=(TextView) convertView.findViewById(R.id.username);
			holder.content=(TextView) convertView.findViewById(R.id.content);
			holder.bury=(TextView) convertView.findViewById(R.id.bury);
			holder.comment=(TextView) convertView.findViewById(R.id.comment);
			holder.farorite=(TextView) convertView.findViewById(R.id.favorite);
			holder.share=(TextView) convertView.findViewById(R.id.share);
			
			
			convertView.setTag(holder);
			 
			
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.content.setText(jokes.get(position).getContent());
		holder.userName.setText(jokes.get(position).getUserName());
		holder.farorite.setText(jokes.get(position).getFavoriteCount()+"");
		holder.bury.setText(jokes.get(position).getBuryCount()+"");
		holder.comment.setText(jokes.get(position).getCommentCount()+"");
		holder.share.setText(jokes.get(position).getShareCount()+"");
		//ͼƬͷ��
		//��ַ  ���߳�  ����  bitmap handler ��ʾͼƬ
		//�Ż�ͼƬ   �ڴ�  ���棨������ ����      
		//������xutils  picasso  fresco
		
		BitmapUtils bitmapUtils=new BitmapUtils(context);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
		bitmapUtils.display(holder.avatar, jokes.get(position).getUserAvatar(),new BitmapLoadCallBack<View>() {

			@Override
			public void onLoadCompleted(View arg0, String arg1, Bitmap arg2,
					BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
				// TODO Auto-generated method stub
				
				
				Bitmap bitmap=BitmapUtil.getRoundedCornerBitmap(arg2);
 
				((ImageView)arg0).setImageBitmap(bitmap);
				
				
			}

			@Override
			public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
				// TODO Auto-generated method stub
				
			}
		});
		//�����¼�
		holder.farorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Joke joke=jokes.get(position);
				int favoritecount=joke.getFavoriteCount();
				joke.setFavoriteCount(favoritecount+1);
				
				joke.update(context, new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(context, "���޳ɹ�", Toast.LENGTH_SHORT).show();
						notifyDataSetChanged();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(context, "����ʧ��", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
		});
		//�����¼�
		holder.comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Joke joke=jokes.get(position);
				Intent intent=new Intent(context,CommentActivity.class);
				intent.putExtra("joke", joke);
				intent.putExtra("type", 1);
				
				Toast.makeText(context, "��ת�����۽�Ŀ��", Toast.LENGTH_SHORT).show();
				context.startActivity(intent);
			}
		});
		//��
		holder.bury.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Joke joke1=jokes.get(position);
				int burrcount=joke1.getBuryCount();
				joke1.setBuryCount(burrcount+1);
				joke1.update(context, new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(context, "�ȳɹ�", Toast.LENGTH_SHORT).show();
						notifyDataSetChanged();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(context, "��ʧ��", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
		});
		///����
		holder.share.setOnClickListener(new ShareClickListener());
		
		holder.avatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Joke joke = jokes.get(position);
				
				final User currentUser = BmobUser.getCurrentUser(context, User.class);
							
				Intent intent=new Intent(context,OtherUserInfoActivity.class);
				intent.putExtra("user", currentUser);
				intent.putExtra("joke", joke);
				intent.putExtra("type", 1);
				context.startActivity(intent);
			}
		});
		return convertView;
	}
	
	//��ķ��� ����¼�
	class ShareClickListener  implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			showShare();
		}
		
	}
	
	class ViewHolder {
		
		public ImageView avatar;
		public TextView userName;
		public TextView content;
		public TextView farorite;
		public TextView bury;
		public TextView comment;
		public TextView share;
			
		
	}

	
	private void showShare() {
		 ShareSDK.initSDK(context);
		 OnekeyShare oks = new OnekeyShare();
		 //�ر�sso��Ȩ
		 oks.disableSSOWhenAuthorize(); 

		// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		 oks.setTitle( "xxx");
		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		 oks.setText("���Ƿ����ı�");
		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		 oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		 oks.setUrl("http://sharesdk.cn");
		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		 oks.setComment("���ǲ��������ı�");
		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		 oks.setSite("http://www.baidu.com");
		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		 oks.setSiteUrl("http://sharesdk.cn");

		// ��������GUI
		 oks.show(context);
		 }
}
