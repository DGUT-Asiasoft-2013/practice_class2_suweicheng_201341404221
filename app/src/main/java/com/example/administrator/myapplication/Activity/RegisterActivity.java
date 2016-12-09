package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.PictureInputCellFragment;
import com.example.administrator.myapplication.fragment.SimpleTextCellFragment;
import com.example.administrator.myapplication.util.MD5Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends Activity {

    final static String URL = "Http://172.27.0.32:8080/membercenter/api/register";

    SimpleTextCellFragment fragmentInputCellAcount;
    SimpleTextCellFragment fragmentInputCellPassword;
    SimpleTextCellFragment fragmentInputCellPasswordRepeat;
    SimpleTextCellFragment fragmentInputCellEmail;
    SimpleTextCellFragment fragmentInputCellName;
    PictureInputCellFragment pictureInputCellFragment;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        fragmentInputCellAcount = (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
        fragmentInputCellName = (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_name);
        fragmentInputCellPassword = (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_password);
        fragmentInputCellPasswordRepeat = (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_twice);
        pictureInputCellFragment = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.picture);
        fragmentInputCellEmail = (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_email);
        register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    public void submit() {
        String password = fragmentInputCellPassword.getText();
        String passwordRepeat = fragmentInputCellPasswordRepeat.getText();
        String passwordHash="";
        if (!password.equals(passwordRepeat)) {
            new AlertDialog.Builder(RegisterActivity.this).setTitle("提示").setMessage("密码不一致，请重新输入").setPositiveButton("确定", null).show();
            return;
        }
        final String account = fragmentInputCellAcount.getText();
        String name = fragmentInputCellName.getText();
        String email = fragmentInputCellEmail.getText();
        try {
             passwordHash = MD5Util.getEncryptedPwd(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();


        /////
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("account", account)
                .addFormDataPart("name", name)
                .addFormDataPart("passwordHash", passwordHash)
                .addFormDataPart("email", email)
                .build();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍候");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Request request = new Request.Builder().url(URL).method("POST", body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RegisterActivity.this.onFailure(call, e);
                    }
                });

                progressDialog.dismiss();
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RegisterActivity.this.onResponse(call, response);
                    }
                });

                progressDialog.dismiss();
            }
        });
    }

    private void onResponse(Call call, Response response) {

        try {
            new AlertDialog.Builder(RegisterActivity.this).setTitle("提示").setMessage("注册成功"+response.body().string()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onFailure(Call call, IOException e) {
        new AlertDialog.Builder(RegisterActivity.this).setTitle("提示").setMessage("注册失败").setMessage(e.toString()).setPositiveButton("确定", null).show();

    }

    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        fragmentInputCellAcount.setLabelText("  账  户  ");
        fragmentInputCellAcount.setHintText("请输入账户名");
        fragmentInputCellAcount.setPassword(false);

        fragmentInputCellName.setLabelText("  昵  称  ");
        fragmentInputCellName.setHintText("请输入昵称");
        fragmentInputCellName.setPassword(false);

        fragmentInputCellPassword.setLabelText("  密  码  ");
        fragmentInputCellPassword.setHintText("请输入密码");

        fragmentInputCellPasswordRepeat.setLabelText("重复密码");
        fragmentInputCellPasswordRepeat.setHintText("请重复输入密码");

        fragmentInputCellEmail.setLabelText("  邮  箱  ");
        fragmentInputCellEmail.setHintText("请输入邮箱");
        fragmentInputCellEmail.setPassword(false);
        fragmentInputCellEmail.setInputType();
    }

}
