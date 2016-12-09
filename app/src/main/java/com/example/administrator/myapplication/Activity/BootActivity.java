package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BootActivity extends Activity {


    final static String URL="Http://172.27.0.32:8080/membercenter/api/hello";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boot);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                startLoginActivity();
//            }
//        }, 1000);

        OkHttpClient httpClient=new OkHttpClient();
        Request request=new Request.Builder().url(URL).method("GET",null).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                BootActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BootActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                BootActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(BootActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        startLoginActivity();
                    }
                });
            }
        });



    }

    void startLoginActivity(){

        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
