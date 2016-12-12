package com.example.administrator.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.api.Server;
import com.example.administrator.myapplication.entity.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/12.
 */

public class AvatarView extends View {

    Paint paint;
    Handler handler = new Handler();
    float radius;

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(paint!=null){
            canvas.drawCircle(getWidth()/2, getHeight()/2, radius, paint);
        }
    }

   public void load(User user) {

        OkHttpClient client = Server.getShareClient();
        Request request = new Request.Builder().method("get", null).url(Server.SERVER_ADDRESS + user.getAvatar()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               try {
                   byte[] bytes = response.body().bytes();
                   final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           setBitmap(bitmap);
                       }
                   });
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });
    }

    void setBitmap(Bitmap bitmap) {
        paint=new Paint();
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        radius = Math.min(bitmap.getWidth(), bitmap.getHeight())/2;
        invalidate();
    }
}
