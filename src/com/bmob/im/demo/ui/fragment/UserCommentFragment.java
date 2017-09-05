package com.bmob.im.demo.ui.fragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.CommentAdapter;
import com.bmob.im.demo.bean.Comment;
import com.bmob.im.demo.bean.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UserCommentFragment extends Fragment {
	private ListView lv_comment;
	private CommentAdapter adapter;
	private Intent intent;
	private String userObjectId;
	private User currentUser, otheruser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_comment, null);
		lv_comment = (ListView) view.findViewById(R.id.lv_comment);
		currentUser = BmobUser.getCurrentUser(getActivity(), User.class);
		userObjectId = currentUser.getObjectId();
		intent = getActivity().getIntent();
		if (intent.getIntExtra("type", 0) == 4) {
			otheruser = (User) intent.getSerializableExtra("otheruser");
			userObjectId = otheruser.getObjectId();
		}
		queryComment();
		return view;
	}

	private void queryComment() {
		// TODO Auto-generated method stub

		BmobQuery<Comment> bmobQuery = new BmobQuery<Comment>();
		bmobQuery.order("-createdAt");// “¿’’ ˝æ›≈≈–Ú ±º‰≈≈–Ú
		bmobQuery.addWhereEqualTo("userId", userObjectId);
		bmobQuery.findObjects(getActivity(), new FindListener<Comment>() {

			@Override
			public void onSuccess(List<Comment> comments) {
				// TODO Auto-generated method stub

				adapter = new CommentAdapter(getActivity(), comments);
				lv_comment.setAdapter(adapter);
				lv_comment.setVerticalScrollBarEnabled(true);
				lv_comment.setSelection(0);

			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println(" ß∞‹¡À");
			}
		});
	}

}
