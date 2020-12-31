package com.together.nosheng.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.databinding.LayoutNoteListItemBinding;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.view.NewPostFragment;
import com.together.nosheng.view.PostView;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class PostAdapter extends BaseAdapter {

    private List<Post> posts;
    private Context context;
    private String projectId;
    private FragmentActivity fragment;

    private String TAG = "PostAdapter";

    public PostAdapter(List<Post> posts, Context context, String projectId, FragmentActivity fragment) {
        this.posts = posts;
        this.context = context;
        this.projectId = projectId;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostView postView = new PostView(parent.getContext());
        Post post = (Post)getItem(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd");

        postView.setPostTitle(post.getTitle());
        postView.setPostRegDate(dateFormat.format(post.getRegDate()));
        postView.setTxt_nickname(post.getNickName());
        postView.setNotice(post.isNotice());

        postView.deleteNote(context, posts, projectId, position, fragment);


        return postView;
    }
}

