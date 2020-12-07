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

public class HomeAdapter extends BaseAdapter {
    List<Project> projects = new ArrayList<>();

    public HomeAdapter(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
