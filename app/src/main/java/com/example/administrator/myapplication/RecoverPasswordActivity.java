package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.RecoverPasswordStepOneFragment;
import com.example.administrator.myapplication.fragment.RecoverPasswordStepTwoFragment;

public class RecoverPasswordActivity extends Activity {
    RecoverPasswordStepOneFragment recoverPasswordFragmentOne = new RecoverPasswordStepOneFragment();
    RecoverPasswordStepTwoFragment recoverPasswordFragmentTwo = new RecoverPasswordStepTwoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);


        recoverPasswordFragmentOne.setSetNextStepListener(new RecoverPasswordStepOneFragment.SetNextStepListener() {
            @Override
            public void goNext() {
                goStep2();
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.container, recoverPasswordFragmentOne).commit();

    }

    private void goStep2() {
        getFragmentManager().beginTransaction().replace(R.id.container,recoverPasswordFragmentTwo).setCustomAnimations(R.animator.slide_in_right,R.animator.slide_out_left,R.animator.slide_in_left,R.animator.slide_out_right).addToBackStack(null).commit();
    }
}
