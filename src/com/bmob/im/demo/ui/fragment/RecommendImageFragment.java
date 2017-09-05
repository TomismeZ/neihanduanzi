package com.bmob.im.demo.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.bean.ImageInfo;
import com.bmob.im.demo.bean.Video;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecommendImageFragment extends Fragment implements OnClickListener{
	List<ImageInfo> images;
	TextView content;
	ImageView imageContent;
	ImageView digdown, digupicon;
	RelativeLayout relativeLayout;
	int index = 0;
	Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.activity_check, null);
		relativeLayout=(RelativeLayout) view.findViewById(R.id.relativeLayout);
		content = (TextView) view.findViewById(R.id.content);
		imageContent=(ImageView) view.findViewById(R.id.image_content);
	
		digdown=(ImageView) view.findViewById(R.id.digdown);
		digupicon=(ImageView) view.findViewById(R.id.digupicon);
		imageContent.setVisibility(View.VISIBLE);
		relativeLayout.setVisibility(View.GONE);
		images = new ArrayList<ImageInfo>();
		intent = getActivity().getIntent();
		digdown.setOnClickListener(this);
		digupicon.setOnClickListener(this);
		getDBdata();
		return view;
	}

	private void getDBdata() {
		// TODO Auto-generated method stub
		BmobQuery<ImageInfo> query = new BmobQuery<ImageInfo>();
		query.order("-createdAt");// “¿’’ ˝æ›≈≈–Ú ±º‰≈≈–Ú
		query.findObjects(getActivity(), new FindListener<ImageInfo>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("search fail!");
			}

			@Override
			public void onSuccess(List<ImageInfo> list) {
				// TODO Auto-generated method stub
				for (ImageInfo imageInfo : list) {
					images.add(imageInfo);
				}
				showImageContent();
				
			}

		});
	}
	private void showImageContent() {
		// TODO Auto-generated method stub
		content.setText(images.get(index).getContent());
	
		if(images.get(index).getLargeImageUri()==null||images.get(index).getLargeImageUri().equals(""))
		{			
			Bitmap bm = BitmapFactory.decodeFile(images.get(index).getMiddleImageUri());						
			imageContent.setAdjustViewBounds(true);
			imageContent.setMaxWidth(260);
			imageContent.setMaxHeight(300);
			imageContent.setImageBitmap(bm);
		}else{
		// –ﬁ∏ƒàD∆¨
			String uri = "http://pb2.pstatp.com/"
				+ images.get(index).getMiddleImageUri();
		Picasso.with(getActivity()).load(uri).placeholder(R.drawable.ic_launcher)
		.into(imageContent);
		}
		

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.digdown:
			if (intent.getIntExtra("type", 0) == 2) {
				images.get(index).setIs_recomment(false);
			}else if(intent.getIntExtra("type", 0) == 1) {
				System.out.println("-----------no pass---------!");
			}
			
			images.get(index).update(getActivity());
			if (index <= images.size()) {
				index++;
			}
			showImageContent();
			break;
		case R.id.digupicon:
			if (intent.getIntExtra("type", 0) == 2) {
				images.get(index).setIs_recomment(true);
			}else if(intent.getIntExtra("type", 0) == 1) {
				System.out.println("-----------pass---------!");
			}
			
			images.get(index).update(getActivity());
			if (index <= images.size()) {
				index++;
			}
			showImageContent();
			break;
		default:
			break;
		}
	}

	

}
