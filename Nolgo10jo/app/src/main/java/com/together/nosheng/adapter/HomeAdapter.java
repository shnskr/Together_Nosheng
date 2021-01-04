package com.together.nosheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.view.ProjectView;
import com.together.nosheng.view.TabActivity;
import com.together.nosheng.viewmodel.ProjectViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class HomeAdapter extends BaseAdapter {

    private List<String> userProjectId;
    private Map<String, Project> userProject;
    private Context context;
    private ProjectViewModel projectViewModel;
    private UserViewModel userViewModel;

    public HomeAdapter(Map<String, Project> userProject, Context context,UserViewModel userViewModel, ProjectViewModel projectViewModel) {
        this.userProject = userProject;
        this.context = context;
        this.userViewModel = userViewModel;
        this.projectViewModel = projectViewModel;

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
        } else if(today.after(project.getStartDate()) && today.before(project.getEndDate())){
            projectView.setTravelStatus("Carpe Diem");
        } else if(project.getEndDate().before(today)) {
            projectView.setTravelStatus("the End");
        }

        projectView.deleteProject(context, position, userViewModel, projectViewModel, userProjectId);

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
