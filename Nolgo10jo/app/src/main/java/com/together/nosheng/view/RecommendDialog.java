package com.together.nosheng.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.together.nosheng.R;

import java.util.ArrayList;
import java.util.List;

public class RecommendDialog {

    private Context context;

    private TextView tvGender, tvMan, tvWoman , tvMember, tvAge, tvMonth;
    private RadioGroup rgRadio;
    private RadioButton rbMan, rbWoman;
    private EditText etMember;
    private Spinner spAge, spMonth;
    private Button btnOk, btnCancel;

    public RecommendDialog(Context context) {
        this.context = context;
    }

    public void init() {
        Dialog dlg = new Dialog(context);
        dlg.setContentView(R.layout.dialog_recommend);
        dlg.show();

        tvGender = dlg.findViewById(R.id.tv_gender);
        tvMan = dlg.findViewById(R.id.tv_man);
        tvWoman = dlg.findViewById(R.id.tv_woman);
        tvMember = dlg.findViewById(R.id.tv_member);
        tvAge = dlg.findViewById(R.id.tv_age);
        tvMonth = dlg.findViewById(R.id.tv_month);
        rgRadio = dlg.findViewById(R.id.rg_radio);
        rbMan = dlg.findViewById(R.id.rb_man);
        rbWoman = dlg.findViewById(R.id.rb_woman);
        etMember = dlg.findViewById(R.id.et_member);
        spAge = dlg.findViewById(R.id.sp_age);
        spMonth = dlg.findViewById(R.id.sp_month);
        btnOk = dlg.findViewById(R.id.btn_ok);
        btnCancel = dlg.findViewById(R.id.btn_cancel);

        rbMan.setChecked(true);

        tvMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbMan.setChecked(true);
            }
        });

        tvWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbWoman.setChecked(true);
            }
        });

        List<String> ageList = new ArrayList<>();
        ageList.add("~ 20");
        ageList.add("21 ~ 30");
        ageList.add("31 ~ 40");
        ageList.add("41 ~ 50");
        ageList.add("51 ~ 60");
        ageList.add("61 ~ 70");
        ageList.add("71 ~");

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, ageList);
        spAge.setAdapter(ageAdapter);

        List<String> monthList = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            monthList.add(i + "월");
        }

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, monthList);
        spMonth.setAdapter(monthAdapter);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gender = rbMan.isChecked() ? 1 : 2;
                int member = Math.min(Integer.parseInt(etMember.getText().toString()), 8);
                int age = spAge.getSelectedItemPosition() + 1;
                int month = spMonth.getSelectedItemPosition() + 1;

                if (etMember.length() < 1 || member < 1) {
                    Toast.makeText(context, "빈칸이 존재합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    dlg.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
    }
}
