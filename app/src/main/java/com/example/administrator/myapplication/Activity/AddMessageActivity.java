package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.Server;
import com.example.administrator.myapplication.entity.Article;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddMessageActivity extends Activity {

    EditText content;
    EditText title;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        add = (Button) findViewById(R.id.btn_add);
        content= (EditText) findViewById(R.id.et_addcontent);
        title= (EditText) findViewById(R.id.et_title);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
                addArticleToServer();
            }
        });
    }

    private void addArticleToServer() {
        OkHttpClient client= Server.getShareClient();
        String articleContent=content.getText().toString();
        String articleTitle=title.getText().toString();
        RequestBody body=new MultipartBody.Builder().addFormDataPart("title",articleTitle).addFormDataPart("content",articleContent).build();
        Request request=Server.requestBuildWithAPI("addArticle").method("POST",body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseString =response.body().string();
                ObjectMapper mapper=new ObjectMapper();
                Article article=null;
                try{
                    article=mapper.readValue(responseString,Article.class);
                    if (article!=null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(AddMessageActivity.this).setTitle("提示：").setMessage("添加文章成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(AddMessageActivity.this,HelloWorldActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();

                            }
                        });
                    }
                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(AddMessageActivity.this).setTitle("提示：").setMessage("添加文章失败！").setPositiveButton("确定",null).show();

                        }
                    });

                }

            }
        });
    }
}
