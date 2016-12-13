package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.Server;
import com.example.administrator.myapplication.entity.User;
import com.example.administrator.myapplication.fragment.SimpleTextCellFragment;
import com.example.administrator.myapplication.util.MD5;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends Activity {
    SimpleTextCellFragment fragmentInputCellAcount;
    SimpleTextCellFragment fragmentInputCellPassword;
    final static String URL = "Http://172.27.0.33:8080/membercenter/api/login";
    Button register;
    Button login;
    Button forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fragmentInputCellAcount = (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
        fragmentInputCellPassword = (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_password);

        register = (Button) findViewById(R.id.btn_register);
        login = (Button) findViewById(R.id.btn_login);
        forget = (Button) findViewById(R.id.btn_forget);

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                goRegister();
            }
        });
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

        forget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goRecover();
            }
        });


    }

    private void goRecover() {
        Intent intent = new Intent(this, RecoverPasswordActivity.class);
        startActivity(intent);

    }

    void goRegister() {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    void goLogin() {

        ProgressBar progressBar=new ProgressBar(this);
        String account = fragmentInputCellAcount.getText();
        String passwordHash = MD5.getMD5(fragmentInputCellPassword.getText());


        OkHttpClient client = Server.getShareClient();


        /////
        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", account)
                .addFormDataPart("passwordHash", passwordHash);

        Request request =Server.requestBuildWithAPI("login").method("GET", null).post(body.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoginActivity.this.onFailure(call, e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String responseString = response.body().string();
                ObjectMapper mapper = new ObjectMapper();

//                final String responseString =response.body().string();
                User user = null;

                try {

                    user = mapper.readValue(responseString, User.class);
                    if (user != null) {
                        final User finalUser = user;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                LoginActivity.this.onResponse(call, finalUser.getAccount());

                            }
                        });
                    }

                } catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LoginActivity.this.onFailure(call, e);
                        }
                    });

                }
                ;
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        fragmentInputCellAcount.setLabelText("用户名");
        fragmentInputCellAcount.setHintText("请输入用户名");
        fragmentInputCellAcount.setPassword(false);
        fragmentInputCellPassword.setLabelText("密  码");
        fragmentInputCellPassword.setHintText("请输入密码");
    }

    private void onResponse(Call call, String response) {
        if (response.equals("")) {
            new AlertDialog.Builder(LoginActivity.this).setTitle("提示").setMessage("登录失败" + "账户不存在或密码错误").setPositiveButton("确定", null).show();
        } else {
            new AlertDialog.Builder(LoginActivity.this).setTitle("提示").setMessage("你好， " + response).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(LoginActivity.this, HelloWorldActivity.class);
                    startActivity(intent);
                    finish();

                }
            }).show();

        }


    }

    private void onFailure(Call call, Exception e) {
        new AlertDialog.Builder(LoginActivity.this).setTitle("失败提示").setMessage(e.toString()).setPositiveButton("确定", null).show();

    }


}
