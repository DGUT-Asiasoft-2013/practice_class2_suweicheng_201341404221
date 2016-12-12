package com.example.administrator.myapplication.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public class RecoverPasswordStepTwoFragment extends Fragment {
    View view;
    Button nextStepTwo;
    SimpleTextCellFragment inputPassword;
    SimpleTextCellFragment inputPasswordRepeat;
    SimpleTextCellFragment inputVerfity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_password_recover_step2, null);
        }

        nextStepTwo = (Button) view.findViewById(R.id.nextstep2);
        inputVerfity= (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_vertify);
        inputPassword = (SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_password_recover);
        inputPasswordRepeat = ((SimpleTextCellFragment) getFragmentManager().findFragmentById(R.id.input_password_recover_repeat));


        nextStepTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goReset();
            }
        });


        return view;
    }

    public interface SetOnClickListener {
        void goReset();
    }

    public void setSetOnClickListener(SetOnClickListener setOnClickListener) {
        this.setOnClickListener = setOnClickListener;
    }

    SetOnClickListener setOnClickListener;

    private void goReset() {
        setOnClickListener.goReset();
    }

    @Override
    public void onResume() {
        super.onResume();
        inputPassword.setLabelText("密码");
        inputPassword.setHintText("请输入密码");
        inputPassword.setPassword(true);
        inputPasswordRepeat.setLabelText("再次输入");
        inputPasswordRepeat.setHintText("再次输入密码");
        inputPasswordRepeat.setPassword(true);
        inputVerfity.setLabelText("验证码");
        inputVerfity.setPassword(false);
        inputVerfity.setHintText("请输入验证码");
    }
}
