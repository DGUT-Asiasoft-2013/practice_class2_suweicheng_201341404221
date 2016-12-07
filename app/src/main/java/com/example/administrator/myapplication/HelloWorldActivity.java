package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.myapplication.fragment.TabFragment;
import com.example.administrator.myapplication.fragment.pages.FeedListFragment;
import com.example.administrator.myapplication.fragment.pages.MyInfoFragment;
import com.example.administrator.myapplication.fragment.pages.NotesListFragment;
import com.example.administrator.myapplication.fragment.pages.SearchFragment;

public class HelloWorldActivity extends Activity {
    TabFragment tabFragment;
    FeedListFragment feedListFragment = new FeedListFragment();
    NotesListFragment notesListFragment = new NotesListFragment();
    MyInfoFragment myInfoFragment = new MyInfoFragment();
    SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_helloworld);

        tabFragment = (TabFragment) getFragmentManager().findFragmentById(R.id.frag_tab);
        tabFragment.setSetTabOnClicklistener(new TabFragment.SetTabOnClicklistener() {
            @Override
            public void onClick(int index) {
                onItemClick(index);
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
    }

    private void onItemClick(int index) {
        switch (index) {
            case 0:
                getFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                break;
            case 1:
                getFragmentManager().beginTransaction().replace(R.id.container, feedListFragment).commit();
                break;
            case 2:
                getFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                break;
            case 3:
                getFragmentManager().beginTransaction().replace(R.id.container, myInfoFragment).commit();
                break;
            case 4:
                getFragmentManager().beginTransaction().replace(R.id.container, notesListFragment).commit();
                break;

        }
    }
}
