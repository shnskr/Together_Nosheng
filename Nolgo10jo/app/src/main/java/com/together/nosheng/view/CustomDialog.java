package com.together.nosheng.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.together.nosheng.R;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.viewmodel.PlanViewModel;

public class CustomDialog {

    private Context context;
    private Plan plan;
    private PlanViewModel planViewModel;
    private String planId;

    public CustomDialog(Context context, Plan plan, PlanViewModel planViewModel, String planId) {
        this.context = context;
        this.plan = plan;
        this.planViewModel = planViewModel;
        this.planId = planId;
    }

    public void init() {
        Dialog dlg = new Dialog(context);

        dlg.setContentView(R.layout.dialog_plan_open);

        dlg.show();

        EditText etTitle = dlg.findViewById(R.id.et_title);
        EditText etTheme = dlg.findViewById(R.id.et_theme);
        Button btnOk = dlg.findViewById(R.id.btn_ok);
        Button btnCancle = dlg.findViewById(R.id.btn_cancel);
        RadioGroup rgRadio = dlg.findViewById(R.id.rg_radio);
        RadioButton rbTrue = dlg.findViewById(R.id.rb_true);
        RadioButton rbFalse = dlg.findViewById(R.id.rb_false);
        TextView tvTrue = dlg.findViewById(R.id.tv_true);
        TextView tvFalse = dlg.findViewById(R.id.tv_false);

        etTitle.setText(plan.getPlanTitle());
        etTheme.setText(plan.getPlanTheme());

        if (plan.isOpen()) {
            rbTrue.setChecked(true);
        } else {
            rbFalse.setChecked(true);
        }

        tvTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbTrue.setChecked(true);
            }
        });

        tvFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbFalse.setChecked(true);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTitle.length() < 1 || etTheme.length() < 1) {
                    Toast.makeText(context, "빈칸이 존재합니다 !", Toast.LENGTH_SHORT).show();
                } else {
                    if (rbTrue.isChecked()) {
                        plan.setOpen(true);
                    } else {
                        plan.setOpen(false);
                    }
                    plan.setPlanTitle(etTitle.getText().toString());
                    plan.setPlanTheme(etTheme.getText().toString());


                    planViewModel.updatePlan(planId, plan);
                    dlg.dismiss();
                }
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
    }
}
