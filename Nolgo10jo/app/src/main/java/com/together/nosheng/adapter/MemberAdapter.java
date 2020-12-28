package com.together.nosheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.view.MemberView;

import java.util.List;

public class MemberAdapter extends BaseAdapter {

    private Context context;
    private String projectId;

    private List<String> members;

    public MemberAdapter(Context context,String projectId, List<String> members) {
        this.context = context;
        this.projectId = projectId;
        this.members = members;
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int position) {
        return members.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemberView memberView = new MemberView(parent.getContext());

        memberView.setNickName(members.get(position));

        return memberView;
    }
}
