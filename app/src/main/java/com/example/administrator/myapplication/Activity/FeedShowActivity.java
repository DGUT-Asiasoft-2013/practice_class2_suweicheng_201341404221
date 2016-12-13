package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entity.Article;

import java.text.SimpleDateFormat;

import static android.R.attr.text;

public class FeedShowActivity extends Activity {
    Article article;
    TextView title;
    TextView createDate;
    TextView author;
    TextView content;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_show);
         article= (Article) getIntent().getSerializableExtra("content");

        author= (TextView) findViewById(R.id.tv_author);
        title= (TextView) findViewById(R.id.tv_title);
        createDate= (TextView) findViewById(R.id.tv_createdate);
        content= (TextView) findViewById(R.id.tv_content);
        back= (Button) findViewById(R.id.btn_back);


    }


    @Override
    protected void onResume() {
        super.onResume();

        author.setText(article.getAuthorName());
        title.setText(article.getTitle());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        createDate.setText(format.format(article.getCreateDate()));
        content.setText(article.getContent());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(FeedShowActivity.this,HelloWorldActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
