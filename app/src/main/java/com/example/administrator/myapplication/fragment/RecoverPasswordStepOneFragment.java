package com.example.administrator.myapplication.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public class RecoverPasswordStepOneFragment extends Fragment {

    Button nextStep;
    EditText email;
    View view;

    public void setSetNextStepListener(SetNextStepListener setNextStepListener) {
        this.setNextStepListener = setNextStepListener;
    }

    public SetNextStepListener setNextStepListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_password_recover_step1, null);
        }
        nextStep = (Button) view.findViewById(R.id.nextstep1);
        email= (EditText) view.findViewById(R.id.email);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });


        return view;

    }

    public static  interface SetNextStepListener {
        void goNext();
    }


    void goNext() {

        if (setNextStepListener!=null) {
            setNextStepListener.goNext();
        }


    }
}
