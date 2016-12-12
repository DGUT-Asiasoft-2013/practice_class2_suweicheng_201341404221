package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.Server;
import com.example.administrator.myapplication.fragment.RecoverPasswordStepOneFragment;
import com.example.administrator.myapplication.fragment.RecoverPasswordStepTwoFragment;
import com.example.administrator.myapplication.fragment.SimpleTextCellFragment;
import com.example.administrator.myapplication.util.MD5;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecoverPasswordActivity extends Activity {
    RecoverPasswordStepOneFragment recoverPasswordFragmentOne = new RecoverPasswordStepOneFragment();
    RecoverPasswordStepTwoFragment recoverPasswordFragmentTwo = new RecoverPasswordStepTwoFragment();
    String email;
    String passwordHash;
    String getPasswordHashRepeat;
    SimpleTextCellFragment inputPassword;
    SimpleTextCellFragment inputPasswordRepeat;
    SimpleTextCellFragment inputVerfity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        inputVerfity= (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_vertify);
        inputPassword = (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_password_recover);
        inputPasswordRepeat = ((SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_password_recover_repeat));



        recoverPasswordFragmentOne.setSetNextStepListener(new RecoverPasswordStepOneFragment.SetNextStepListener() {
            @Override
            public void goNext() {

                goStep2();
            }
        });

        recoverPasswordFragmentTwo.setSetOnClickListener(new RecoverPasswordStepTwoFragment.SetOnClickListener() {
            @Override
            public void goReset() {
                goResetPassWord();
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.container, recoverPasswordFragmentOne).commit();
    }

    private void goResetPassWord() {



       OkHttpClient client = Server.getShareClient();

        String passwordRepeat = inputPasswordRepeat.getText();
        String password = inputPassword.getText();

        if (passwordRepeat.equals(password)) {
            passwordHash = MD5.getMD5(password);
            RequestBody body = new MultipartBody.Builder().addFormDataPart("email", email).addFormDataPart("passwordHash", passwordHash).build();
            Request request = Server.requestBuildWithAPI("reset").method("POST", body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        final String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result.equals("true")) {
                                    new AlertDialog.Builder(RecoverPasswordActivity.this).setTitle("提示").setMessage("密码修改成功\n" + "请重新登录").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                                }
                            }
                        });
                    } catch (final Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RecoverPasswordActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
            });
        } else {
            new AlertDialog.Builder(RecoverPasswordActivity.this).setTitle("提示").setMessage("两次输入的密码不一致\n" + "请确认后进行修改").setPositiveButton("确定", null).show();
        }


    }

    private void goStep2() {
        email = ((EditText) findViewById(R.id.email)).getText().toString();
        getFragmentManager().beginTransaction().replace(R.id.container, recoverPasswordFragmentTwo).setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left, R.animator.slide_out_right).addToBackStack(null).commit();

    }



}
