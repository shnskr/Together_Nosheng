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

    private Project project;
    private ProjectViewModel projectViewModel;
    private UserViewModel userViewModel;
    private ActivityNewTripBinding newTripBinding;

    private String TAG = "NewTripActivity";

    private String joinCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newTripBinding = ActivityNewTripBinding.inflate(getLayoutInflater());
        setContentView(newTripBinding.getRoot());

        project = new Project();
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        List<String> members = project.getMembers();
        List<String> projectList = userViewModel.getMemberProject(GlobalApplication.firebaseUser.getUid());
        List<String> allProjectList = projectViewModel.getProjectList();

        //join project
        newTripBinding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinCode = newTripBinding.etxtJoinCode.getText().toString();

                boolean key = false;
                boolean key2 = true;

                if(joinCode.length() > 0){
                    joinCode.trim();
                    if(allProjectList.contains(joinCode)){
                        if(!projectList.contains(joinCode)){
                            members.add(GlobalApplication.firebaseUser.getUid());
                            projectList.add(joinCode);
                            projectViewModel.addMember(joinCode,members);
                            userViewModel.updateUserProjectList(GlobalApplication.firebaseUser.getUid(),projectList);
                            key2 = false;
                            finish();
                        }
                        key = true;
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"코드를 입력하세요!", Toast.LENGTH_SHORT).show();
                }

                if(key == false){
                    Toast.makeText(getApplicationContext(),"존재하지 않는 코드입니다!", Toast.LENGTH_SHORT).show();
                } else if(key == true && key2 == true){
                    Toast.makeText(getApplicationContext(),"이미 추가된된 코드입니다!", Toast.LENGTH_SHORT).show();
                }

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
                newTripBinding.txtCountLength.setText(15-s.length()+"글자");   //글자수 TextView에 보여주기.
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
                joinCode = newTripBinding.etxtJoinCode.getText().toString();
                Log.i(TAG+"글자수", joinCode+"??>>");
                if(joinCode.length() == 0){
                    if(project.getStartDate() != null){
                        List<String> members = new ArrayList<>();
                        Map<String, List<String>> userTags = new HashMap<>();

                        members.add(GlobalApplication.firebaseUser.getUid());
                        userTags.put(members.get(0), new ArrayList<>());

                        project.setTitle(newTripBinding.etxtTitle.getText().toString());
                        project.setRegDate(new Date());
                        project.setMembers(members);
                        project.setUserTags(userTags);
                        projectViewModel.addUserProject(project);

                        Intent intent = new Intent(NewTripActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"날짜를 선택해주세요!", Toast.LENGTH_SHORT).show();
                        Log.i("날짜를 선택해주세요!!!!!!!", "3");
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"여행기 참여하시려면\nJoin을 눌러주세요!", Toast.LENGTH_SHORT).show();
                    Log.i("Join project", "3");
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
