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
    Button nextStep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view==null) {
            view=inflater.inflate(R.layout.fragment_password_recover_step2,null);
        }
     return  view;
    }

}
