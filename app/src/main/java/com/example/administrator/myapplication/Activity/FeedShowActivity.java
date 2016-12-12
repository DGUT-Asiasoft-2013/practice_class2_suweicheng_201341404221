package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

public class FeedShowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_show);
        String text=getIntent().getStringExtra("text").toString();
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();

    }
}
