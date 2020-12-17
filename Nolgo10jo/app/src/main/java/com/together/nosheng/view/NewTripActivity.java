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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newTripBinding = ActivityNewTripBinding.inflate(getLayoutInflater());
        setContentView(newTripBinding.getRoot());

        project = new Project();
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);


        //trip project code duplicate
        String tripCode = newTripBinding.txtTripCode.getText().toString();

        newTripBinding.btnDuplicate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Trip Code", tripCode);
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
                newTripBinding.txtCountLength.setText(s.length()+"글자");   //글자수 TextView에 보여주기.
            }
        });

        //EditText Enter key 방지
        newTripBinding.etxtTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        //calendar function
        Date today = new Date();
        ArrayList<Integer> today_int = changeForm(today);
        String t_day = "" + today_int.get(0) + "." + today_int.get(1) + "." + today_int.get(2);

        newTripBinding.txtStartDate.setText(t_day);

        newTripBinding.btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTripBinding.calendar.selectDate(today);
                newTripBinding.txtStartDate.setText(t_day);
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

                ArrayList<Integer> start = changeForm(startDate);
                ArrayList<Integer> end = changeForm(endDate);

                String s_day = "" + start.get(0) + "." + start.get(1) + "." + start.get(2);
                String e_day = "" + end.get(0) + "." + end.get(1) + "." + end.get(2);

                System.out.println("박일 : " + periodList.size() + "/" + periodList.size()+1);

                if (periodList.size() > 1){
                    newTripBinding.txtEndDate.setText(e_day);
                    newTripBinding.txtCountDate.setText((periodList.size()-1) + " 박 " + periodList.size() + " 일");

                }else if(periodList.size() == 1){
                    newTripBinding.txtStartDate.setText(s_day);
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

    public ArrayList<Integer> changeForm(Date d){

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        ArrayList dateList = new ArrayList<Integer>();

        dateList.add(0,year);
        dateList.add(1,month);
        dateList.add(2,day);

        return dateList;
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
