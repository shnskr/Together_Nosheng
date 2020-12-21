package com.together.nosheng.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.databinding.LayoutTripListItemBinding;
import com.together.nosheng.model.user.User;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectView extends LinearLayout {

    private ProjectViewModel projectViewModel;


    private TextView travelTitle, travelPeriod, travelStatus;
    private ImageButton delete;

    public ProjectView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_trip_list_item,this,true);
        travelTitle = findViewById(R.id.trip_title);
        travelPeriod = findViewById(R.id.trip_period);
        travelStatus = findViewById(R.id.trip_status);
        delete = findViewById(R.id.btn_delete);
    }

    public void setTravelTitle(String tt){
        travelTitle.setText(tt);
    }

    public void setTravelPeriod(String tp){
        travelPeriod.setText(tp);
    }

    public void setTravelStatus(String ts) {
        travelStatus.setText(ts);
    }

    public void deleteProject(Context context, int position, FragmentActivity fragment, List<String> projects){
        projectViewModel = new ViewModelProvider(fragment).get(ProjectViewModel.class);

        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("여행기 삭제!")
                        .setMessage("삭제하신 여행기는 복구 할 수 없습니다.\n삭제하시겠습니까?")
                        .setIcon(R.drawable.ic_baseline_announcement_24);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String projectId = projects.get(position);
                        for(int i = 0; i < projects.size(); i++){
                            if(projects.get(i) == projectId){
                                projects.remove(projectId);
                                break;
                            }
                        }
                        projectViewModel.deleteUserProject(projectId);
                        projectViewModel.updateUserProjectList(projects);

                        Toast.makeText(context, "여행기가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        Log.i("여행기가 삭제되었습니다.", "0");
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
    }
}
