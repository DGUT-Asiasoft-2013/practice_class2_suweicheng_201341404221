package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class FeedShowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_show);
        Toast.makeText(this,"点击了"+getIntent().getExtras().toString(),Toast.LENGTH_LONG).show();


    }
}
