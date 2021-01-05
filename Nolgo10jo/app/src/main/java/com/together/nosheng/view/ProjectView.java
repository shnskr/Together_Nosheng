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
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.ProjectViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProjectView extends LinearLayout {

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

    public ImageButton getDelete() {
        return delete;
    }
}
