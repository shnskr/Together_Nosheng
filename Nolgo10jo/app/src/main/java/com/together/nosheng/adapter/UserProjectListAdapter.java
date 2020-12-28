package com.together.nosheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.view.AuserListView;
import com.together.nosheng.view.AuserProjectListActivity;
import com.together.nosheng.view.TabActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserProjectListAdapter extends BaseAdapter {

    ArrayList<Project> items = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    private Context context;
    public UserProjectListAdapter(Context context) {
        this.context = context;
    }

    //데이터 추가 메소드
    public void addItem(Project project, String docId) {
        items.add(project);
        ids.add(docId);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AuserListView view = new AuserListView(parent.getContext());


        Project project = items.get(position);
//        Log.i("아아앜","아아아앜"+position+"/"+items.toString());
//        User user = items.get(position);

        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");


        view.setProject_title(project.getTitle());// 리스트 아이템 하나에 하나하나 set
        view.setTv_startdate(format.format(project.getStartDate())+" ~ ");
        view.setTv_enddate(format.format(project.getEndDate()));

        Date today = new Date();
        Log.i("dkdkkdkdkd",today+"");

        Log.i("dk",project.getStartDate().after(today)+"dkdkdkkdkd 0");
        Log.i("dk",project.getStartDate() + "");
        Log.i("dk",project.getEndDate() + "");
        Log.i("dk",today + "");

        if(project.getStartDate().after(today)){
            Log.i("dkdkkdkdkd","dkdkdkkdkd 1");
            view.setTv_status("Planning");
        } else if(today.after(project.getStartDate()) && today.before(project.getEndDate())){
            Log.i("dkdkkdkdkd","dkdkdkkdkd 2");
            view.setTv_status("Carpe Diem");
        } else if(project.getEndDate().before(today)) {
            Log.i("dkdkkdkdkd","dkdkdkkdkd 3");
            view.setTv_status("the End");
        }



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//프로젝트 리스트 보여주고 그 프로젝트 리스트 아이템 클릭했을때 tapActivity로 이동
//                Intent intent = new Intent(context , AuserProjectListActivity.class);
//                intent.putExtra("docId", ids
//                context.startActivity(intent);
                Intent intent = new Intent(parent.getContext(), TabActivity.class);
                intent.putExtra("projectId", ids.get(position));
                parent.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
