package com.example.administrator.myapplication.fragment.pages;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.Activity.FeedShowActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.Server;
import com.example.administrator.myapplication.entity.Article;
import com.example.administrator.myapplication.entity.Page;
import com.example.administrator.myapplication.view.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedListFragment extends Fragment {
    View view;
    ListView feedList;
    List<Article> data;
    int page;
    View btnLoadMore;
    TextView tvLoadMore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_feed_list, null);
            btnLoadMore=inflater.inflate(R.layout.widget_load_more_button,null);
            tvLoadMore= (TextView) btnLoadMore.findViewById(R.id.tv_loadmore);
            feedList = (ListView) view.findViewById(R.id.lv_feed);
            feedList.addFooterView(tvLoadMore);
            feedList.setAdapter(feedListAdapter);
            feedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onClick(position);
                }
            });
            tvLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadMore();
                }
            });
        }

        return view;
    }

    private void loadMore() {

        tvLoadMore.setEnabled(false);
        tvLoadMore.setText("加载中");

        OkHttpClient client=Server.getShareClient();
        Request request=Server.requestBuildWithAPI("feeds/"+(page+1)).method("get",null).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               /* getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvLoadMore.setEnabled(true);
                        tvLoadMore.setText("加载更多");
                    }
                });*/
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvLoadMore.setEnabled(true);
                        tvLoadMore.setText("加载更多");
                    }
                });
                try {
                    final String responseString = response.body().string();
                    ObjectMapper mapper = new ObjectMapper();
                    Page<Article> articles= mapper.readValue(responseString,new TypeReference<Page<Article>>(){});

                    if (articles.getNumber()>page) {
                        data=articles.getContent();
                    }else{
                        data.addAll(articles.getContent());
                    }
                    page=articles.getNumber();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            feedListAdapter.notifyDataSetChanged();
                        }
                    });

                }catch (final Exception e){
                    e.printStackTrace();
                }


            }
        });

    }

    private void onClick(int position) {
        Intent intent = new Intent(getActivity(), FeedShowActivity.class);
       Bundle bundle=new Bundle();
        bundle.putSerializable("content",data.get(position));
        intent.putExtras(bundle);
        startActivity(intent);

    }

    BaseAdapter feedListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return data==null?0:data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                view = layoutInflater.inflate(R.layout.listview_item, null);

            } else {
                view = convertView;
            }

            TextView text1 = (TextView) view.findViewById(R.id.tv_name);
            text1.setText(data.get(position).getAuthorName());
            TextView text2 = (TextView) view.findViewById(R.id.tv_content);
            text2.setText(data.get(position).getContent());
            TextView text3 = (TextView) view.findViewById(R.id.tv_time);
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
            text3.setText(format.format(data.get(position).getCreateDate()));
            AvatarView avatarView= (AvatarView) view.findViewById(R.id.img_picture);
            avatarView.load(data.get(position).getAvatar());
            return view;
        }
    };

    public void load() {
        List<Article> feedsListData ;

        OkHttpClient client = Server.getShareClient();
        Request request = Server.requestBuildWithAPI("feeds").method("get", null).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String responseString = response.body().string();
                    ObjectMapper mapper = new ObjectMapper();
                    Page<Article> articles= mapper.readValue(responseString,new TypeReference<Page<Article>>(){});
                    FeedListFragment.this.page=articles.getNumber();
                    FeedListFragment.this.data=articles.getContent();
                   getActivity().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           feedListAdapter.notifyDataSetChanged();
                       }
                   });
                } catch (final Exception e) {
                   getActivity().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage(e.getMessage()).show();
                       }
                   });
                }

            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }
}
