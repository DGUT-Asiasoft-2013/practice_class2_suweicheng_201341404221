package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.api.Server;
import com.example.administrator.myapplication.entity.Article;
import com.example.administrator.myapplication.entity.Comment;
import com.example.administrator.myapplication.entity.Page;
import com.example.administrator.myapplication.fragment.CommentFragment;
import com.example.administrator.myapplication.fragment.pages.FeedListFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.text;

public class FeedShowActivity extends Activity {
    static public Article article;
    TextView title;
    TextView createDate;
    TextView author;
    TextView content;
    Button back;
    Button postComment;
   // ListView comments;
    //List<Comment> commentsDatas;
    CommentFragment commentFragment=new CommentFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_show);
        article = (Article) getIntent().getSerializableExtra("content");


        author = (TextView) findViewById(R.id.tv_author);
        title = (TextView) findViewById(R.id.tv_title);
        createDate = (TextView) findViewById(R.id.tv_createdate);
        content = (TextView) findViewById(R.id.tv_content);
        back = (Button) findViewById(R.id.btn_back);
        postComment = (Button) findViewById(R.id.btn_postcomment);
      //  comments = (ListView) findViewById(R.id.lv_comments);

    }

//    private void getComments() {
//        {
//
//            OkHttpClient client = Server.getShareClient();
//            Request request = Server.requestBuildWithAPI("/article/" + article.getId() + "/comments/").method("get", null).build();
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    final String responseString = response.body().string();
//                    try {
//                        ObjectMapper mapper = new ObjectMapper();
//                        final Page<Comment> comment = mapper.readValue(responseString, new TypeReference<Page<Comment>>() {
//                        });
//                        //commentsDatas = comment.getContent();
//                        //   final Comment comment= mapper.readValue(responseString,Comment.class);
//                        FeedShowActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
////                                comments.setAdapter(commentAdapter);
////                                commentAdapter.notifyDataSetChanged();
//                              //  Toast.makeText(FeedShowActivity.this,commentsDatas.toString(),Toast.LENGTH_LONG).show();
//
//                            }
//                        });
//                    } catch (final Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        getFragmentManager().beginTransaction().replace(R.id.container_commentlist, commentFragment).commit();

       // getComments();
      //  comments.setAdapter(commentAdapter);
        author.setText(article.getAuthorName());
        title.setText(article.getTitle());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        createDate.setText(format.format(article.getCreateDate()));
        content.setText(article.getContent());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FeedShowActivity.this, HelloWorldActivity.class);
                startActivity(intent);
                finish();

            }
        });

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedShowActivity.this, AddCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("article", article);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }


//    BaseAdapter commentAdapter = new BaseAdapter() {
//        @Override
//        public int getCount() {
//            return commentsDatas==null?commentsDatas.size():0;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return commentsDatas.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = null;
//            if (convertView == null) {
//                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//                view = layoutInflater.inflate(R.layout.listview_item_comment, null);
//            }else{
//                view=convertView;
//            }
//
//            TextView commenteror= (TextView) view.findViewById(R.id.tv_commenteror);
//            TextView commentContent= (TextView) view.findViewById(R.id.tv_content);
//
//
//            commenteror.setText(commentsDatas.get(position).getCommenterorId());
//            commentContent.setText(commentsDatas.get(position).getContent());
//
//            return null;
//        }
//    };
}
