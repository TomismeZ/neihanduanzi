package com.bmob.im.demo.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import com.bmob.im.demo.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ShowImageActivity extends Activity{
	
	ImageView  imageView;
	GifImageView gifImageView;
	String uri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showiamge );
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
		imageView=(ImageView) this.findViewById(R.id.larger_image);
		gifImageView = (GifImageView) findViewById(R.id.myGifView);
		Intent intent=this.getIntent();
		  uri=intent.getStringExtra("uri");
		int isgif=intent.getIntExtra("isgif", 0);
		
		if (isgif==1) {
			//动态的
			
			///异步任务    子线程 handler
			new AsyncTask<Void, Void, byte[]>() {

				//子线程
				@Override
				protected byte[] doInBackground(Void... params) {
					byte[] gifbyte = null;
					HttpURLConnection conn = null;
					try {
						URL url = new URL(uri);
						conn = (HttpURLConnection) url.openConnection();
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						InputStream in = conn.getInputStream();
						if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
							// 连接不成功
							return null;
						}

						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
						gifbyte = out.toByteArray();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						conn.disconnect();
					}


					return gifbyte;

				}

				//handler
				
				protected void onPostExecute(byte[] gifbyte) {

					 
					 try {
						GifDrawable drawable=new GifDrawable(gifbyte);
						gifImageView.setBackgroundDrawable(drawable);
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				};

			}.execute();

		}else {
			//普通的
			Picasso.with(this).load(uri).into(imageView);
		}
		
		
		
	}

}
