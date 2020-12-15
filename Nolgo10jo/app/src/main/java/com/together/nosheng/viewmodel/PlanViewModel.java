package com.together.nosheng.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.repository.PlanRepository;

import java.util.Map;

public class PlanViewModel extends ViewModel {
    private LiveData<Map<String, Plan>> userProjects;
    private PlanRepository planRepository;

    public PlanViewModel() {
        planRepository = new PlanRepository();
        //userProjects = planRepository.getPlans();
    }
    public LiveData<Map<String, Plan>> getPlans() {return userProjects;}

    public void setPlanRepository() {
        userProjects =planRepository.getPlans();
    }

}