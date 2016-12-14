package com.example.administrator.myapplication.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.Activity.FeedShowActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.Server;
import com.example.administrator.myapplication.entity.Comment;
import com.example.administrator.myapplication.entity.Page;
import com.example.administrator.myapplication.view.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CommentFragment extends Fragment {
    View view;
    ListView comments;
    List<Comment> commentsDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_comment, null);
            comments = (ListView) view.findViewById(R.id.lv_comments);
            comments.setAdapter(commentsListAdapter);
        }
        comments = (ListView) view.findViewById(R.id.lv_comments);
        return view;
    }

    private void getComments() {
        {

            OkHttpClient client = Server.getShareClient();
            Request request = Server.requestBuildWithAPI("/article/" + FeedShowActivity.article.getId() + "/comments/").method("get", null).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseString = response.body().string();
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        final Page<Comment> comment = mapper.readValue(responseString, new TypeReference<Page<Comment>>() {
                        });
                        commentsDatas = comment.getContent();
                        //   final Comment comment= mapper.readValue(responseString,Comment.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                commentsListAdapter.notifyDataSetChanged();
                            }
                        });
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }


    @Override
    public void onResume() {

        super.onResume();
        getComments();


    }


    BaseAdapter commentsListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return commentsDatas == null ? 0 : commentsDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return commentsDatas.get(position);
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
                view = layoutInflater.inflate(R.layout.listview_item_comment, null);
            } else {
                view = convertView;
            }

            TextView commenteror = (TextView) view.findViewById(R.id.tv_commenteror);
            TextView commentContent = (TextView) view.findViewById(R.id.tv_content);
            TextView commentDate = (TextView) view.findViewById(R.id.tv_createdate);
            AvatarView avatarView = (AvatarView) view.findViewById(R.id.avatar_icon);

            avatarView.load(commentsDatas.get(position).getCommentator().getAvatar());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            commentDate.setText(simpleDateFormat.format(commentsDatas.get(position).getCreateDate()));
            commenteror.setText(commentsDatas.get(position).getCommentator().getName());
            commentContent.setText(commentsDatas.get(position).getContent());

            return view;
        }
    };


}
