package com.together.nosheng.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
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

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.timessquare.CalendarPickerView;
import com.together.nosheng.databinding.ActivityFragmentTripinfoBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class TripInfoFragmentActivity extends Fragment {

    public int count;

    private Date today;
    private ActivityFragmentTripinfoBinding tripinfoBinding;
    private BottomBtnActivity bottomBtnActivity;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "DB 연동 가즈아~~~~~~~~~~~";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripinfoBinding = ActivityFragmentTripinfoBinding.inflate(inflater, container, false);
        View view = tripinfoBinding.getRoot();


        String tripCode = tripinfoBinding.txtTripCode.getText().toString();

        tripinfoBinding.btnDuplicate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Trip Code", tripCode);
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(getContext(), "Trip Code 복사 완료", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        tripinfoBinding.etxtTitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER) {
                    Log.i("엔터", "1");
                    return false;
                } else
                    Log.i("엔터","2");
                    return true;
            }
        });

        today = new Date();
        ArrayList<Integer> today_int = changeForm(today);
        String t_day = "" + today_int.get(0) + "." + today_int.get(1) + "." + today_int.get(2);

        tripinfoBinding.txtStartDate.setText(t_day);

        tripinfoBinding.btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripinfoBinding.calendar.selectDate(today);
                tripinfoBinding.txtStartDate.setText(t_day);
                tripinfoBinding.txtEndDate.setText("endDate");
            }
        });


        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,2);

        tripinfoBinding.calendar.init(today,nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);



        tripinfoBinding.calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                List<Date> periodList = tripinfoBinding.calendar.getSelectedDates();

                Date startDate = periodList.get(0);
                Date endDate = periodList.get(periodList.size()-1);

                ArrayList<Integer> start = changeForm(startDate);
                ArrayList<Integer> end = changeForm(endDate);

                String s_day = "" + start.get(0) + "." + start.get(1) + "." + start.get(2);
                String e_day = "" + end.get(0) + "." + end.get(1) + "." + end.get(2);

                System.out.println("박일 : " + periodList.size() + "/" + periodList.size()+1);

                if (periodList.size() > 1){
                    tripinfoBinding.txtEndDate.setText(e_day);
                    tripinfoBinding.txtCountDate.setText((periodList.size()-1) + " 박 " + periodList.size() + " 일");

                }else if(periodList.size() == 1){
                    tripinfoBinding.txtStartDate.setText(s_day);
                    tripinfoBinding.txtEndDate.setText("endDate");
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        return view;

    }   //onCreateView()

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);//기존
        bottomBtnActivity = (BottomBtnActivity) getActivity();//기존
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
