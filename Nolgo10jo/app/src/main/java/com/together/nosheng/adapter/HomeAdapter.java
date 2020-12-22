package com.together.nosheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.view.ProjectView;
import com.together.nosheng.view.TabActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class HomeAdapter extends BaseAdapter {

    private List<String> userProjectId;
    private Map<String, Project> userProject;
    private Context context;
    private FragmentActivity fragment;
    private List<String> projects;
    private FragmentTransaction ft;

    public HomeAdapter(Map<String, Project> userProject, Context context, FragmentActivity fragment, List<String> projects) {
        this.userProject = userProject;
        this.context = context;
        this.fragment = fragment;
        this.projects = projects;

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

        if(project.getTitle() != ""){
            projectView.setTravelTitle(project.getTitle());
        } else {
            projectView.setTravelTitle("여행기");
        }
        projectView.setTravelPeriod(format.format(project.getStartDate()) + " ~ " + format.format(project.getEndDate()));

        Date today = new Date();

        if(project.getStartDate().after(today)){
            projectView.setTravelStatus("Planning");
        } else if(today.before(project.getStartDate()) && today.after(project.getEndDate())){
            projectView.setTravelStatus("Carpe Diem");
        } else if(project.getEndDate().before(today)) {
            projectView.setTravelStatus("the End");
        }

        projectView.deleteProject(context, position, fragment, userProjectId);

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
