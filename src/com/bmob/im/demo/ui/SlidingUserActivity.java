package com.bmob.im.demo.ui;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.UserListAdapter;
import com.bmob.im.demo.bean.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class SlidingUserActivity extends Activity implements OnItemClickListener,OnItemLongClickListener{
	private List<User> users;
	ListView lv;
	UserListAdapter adapter;
	private EditText et_lookup;
	private ImageView image_delete;
	private User currentUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.sliding_alluser_activity);
		initView();
		getUserList();
	}

	private void initView() {
		// TODO Auto-generated method stub
		lv = (ListView) findViewById(R.id.lv_user);
		et_lookup = (EditText) findViewById(R.id.et_lookup);
		image_delete = (ImageView) findViewById(R.id.image_delete);
		users=new ArrayList<User>();
		currentUser=BmobUser.getCurrentUser(this, User.class);
		et_lookup.addTextChangedListener(new SearchInfoListener());
		lv.setOnItemClickListener(this);
		lv.setOnItemLongClickListener(this);
	}

	class SearchInfoListener implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!et_lookup.getText().toString().trim().equals("")) {
				String queryClause = et_lookup.getText().toString();
				findByName(queryClause);

				image_delete.setVisibility(View.VISIBLE);
				System.out.println("text is changing");
			} else {
				getUserList();
				image_delete.setVisibility(View.GONE);
				// contacts.clear();
				// 隐藏输入法
				InputMethodManager manager = (InputMethodManager) getApplicationContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				// 显示或者隐藏输入法
				manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

	}

	// 模糊查询，通过用户名来查询
	public void findByName(final String queryClause) {
		users.clear();
		BmobQuery<User> query = new BmobQuery<User>();
		query.order("-createdAt");
		query.addWhereNotEqualTo("objectId", currentUser.getObjectId());
//		query.addWhereContains("username", queryClause);
//		query.addWhereNotEqualTo("admin", true);
//		query.addWhereEqualTo("username", queryClause);
		query.findObjects(this, new FindListener<User>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<User> list) {
				// TODO Auto-generated method stub
				for (User user : list) {
					if(user.getUsername().contains(queryClause))
					
						users.add(user);						
					
				}
				adapter = new UserListAdapter(getApplicationContext(), users);
				lv.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		});
	}

	// 获取用户列表
	public void getUserList() {
		users.clear();
		BmobQuery<User> query = new BmobQuery<User>();
		query.order("-createdAt");
		query.addWhereEqualTo("pass", true);
		query.addWhereNotEqualTo("objectId", currentUser.getObjectId());
//		query.addWhereNotEqualTo("admin", true);
		query.findObjects(this, new FindListener<User>() {

			@Override
			public void onSuccess(List<User> list) {
				// TODO Auto-generated method stub
				for (User user : list) {
					if(user!=null){
						users.add(user);						
					}
				}
				adapter = new UserListAdapter(getApplicationContext(), users);
				lv.setAdapter(adapter);
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("操作");
		builder.setIcon(R.drawable.op);
		builder.setItems(new String[] {"删除","修改"}, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(which==0){
					dialog.dismiss();
					AlertDialog.Builder builder = new AlertDialog.Builder(SlidingUserActivity.this);
					builder.setMessage("确定要删除吗？删除之后就无法回复哦！");
					builder.setTitle("删除");
					builder.setIcon(R.drawable.delete);
					builder.setPositiveButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
					builder.setNegativeButton("取消", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
				}
			}
		});
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		User user=users.get(position);
		Log.e("zdk","user:------"+ user.toString());
		Intent intent=new Intent(getApplicationContext(), OtherUserInfoActivity.class);
		intent.putExtra("otheruser", user);
		intent.putExtra("type", 4);
		startActivity(intent);
	}
}
