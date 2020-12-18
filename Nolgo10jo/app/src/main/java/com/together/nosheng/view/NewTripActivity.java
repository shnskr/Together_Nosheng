package com.together.nosheng.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.timessquare.CalendarPickerView;
import com.together.nosheng.databinding.ActivityNewTripBinding;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTripActivity extends AppCompatActivity {

    private Project project;
    private ProjectViewModel projectViewModel;
    private ActivityNewTripBinding newTripBinding;

    private String projectId;

    private String TAG = "NewTripActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newTripBinding = ActivityNewTripBinding.inflate(getLayoutInflater());
        setContentView(newTripBinding.getRoot());

        project = new Project();
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        //code duplicate
        newTripBinding.btnDuplicate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Trip Code", projectId);
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(getApplicationContext(),"Trip Code 복사 완료", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //EditText 리스너 (입력시 반응)
        newTripBinding.etxtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                newTripBinding.txtCountLength.setText(25-s.length()+"글자");   //글자수 TextView에 보여주기.
            }
        });

        //calendar function
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");   //추가

        Date today = new Date();

        newTripBinding.txtStartDate.setText(dateFormat.format(today));

        newTripBinding.btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTripBinding.calendar.selectDate(today);
                newTripBinding.txtStartDate.setText(dateFormat.format(today));
                newTripBinding.txtEndDate.setText("endDate");
            }
        });

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,2);

        newTripBinding.calendar.init(today,nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);


        newTripBinding.calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                List<Date> periodList = newTripBinding.calendar.getSelectedDates();

                Date startDate = periodList.get(0);
                project.setStartDate(startDate);
                Date endDate = periodList.get(periodList.size()-1);
                project.setEndDate(endDate);

                if (periodList.size() > 1){
                    newTripBinding.txtEndDate.setText(dateFormat.format(endDate));
                    newTripBinding.txtCountDate.setText((periodList.size()-1) + " 박 " + periodList.size() + " 일");

                }else if(periodList.size() == 1){
                    newTripBinding.txtStartDate.setText(dateFormat.format(startDate));
                    newTripBinding.txtEndDate.setText("endDate");
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });


        //check
        newTripBinding.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(project.getStartDate() != null){

                    projectViewModel.addUserProject(project);

                    Intent intent = new Intent(NewTripActivity.this, MainActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"날짜를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    Log.i("날짜를 선택해주세요!!!!!!!", "3");
                }
            }
        });
    }

    //keyboard controller
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
