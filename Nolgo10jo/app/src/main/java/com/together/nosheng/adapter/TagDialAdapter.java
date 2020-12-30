package com.together.nosheng.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.FragmentActivity;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.view.TagDialView;

import java.util.ArrayList;
import java.util.List;

public class TagDialAdapter extends BaseAdapter {

    private String TAG = "TagDialAdapter";

    private List<String> tags;
    private String projectId;
    private FragmentActivity fragmentActivity;
    private List<String> utags;

    public TagDialAdapter(List<String> tags, String projectId, FragmentActivity fragmentActivity, List<String> utags) {
        this.tags = tags;
        this.projectId = projectId;
        this.fragmentActivity = fragmentActivity;
        this.utags = utags;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Object getItem(int position) {
        return tags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TagDialView tagDialView = new TagDialView(parent.getContext());

        if(utags.contains(tags.get(position))){
            tagDialView.setCheckBoxs();
        }

        tagDialView.setTagName(tags.get(position));

        tagDialView.setDeleteTag(tags, position, projectId, fragmentActivity);
        tagDialView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tagDialView.setCheckBox()){
                    Log.i(TAG+"?emf", utags.toString());
                    Log.i(TAG+"!emf", tags.toString());
                    utags.add(tags.get(position));
                    Log.i(TAG+"emfdjdhk!", utags.toString());
                } else {
                    utags.remove(tags.get(position));
                    Log.i(TAG+"emfdjdhk?", utags.toString());
                }
            }
        });

        return tagDialView;
    }
}
