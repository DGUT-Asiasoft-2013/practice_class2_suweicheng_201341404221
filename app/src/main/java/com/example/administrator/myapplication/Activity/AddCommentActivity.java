package com.example.administrator.myapplication.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.Server;
import com.example.administrator.myapplication.entity.Article;
import com.example.administrator.myapplication.entity.Comment;
import com.example.administrator.myapplication.entity.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddCommentActivity extends Activity {

    EditText comment;
    Button post;
    int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        comment = (EditText) findViewById(R.id.et_comment);
        post = (Button) findViewById(R.id.btn_post);
        articleId=((Article)getIntent().getSerializableExtra("article")).getId();
    }


    @Override
    protected void onResume() {
        super.onResume();
        post = (Button) findViewById(R.id.btn_post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });

    }

    private void postComment() {

        String commentString = comment.getText().toString();
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("content", commentString);
        OkHttpClient client = Server.getShareClient();
        Request request = Server.requestBuildWithAPI("/article/" + articleId + "/comments/").method("POST", multipartBody.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseString = response.body().string();
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    final Comment comment= mapper.readValue(responseString,Comment.class);
                    AddCommentActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(AddCommentActivity.this).setTitle("提示：").setMessage("评论发送成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();

                        }
                    });
                }catch (final  Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
