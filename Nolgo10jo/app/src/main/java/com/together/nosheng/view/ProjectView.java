package com.together.nosheng.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.together.nosheng.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectView extends LinearLayout {

    TextView travelTitle, travelPeriod;

    public ProjectView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.activity_fragment_home,this,true);
        travelTitle = findViewById(R.id.trip_title);
        travelPeriod = findViewById(R.id.trip_period);
    }

    public void setTravelTitle(String tt){
        travelTitle.setText(tt);
    }

    public void setTravelPeriod(Date tp){
//        2019.02.13 - 2019.2.25
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        travelPeriod.setText(format.format(tp));
    }
}
