package com.together.nosheng.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.view.ProjectView;
import com.together.nosheng.view.TabActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends BaseAdapter {

    private Map<String, Project> userProject;
    private List<String> userProjectId;

    public HomeAdapter(Map<String, Project> userProject) {
        this.userProject = userProject;
        userProjectId = new ArrayList<>(userProject.keySet());
    }

    @Override
    public int getCount() {
        return userProjectId.size();
    }

    @Override
    public Object getItem(int position) {
        return userProject.get(userProjectId.get(position));
    }

    @Override
    public long getItemId(int position) {
        return userProjectId.indexOf(userProjectId.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectView projectView = new ProjectView(parent.getContext());
        Project project = (Project) getItem(position);

        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");

        projectView.setTravelTitle(project.getTitle());
        projectView.setTravelPeriod(format.format(project.getStartDate()) + " ~ " + format.format(project.getEndDate()));

        projectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), TabActivity.class);
                intent.putExtra("projectId", userProjectId.get(position));
                parent.getContext().startActivity(intent);
            }
        });

        return projectView;
    }

}
