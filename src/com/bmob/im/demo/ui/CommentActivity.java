package com.bmob.im.demo.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.CommentAdapter;
import com.bmob.im.demo.bean.AllInfo;
import com.bmob.im.demo.bean.Comment;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.Joke;
import com.bmob.im.demo.bean.User;
import com.bmob.im.demo.bean.Video;
import com.bmob.im.demo.util.BitmapUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends Activity implements OnClickListener {
	private ImageView avatar, imageContent;
	private TextView userName;
	private TextView content;
	private TextView farorite;
	private TextView bury;
	private TextView comment;
	private TextView share;
	private TextView hide_down;
	private EditText comment_content;
	private Button comment_send;
	private RelativeLayout rl_comment;
	private ListView lv_comment;
	private ArrayList<Comment> comments;
	private CommentAdapter adapter;
	private String contentId;
	BmobUserManager userManager;
	Intent intent;
	Joke joke;
	ImageInfo image;
	Video video;
	AllInfo allInfo;
	User userInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		avatar = (ImageView) findViewById(R.id.avatar);
		userName = (TextView) findViewById(R.id.username);
		content = (TextView) findViewById(R.id.content);
		farorite = (TextView) findViewById(R.id.favorite);
		bury = (TextView) findViewById(R.id.bury);
		comment = (TextView) findViewById(R.id.comment);
		share = (TextView) findViewById(R.id.share);
		lv_comment = (ListView) findViewById(R.id.lv_comment);
		hide_down = (TextView) findViewById(R.id.hide_down);
		comment_content = (EditText) findViewById(R.id.comment_content);
		comment_send = (Button) findViewById(R.id.comment_send);
		rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
		imageContent = (ImageView) findViewById(R.id.image_content);
		comment.setOnClickListener(this);
		hide_down.setOnClickListener(this);
		comment_send.setOnClickListener(this);
		userManager = BmobUserManager.getInstance(this);
		userInfo = (User) userManager.getCurrentUser(User.class);
		intent = getIntent();
		if (intent.getIntExtra("type", 0) == 1) {
			joke = (Joke) intent.getSerializableExtra("joke");
			contentId = joke.getGroupId() + "";
			userName.setText(joke.getUserName());
			content.setText(joke.getContent());
			farorite.setText(String.valueOf(joke.getFavoriteCount()));
			bury.setText(String.valueOf(joke.getBuryCount()));
			comment.setText(String.valueOf(joke.getCommentCount()));
			share.setText(String.valueOf(joke.getShareCount()));

			BitmapUtils bitmapUtils = new BitmapUtils(this);
			bitmapUtils
					.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
			bitmapUtils.display(avatar, joke.getUserAvatar(),
					new BitmapLoadCallBack<View>() {

						@Override
						public void onLoadCompleted(View arg0, String arg1,
								Bitmap arg2, BitmapDisplayConfig arg3,
								BitmapLoadFrom arg4) {
							// TODO Auto-generated method stub

							Bitmap bitmap = BitmapUtil
									.getRoundedCornerBitmap(arg2);

							((ImageView) arg0).setImageBitmap(bitmap);

						}

						@Override
						public void onLoadFailed(View arg0, String arg1,
								Drawable arg2) {
							// TODO Auto-generated method stub

						}
					});
		} else if (intent.getIntExtra("type", 0) == 2) {
			imageContent.setVisibility(View.VISIBLE);
			image = (ImageInfo) intent.getSerializableExtra("image");
			contentId = image.getObjectId();
			userName.setText(image.getUserName());
			content.setText(image.getContent());
			farorite.setText(image.getFavoriteCount() + "");
			bury.setText(image.getBuryCount() + "");
			comment.setText(image.getCommentCount() + "");
			share.setText(image.getShareCount() + "");
			String uri = "http://pb2.pstatp.com/" + image.getMiddleImageUri();
			Picasso.with(this).load(image.getUserAvatar())
					.placeholder(R.drawable.ic_launcher).into(avatar);
			Picasso.with(this).load(uri).placeholder(R.drawable.ic_launcher)
					.into(imageContent);
		} else if (intent.getIntExtra("type", 0) == 3) {
			imageContent.setVisibility(View.VISIBLE);
			video = (Video) intent.getSerializableExtra("video");
			contentId = video.getObjectId();
			userName.setText(video.getUserName());
			content.setText(video.getTitle());
			farorite.setText(video.getFavoriteCount() + "");
			bury.setText(video.getBuryCount() + "");
			comment.setText(video.getCommentCount() + "");
			share.setText(video.getShareCount() + "");

			Picasso.with(this).load(video.getUserAvatar())
					.placeholder(R.drawable.ic_launcher).into(avatar);
			Picasso.with(this).load(video.getCoverUrl())
					.placeholder(R.drawable.ic_launcher).into(imageContent);
		} else if (intent.getIntExtra("type", 0) == 4) {
			allInfo = (AllInfo) intent.getSerializableExtra("allInfo");
			contentId = allInfo.getObjectId();
			userName.setText(allInfo.getUserName());
			content.setText(allInfo.getContent());
			farorite.setText(allInfo.getFavoriteCount() + "");
			bury.setText(allInfo.getBuryCount() + "");
			comment.setText(allInfo.getCommentCount() + "");
			share.setText(allInfo.getShareCount() + "");
			BitmapUtils bitmapUtils = new BitmapUtils(this);
			bitmapUtils
					.configDefaultLoadFailedImage(R.drawable.scroll_banner_level_fail);
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.ic_nearby_empty_list);
			bitmapUtils.display(avatar, allInfo.getUserAvatar(),
					new BitmapLoadCallBack<View>() {

						@Override
						public void onLoadCompleted(View arg0, String arg1,
								Bitmap arg2, BitmapDisplayConfig arg3,
								BitmapLoadFrom arg4) {
							// TODO Auto-generated method stub

							Bitmap bitmap = BitmapUtil
									.getRoundedCornerBitmap(arg2);

							((ImageView) arg0).setImageBitmap(bitmap);

						}

						@Override
						public void onLoadFailed(View arg0, String arg1,
								Drawable arg2) {
							// TODO Auto-generated method stub

						}
					});
			if (allInfo.getMiddleImageUri() != "") {
				imageContent.setVisibility(View.VISIBLE);
				// Picasso.with(this).load(allInfo.getCoverUrl())
				// .placeholder(R.drawable.ic_launcher).into(imageContent);
				Bitmap bm = BitmapFactory.decodeFile(allInfo
						.getMiddleImageUri());
				imageContent.setAdjustViewBounds(true);
				imageContent.setMaxWidth(150);
				imageContent.setMaxHeight(150);
				imageContent.setImageBitmap(bm);
			}
		}

		queryComment(contentId);

	}

	private void queryComment(String contentId) {
		// TODO Auto-generated method stub

		BmobQuery<Comment> bmobQuery = new BmobQuery<Comment>();
		bmobQuery.order("-createdAt");// 依照数据排序时间排序

		bmobQuery.addWhereEqualTo("contentId", contentId);
		bmobQuery.findObjects(getApplicationContext(),
				new FindListener<Comment>() {

					@Override
					public void onSuccess(List<Comment> comments) {
						// TODO Auto-generated method stub

						adapter = new CommentAdapter(getApplicationContext(),
								comments);
						lv_comment.setAdapter(adapter);
						lv_comment.setVerticalScrollBarEnabled(true);
						lv_comment.setSelection(0);

					}

					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						System.out.println("失败了");
					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.comment:
			// 弹出输入法
			InputMethodManager imm = (InputMethodManager) getApplicationContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			// 显示评论框
			lv_comment.setVisibility(View.GONE);
			rl_comment.setVisibility(View.VISIBLE);
			break;
		case R.id.hide_down:
			// 隐藏评论框
			lv_comment.setVisibility(View.VISIBLE);
			rl_comment.setVisibility(View.GONE);
			// 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
			InputMethodManager im = (InputMethodManager) getApplicationContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
			break;
		case R.id.comment_send:
			if (userInfo != null) {
				sendComment();
			}else{
				Toast.makeText(this, "请登录后才能评论哦！", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	private void sendComment() {
		// TODO Auto-generated method stub
		if (comment_content.getText().toString().equals("")) {
			Toast.makeText(this, "评论不能为空！", Toast.LENGTH_LONG).show();
		} else {
			// 生成评论数据

			Comment comment = new Comment();
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
			comment.setCreateTime(sdf.format(now));
//			comment.setCommentId(comment.getObjectId());
			comment.setUserId(userInfo.getObjectId());
			comment.setUserName(userInfo.getUsername());
			comment.setUserProfileImageUrl(userInfo.getUserImage());
			comment.setText(comment_content.getText().toString());
			// comments.add(comment);

			// comment.setGroupId(contentId);
			comment.setContentId(contentId);
			comment.save(this, new SaveListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					System.out
							.println("-----------------------評論成功------------------------！");
					queryComment(contentId);
					adapter.notifyDataSetChanged();
					lv_comment.setVisibility(View.VISIBLE);
					rl_comment.setVisibility(View.GONE);

				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					System.out
							.println("-----------------------------評論失敗------------------------------！");
				}
			});

		}
	}

}
