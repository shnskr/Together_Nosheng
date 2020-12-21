package com.together.nosheng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.together.nosheng.R;

public class AuserListView extends LinearLayout {

    private Context context;
    private TextView project_title;
    private TextView tv_startdate;
    private TextView tv_enddate;
    private TextView tv_status;


    public AuserListView(Context context) {
        super(context);
        init(context);
    }

    public AuserListView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.auser_project_list_item,this,true);
        this.context = context;

        project_title = (TextView)findViewById(R.id.project_title);
        tv_startdate = (TextView)findViewById(R.id.tv_startdate);
        tv_enddate = (TextView)findViewById(R.id.tv_enddate);
        tv_status = (TextView)findViewById(R.id.tv_status);
    }

    //user_Name.setText(setNickName);

    public void setProject_title(String project_title) {
        this.project_title.setText(project_title);
    }

    public void setTv_startdate(String tv_startdate) {
        this.tv_startdate.setText(tv_startdate);
    }

    public void setTv_enddate(String tv_enddate) {
        this.tv_enddate.setText(tv_enddate);
    }

    public void setTv_status(String tv_status) {
        this.tv_status.setText(tv_status);
    }
}
