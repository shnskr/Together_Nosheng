package com.together.nosheng.view;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.timessquare.CalendarPickerView;
import com.together.nosheng.databinding.ActivityNewTripBinding;
import com.together.nosheng.model.project.CheckList;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.ProjectViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTripActivity extends AppCompatActivity {

    private ProjectViewModel projectViewModel;
    private ActivityNewTripBinding newTripBinding;

    private String TAG = "NewTripActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newTripBinding = ActivityNewTripBinding.inflate(getLayoutInflater());
        setContentView(newTripBinding.getRoot());

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        newTripBinding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String joinCode = newTripBinding.etxtJoinCode.getText().toString();

                projectViewModel.searchProject(NewTripActivity.this, joinCode);
                finish();
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
                newTripBinding.txtCountLength.setText(15 - s.length() + "글자");   //글자수 TextView에 보여주기.
            }
        });

        //calendar function
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd");   //추가

        Date today = new Date();

        newTripBinding.btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTripBinding.calendar.selectDate(today);
                newTripBinding.txtStartDate.setText(dateFormat.format(today));
                newTripBinding.txtPeriodMark.setText("");
                newTripBinding.txtEndDate.setText("");
                newTripBinding.txtCountDate.setText("당일치기 !");
            }
        });

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 2);

        newTripBinding.calendar.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);

        newTripBinding.calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                List<Date> periodList = newTripBinding.calendar.getSelectedDates();

                int days = periodList.size();

                Date startDate = periodList.get(0);
                Date endDate = periodList.get(days - 1);

                if (days < 2) {
                    newTripBinding.txtStartDate.setText(dateFormat.format(startDate));
                    newTripBinding.txtPeriodMark.setText("");
                    newTripBinding.txtEndDate.setText("");
                    newTripBinding.txtCountDate.setText("당일치기 !");
                } else {
                    newTripBinding.txtPeriodMark.setText("~");
                    newTripBinding.txtEndDate.setText(dateFormat.format(endDate));
                    newTripBinding.txtCountDate.setText(days - 1 + " 박 " + days + " 일");
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
                String title = newTripBinding.etxtTitle.getText().toString();
                List<Date> days = newTripBinding.calendar.getSelectedDates();

                if (title.length() < 0) {
                    Toast.makeText(NewTripActivity.this, "제목을 입력 해주세요 !", Toast.LENGTH_SHORT).show();
                } else if (days.size() < 1) {
                    Toast.makeText(NewTripActivity.this, "날짜를 선택 해주세요 !", Toast.LENGTH_SHORT).show();
                } else {
                    String uid = GlobalApplication.firebaseUser.getUid();

                    Project project = new Project();
                    project.setTitle(title);
                    project.setRegDate(new Date());
                    project.setStartDate(days.get(0));
                    project.setEndDate(days.get(days.size()-1));

                    Map<String, List<String>> userTags = project.getUserTags();
                    userTags.put(uid, new ArrayList<>());
                    project.setUserTags(userTags);

                    Map<String, CheckList> checkLists = project.getCheckLists();
                    checkLists.put(uid, new CheckList());
                    project.setCheckLists(checkLists);

                    List<String> members = project.getMembers();
                    members.add(uid);
                    project.setMembers(members);

                    projectViewModel.addUserProject(project);

                    Toast.makeText(NewTripActivity.this, "여행기가 생성 되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
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
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
