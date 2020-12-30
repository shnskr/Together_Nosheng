package com.together.nosheng.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.databinding.ActivityFragmentBudgetBinding;
import com.together.nosheng.model.project.Budget;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.text.DecimalFormat;
import java.util.Map;

public class BudgetFragmentActivity extends Fragment {

    private ActivityFragmentBudgetBinding binding;

    private ProjectViewModel projectViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFragmentBudgetBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String projectId = requireActivity().getIntent().getStringExtra("projectId");

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                Map<String, Budget> budgets = stringProjectMap.get(projectId).getBudgets();

                Budget food = budgets.get("식비");
                Budget room = budgets.get("숙박비");
                Budget transportation = budgets.get("교통비");
                Budget emergency = budgets.get("비상금");
                Budget etc = budgets.get("기타");

                DecimalFormat format = new DecimalFormat("#,###");

                binding.etxtFood.setText(format.format(food.getTotal()));
                binding.etxtRoom.setText(format.format(room.getTotal()));
                binding.etxtTransportation.setText(format.format(transportation.getTotal()));
                binding.etxtEmergency.setText(format.format(emergency.getTotal()));
                binding.etxtEtc.setText(format.format(etc.getTotal()));

                binding.etxtTotal.setText(format.format(food.getTotal() + room.getTotal() + transportation.getTotal() + emergency.getTotal() + emergency.getTotal()));

                addTextChangedListener(binding.etxtFood);
                addTextChangedListener(binding.etxtRoom);
                addTextChangedListener(binding.etxtTransportation);
                addTextChangedListener(binding.etxtEmergency);
                addTextChangedListener(binding.etxtEtc);
            }
        });

        //ocr 버튼!
        binding.ocrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ScanOCR.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void addTextChangedListener(EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et.removeTextChangedListener(this);

                if (s.length() < 1) {
                    et.setText("0");
                    et.setSelection(1);
                } else {
                    int text = Integer.parseInt(s.toString().replaceAll(",", ""));
                    String str = new DecimalFormat("#,###").format(text);
                    et.setText(str);
                    et.setSelection(str.length());
                }

                et.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
                int food = Integer.parseInt(binding.etxtFood.getText().toString().replaceAll(",", ""));
                int room = Integer.parseInt(binding.etxtRoom.getText().toString().replaceAll(",", ""));
                int transportation = Integer.parseInt(binding.etxtTransportation.getText().toString().replaceAll(",", ""));
                int emergency = Integer.parseInt(binding.etxtEmergency.getText().toString().replaceAll(",", ""));
                int etc = Integer.parseInt(binding.etxtEtc.getText().toString().replaceAll(",", ""));

                binding.etxtTotal.setText(String.valueOf(food + room + transportation + emergency + etc));
            }
        });
    }
}