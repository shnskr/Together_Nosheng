package com.together.nosheng.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

public class BudgetFragmentActivity extends Fragment {

    private ActivityFragmentBudgetBinding binding;

    private ProjectViewModel projectViewModel;

    private String projectId;

    private Project currentProject;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFragmentBudgetBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        projectId = requireActivity().getIntent().getStringExtra("projectId");

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                currentProject = stringProjectMap.get(projectId);
                Map<String, Budget> budgets = currentProject.getBudgets();

                Budget food = budgets.get("식비");
                Budget room = budgets.get("숙박비");
                Budget transportation = budgets.get("교통비");
                Budget emergency = budgets.get("비상금");
                Budget etc = budgets.get("기타");

                int foodTotal = food.getTotal();
                int roomTotal = room.getTotal();
                int transportationTotal = transportation.getTotal();
                int emergencyTotal = emergency.getTotal();
                int etcTotal = etc.getTotal();

                int total = foodTotal + roomTotal + transportationTotal + emergencyTotal + etcTotal;

                DecimalFormat format = new DecimalFormat("#,###");

                binding.etxtFood.setText(format.format(foodTotal));
                binding.etxtRoom.setText(format.format(roomTotal));
                binding.etxtTransportation.setText(format.format(transportationTotal));
                binding.etxtEmergency.setText(format.format(emergencyTotal));
                binding.etxtEtc.setText(format.format(etcTotal));

                binding.etxtTotal.setText(format.format(total));
                binding.etxtDivide.setText(format.format(total / currentProject.getMembers().size()));

                addTextChangedListener(binding.etxtFood);
                addTextChangedListener(binding.etxtRoom);
                addTextChangedListener(binding.etxtTransportation);
                addTextChangedListener(binding.etxtEmergency);
                addTextChangedListener(binding.etxtEtc);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int food = Integer.parseInt(binding.etxtFood.getText().toString().replaceAll(",", ""));
                int room = Integer.parseInt(binding.etxtRoom.getText().toString().replaceAll(",", ""));
                int transportation = Integer.parseInt(binding.etxtTransportation.getText().toString().replaceAll(",", ""));
                int emergency = Integer.parseInt(binding.etxtEmergency.getText().toString().replaceAll(",", ""));
                int etc = Integer.parseInt(binding.etxtEtc.getText().toString().replaceAll(",", ""));

                Map<String, Budget> budgets = new HashMap<>();
                Budget bFood = new Budget();
                bFood.setTotal(food);
                Budget bRoom = new Budget();
                bRoom.setTotal(room);
                Budget bTransportation = new Budget();
                bTransportation.setTotal(transportation);
                Budget bEmergency = new Budget();
                bEmergency.setTotal(emergency);
                Budget bEtc = new Budget();
                bEtc.setTotal(etc);

                budgets.put("식비", bFood);
                budgets.put("숙박비", bRoom);
                budgets.put("교통비", bTransportation);
                budgets.put("비상금", bEmergency);
                budgets.put("기타", bEtc);

                projectViewModel.updateBudgets(projectId, budgets);

                Toast.makeText(requireContext(), "저장 완료", Toast.LENGTH_SHORT).show();
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

                int total = food + room + transportation + emergency + etc;

                DecimalFormat format = new DecimalFormat("#,###");

                binding.etxtTotal.setText(format.format(total));
                binding.etxtDivide.setText(format.format(total / currentProject.getMembers().size()));
            }
        });
    }
}