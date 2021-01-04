package com.together.nosheng.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.together.nosheng.model.pin.Pin;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.repository.PlanRepository;

import java.util.List;
import java.util.Map;

public class PlanViewModel extends ViewModel {
    private PlanRepository planRepository;
    private LiveData<Map<String, Plan>> userProjects;
    private LiveData<Map<String,Plan>> publicPlans;
    private LiveData<Map<String, Plan>> userBookmarkList;

    private LiveData<Plan> currentPlan;

    public PlanViewModel() {
        planRepository = new PlanRepository();
    }
    public LiveData<Map<String, Plan>> getPlans() {return userProjects;}

    public void setPlanRepository() {
        userProjects =planRepository.getPlans();
    }

    public LiveData<Map<String, Plan>> getPublicPlans (){return publicPlans;}
    public void setPublicPlans() {publicPlans = planRepository.getPublicPlans();}

    public void userLiked (List<String> prevData, String s){
        planRepository.planLiked(prevData, s);
    }

    public void setCurrentPlan(String planId) {
        currentPlan = planRepository.getCurrentPlan(planId);
    }

    public LiveData<Plan> getCurrentPlan() {
        return currentPlan;
    }

    public void updatePins(String planId, List<Pin> pins) {
        planRepository.updatePins(planId, pins);
    }

    public void setUserBookmark() {
        userBookmarkList = planRepository.getUserBookmark();
    }

    public LiveData<Map<String, Plan>> getUserBookmark() {
        return userBookmarkList;
    }
}