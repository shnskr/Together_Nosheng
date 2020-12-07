package com.together.nosheng.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.view.ProjectView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends BaseAdapter {
    List<Project> projects = new ArrayList<>();

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
        Project project = projects.get(position);

        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");

        projectView.setTravelTitle(project.getTitle());
        projectView.setTravelPeriod(format.format(project.getStartDate()) + " ~ " + format.format(project.getEndDate()));
        return projectView;
    }

    public ArrayList<Project> getProjects(){
        ArrayList<Project> tripData = new ArrayList<>();
        for(int x = 0; x < projects.size(); x++){
            tripData.add(projects.get(x));
        }
        return tripData;
    }
}
