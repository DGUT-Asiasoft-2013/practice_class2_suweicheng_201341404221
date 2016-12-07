package com.example.administrator.myapplication.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    Button search;
    Button me;
    Button feeds;
    Button add;
    Button notes;
    View view;
    Button[] items = {search, feeds, add, me,notes};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab, container);

        initView();

        return view;


    }

    void initView() {

        search = (Button) view.findViewById(R.id.btn_search);
        me = (Button) view.findViewById(R.id.btn_me);
        feeds = (Button) view.findViewById(R.id.btn_feeds);
        add = (Button) view.findViewById(R.id.btn_add);
        notes = (Button) view.findViewById(R.id.btn_notes);

        for (int i = 0; i < items.length; i++) {
            final int index=i;
            items[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click(index);
                }
            });
        }


    }

    public void setSetTabOnClicklistener(SetTabOnClicklistener setTabOnClicklistener) {
        this.setTabOnClicklistener = setTabOnClicklistener;
    }

    SetTabOnClicklistener setTabOnClicklistener;

    public static interface SetTabOnClicklistener {
        void onClick(int index);
    }

    private void click(int index) {
        setTabOnClicklistener.onClick(index);
    }


}
