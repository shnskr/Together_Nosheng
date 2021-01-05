package com.together.nosheng.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.together.nosheng.R;
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

        projectView.getDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("여행기 삭제!")
                        .setMessage("삭제하신 여행기는 복구 할 수 없습니다.\n삭제하시겠습니까?")
                        .setIcon(R.drawable.ic_baseline_announcement_24);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String projectId = userProjectId.get(position);
                        userViewModel.deleteProject(projectId);
                        projectViewModel.deleteMember(projectId);

                        Toast.makeText(context, "여행기가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("여행기 삭제 요청이 취소되었습니다.", "1");
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

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
