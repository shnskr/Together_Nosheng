package com.together.nosheng.view;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.timessquare.CalendarPickerView;
import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityFragmentTripinfoBinding;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.content.Context.CLIPBOARD_SERVICE;

public class TripInfoFragmentActivity extends Fragment {

    private Project project;
    private ProjectViewModel projectViewModel;

    private String projectId;
    private ArrayList tripPeriod;

    private ActivityFragmentTripinfoBinding tripinfoBinding;

    String TAG = "TripInfoFragmentActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripinfoBinding = ActivityFragmentTripinfoBinding.inflate(inflater, container, false);
        View view = tripinfoBinding.getRoot();

        project = new Project();
        projectViewModel = new ViewModelProvider(getActivity()).get(ProjectViewModel.class);

        projectId = getActivity().getIntent().getStringExtra("projectId");
        String title =  projectViewModel.getCurrentProject().getValue().get(projectId).getTitle();
        project.setTitle(title);
        tripinfoBinding.setProjectId(projectId);
        tripinfoBinding.setTest(project);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                Log.i("들어오나?", "??");
                initDate();
                init();
            }
        });

        //code duplicate
        tripinfoBinding.btnDuplicate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Trip Code", projectId);
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(getContext(), "Trip Code 복사 완료", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        //EditText 리스너 (입력시 반응)
        tripinfoBinding.etxtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                tripinfoBinding.txtCountLength.setText(25-s.length()+"글자");   //글자수 TextView에 보여주기.
            }
        });

        //calendar function
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        Calendar pastYear = Calendar.getInstance();
        pastYear.add(Calendar.YEAR, -1);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,2);

        initDate();

        Log.i(TAG+"tripPeriod", tripPeriod.toString()+"2");

        tripinfoBinding.calendar.init(pastYear.getTime(), nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDates(tripPeriod);

        tripinfoBinding.calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                List<Date> periodList = tripinfoBinding.calendar.getSelectedDates();

                Date startDate = periodList.get(0);
                project.setStartDate(startDate);
                Date endDate = periodList.get(periodList.size()-1);
                project.setEndDate(endDate);

                if (periodList.size() > 1){
                    tripinfoBinding.txtEndDate.setText(dateFormat.format(endDate));
                    tripinfoBinding.txtCountDate.setText((periodList.size()-1) + " 박 " + periodList.size() + " 일");

                }else if(periodList.size() == 1){
                    tripinfoBinding.txtStartDate.setText(dateFormat.format(startDate));
                    tripinfoBinding.txtEndDate.setText("endDate");
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        tripinfoBinding.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog();
            }
        });
        return view;

    }   //end onCreateView()


    public void createAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("일정 수정!")
                .setMessage("일정 내용이 전부 삭제됩니다.\n수정하시겠습니까?")
                .setIcon(R.drawable.ic_baseline_announcement_24);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                projectViewModel.updateUserProject(project, projectId);
                Toast.makeText(getActivity(), "일정이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                Log.i("일정이 수정되었습니다.", "0");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("일정이 유지됩니다.", "0");

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void initDate(){
        tripPeriod = new ArrayList<>();

        tripPeriod.add(projectViewModel.getCurrentProject().getValue().get(projectId).getStartDate());
        tripPeriod.add(projectViewModel.getCurrentProject().getValue().get(projectId).getEndDate());

        Log.i(TAG+"tripPeriod", tripPeriod.toString()+"3");
    }

    public void init() {
        project.setBudgets(projectViewModel.getCurrentProject().getValue().get(projectId).getBudgets());
        project.setCheckLists(projectViewModel.getCurrentProject().getValue().get(projectId).getCheckLists());
        project.setMembers(projectViewModel.getCurrentProject().getValue().get(projectId).getMembers());
        project.setPlans(projectViewModel.getCurrentProject().getValue().get(projectId).getPlans());
        project.setPosts(projectViewModel.getCurrentProject().getValue().get(projectId).getPosts());
        project.setRegDate(projectViewModel.getCurrentProject().getValue().get(projectId).getRegDate());
        project.setTags(projectViewModel.getCurrentProject().getValue().get(projectId).getTags());
    }

}
