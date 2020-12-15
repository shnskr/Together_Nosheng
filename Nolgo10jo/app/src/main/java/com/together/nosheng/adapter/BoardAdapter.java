package com.together.nosheng.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.user.User;
import com.together.nosheng.view.PostView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class BoardAdapter extends BaseAdapter {

    private List<Post> postList;
    private String TAG  = "Board Adapter";

    public BoardAdapter(List<Post> postList) {
        this.postList = postList;
        Log.i(TAG, postList.toString());
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostView postView = new PostView(parent.getContext());
        Post post = (Post) getItem(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd");

        postView.setPostTitle(post.getTitle());
        postView.setTxt_nickname(post.getNickName());
        postView.setPostRegDate(dateFormat.format(post.getRegDate()));
        postView.setNotice(post.isNotice());

        Log.i(TAG, post.toString()+"접근 완료");

        return postView;
    }
}
