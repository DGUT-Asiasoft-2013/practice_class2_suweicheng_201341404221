package com.example.administrator.myapplication.fragment.pages;


import android.app.Fragment;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.FeedShowActivity;
import com.example.administrator.myapplication.R;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedListFragment extends Fragment {
    View view;
    ListView feedList;
    String []datas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view==null) {
            view=inflater.inflate(R.layout.fragment_feed_list,null);
        }
        feedList= (ListView) view.findViewById(R.id.lv_feed);
        Random random=new Random();
       datas=new String[20];
      for(int i=0;i<20;i++){
         datas[i]=new String("This Row is "+random.nextInt());
       }
        feedList.setAdapter(feedListAdapter);


        feedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClick(position);
            }
        });
        return view;
    }

    private void onClick(int position) {
        Intent intent=new Intent(getActivity(), FeedShowActivity.class);
        intent.putExtra("text",datas[position]);
        startActivity(intent);

    }

    BaseAdapter feedListAdapter=new BaseAdapter() {
        @Override
        public int getCount() {
            return datas.length;
        }

        @Override
        public Object getItem(int position) {
            return datas[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
            if (convertView==null) {
                LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
                view=layoutInflater.inflate(android.R.layout.simple_list_item_1,null);

            }else{
                view=convertView;
            }

            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            text1.setText(datas[position]);
            return view;
        }
    };

}
