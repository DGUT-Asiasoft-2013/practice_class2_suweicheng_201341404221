package com.example.administrator.myapplication.fragment.pages;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.Server;
import com.example.administrator.myapplication.entity.User;
import com.example.administrator.myapplication.view.AvatarView;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyInfoFragment extends Fragment {
    View view;
    TextView textView;
    ProgressBar progress;
    AvatarView avatarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_my_info, null);
        }

        textView = (TextView) view.findViewById(R.id.tv_myInfo);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        avatarView = (AvatarView) view.findViewById(R.id.avatar);


        return view;
    }


    @Override
    public void onResume() {

        super.onResume();

        textView.setVisibility(view.GONE);
        progress.setVisibility(View.VISIBLE);
        OkHttpClient client = Server.getShareClient();
        Request request = Server.requestBuildWithAPI("me")
                .method("get", null)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call arg0, Response arg1) throws IOException {
                try {
                    final User user = new ObjectMapper().readValue(arg1.body().bytes(), User.class);
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            MyInfoFragment.this.onResponse(arg0, user);
                        }
                    });
                } catch (final Exception e) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            MyInfoFragment.this.onFailuer(arg0, e);
                        }
                    });
                }

            }

            @Override
            public void onFailure(final Call arg0, final IOException arg1) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        MyInfoFragment.this.onFailuer(arg0, arg1);
                    }
                });

            }
        });
    }

    protected void onResponse(Call arg0, User user) {
        progress.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setTextColor(Color.BLACK);
        textView.setText("Hello," + user.getName());
        avatarView.load(user.getAvatar());


    }

    protected void onFailuer(Call arg0, Exception ex) {
        progress.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setTextColor(Color.RED);
        textView.setText(ex.getMessage());

    }


}
