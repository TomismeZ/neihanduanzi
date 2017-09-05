package com.bmob.im.demo.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

public class BitmapUtil {


    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            //��֤�Ƿ��Σ����Ҵ����Ļ�
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int w;
            int deltaX = 0;
            int deltaY = 0;
            if (width <= height) {
                w = width;
                deltaY = height - w;
            } else {
                w = height;
                deltaX = width - w;
            }
            final Rect rect = new Rect(deltaX, deltaY, w, w);
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            //Բ�Σ�����ֻ��һ��
            
            int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
            canvas.drawRoundRect(rectF, radius, radius, paint);

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        }

    public synchronized Drawable byteToDrawable(String icon) {  
        
		byte[] img=Base64.decode(icon.getBytes(), Base64.DEFAULT);
		Bitmap bitmap;  
        if (img != null) {  
  
              
            bitmap = BitmapFactory.decodeByteArray(img,0, img.length);  
            @SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(bitmap);  
              
            return drawable;  
        }  
        return null;  
  
    }
	public  synchronized  String drawableToByte(Drawable drawable) {  
        
        if (drawable != null) {  
            Bitmap bitmap = Bitmap  
                    .createBitmap(  
                            drawable.getIntrinsicWidth(),  
                            drawable.getIntrinsicHeight(),  
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
                                    : Bitmap.Config.RGB_565);  
            Canvas canvas = new Canvas(bitmap);  
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
                    drawable.getIntrinsicHeight());  
            drawable.draw(canvas);  
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;  
          
            // ����һ���ֽ����������,���Ĵ�СΪsize  
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);  
            // ����λͼ��ѹ����ʽ������Ϊ100%���������ֽ������������  
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);  
            // ���ֽ����������ת��Ϊ�ֽ�����byte[]  
            byte[] imagedata = baos.toByteArray();  
            
           String icon= Base64.encodeToString(imagedata, Base64.DEFAULT);
            return icon;  
        }  
        return null;  
    }

}
