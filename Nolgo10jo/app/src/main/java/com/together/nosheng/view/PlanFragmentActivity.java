package com.together.nosheng.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.together.nosheng.R;
import com.together.nosheng.adapter.PinRecyclerAdapter;
import com.together.nosheng.databinding.ActivityFragmentPlanBinding;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.PlanViewModel;
import com.together.nosheng.viewmodel.ProjectViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class PlanFragmentActivity extends Fragment {

    private ActivityFragmentPlanBinding binding;

    private ProjectViewModel projectViewModel;
    private PlanViewModel planViewModel;

    private Project currentProject;

    private Fragment googleActivity;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFragmentPlanBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        context = requireContext();

        String projectId = requireActivity().getIntent().getStringExtra("projectId");

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        planViewModel = new ViewModelProvider(requireActivity()).get(PlanViewModel.class);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                currentProject = stringProjectMap.get(projectId);

                long days = currentProject.getEndDate().getTime() - currentProject.getStartDate().getTime();
                long diffDays = days / (1000 * 60 * 60 * 24);

                List<String> spinnerList = new ArrayList<>();

                for (int i = 0; i <= diffDays; i++) {
                    spinnerList.add((i + 1) + " 일차");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, spinnerList);
                binding.spDay.setAdapter(adapter);

                binding.spDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Bundle result = new Bundle();
                        result.putInt("position", position);
                        getChildFragmentManager().setFragmentResult("result", result);

                        String planId = currentProject.getPlans().get(position);

                        planViewModel.setCurrentPlan(planId);
                        planViewModel.getCurrentPlan().observe(getViewLifecycleOwner(), new Observer<Plan>() {
                            @Override
                            public void onChanged(Plan plan) {

                                if (plan.isOpen()) {
                                    binding.btnOpen.setText("공개");
                                } else {
                                    binding.btnOpen.setText("비공개");
                                }

                                binding.spDay.setSelection(position);
                                binding.rvPins.setLayoutManager(new LinearLayoutManager(parent.getContext()));
                                PinRecyclerAdapter adapter = new PinRecyclerAdapter(requireContext(), plan.getPins(), currentProject.getPlans().get(position), planViewModel);
                                binding.rvPins.setAdapter(adapter);
                                binding.rvPins.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                                    @Override
                                    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                                        View child = rv.findChildViewUnder(e.getX(), e.getY());
                                        int pinPosition = rv.getChildAdapterPosition(child);

                                        if (e.getAction() == MotionEvent.ACTION_UP) {
                                            Bundle marker = new Bundle();
                                            marker.putInt("pin", pinPosition);
                                            getChildFragmentManager().setFragmentResult("marker", marker);
                                        }
                                        return false;
                                    }

                                    @Override
                                    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                                    }

                                    @Override
                                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        googleActivity = new GoogleActivity();

        getChildFragmentManager().beginTransaction().replace(binding.flContainer.getId(), googleActivity).commit();

        return view;
    }

    private void setEditText(EditText et) {
        et.setSingleLine();

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et.removeTextChangedListener(this);

                if (s.length() > 10) {
                    et.setText(s.subSequence(0, 10));
                }
                et.setSelection(et.length());

                et.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}