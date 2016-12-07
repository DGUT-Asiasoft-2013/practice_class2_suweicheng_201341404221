package com.example.administrator.myapplication.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    View search;
    View me;
    View feeds;
    View add;
    View notes;
    View view;
    View [] items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view==null) {
            view = inflater.inflate(R.layout.fragment_tab, null);
        }


        initView();

        return view;


    }

    void initView() {

        search = (View) view.findViewById(R.id.btn_search);
        me = (View) view.findViewById(R.id.btn_me);
        feeds = (View) view.findViewById(R.id.btn_feeds);
        add = (View) view.findViewById(R.id.btn_add);
        notes = (View) view.findViewById(R.id.btn_notes);
        items = new  View[]{search, feeds, add, me,notes};
        for (int i = 0; i < 5; i++) {
            final int index=i;
            items[index].setOnClickListener(new View.OnClickListener() {
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
