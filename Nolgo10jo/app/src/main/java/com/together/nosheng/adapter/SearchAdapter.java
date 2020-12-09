package com.together.nosheng.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.view.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchAdapter extends BaseAdapter {
    Map<String, Plan> plans;
    List<String> planId;

    public SearchAdapter(Map<String,Plan> plans) {this.plans =plans;
    planId = new ArrayList<>(plans.keySet());
    }

    public void addItem(Plan plan) {plans.put(planId.get(planId.size()),plan);}

    @Override
    public int getCount() {
        return plans.size();
    }

    @Override
    public Object getItem(int position) {
        return plans.get(planId.get(position));
    }

    @Override
    public long getItemId(int position) {
        return planId.indexOf(planId.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchView searchView = new SearchView((parent.getContext()));
        Plan plan = (Plan) getItem(position);

        searchView.setPlanTitle(plan.getPlanTitle());
        searchView.setPlanTheme(plan.getPlanTheme());
        searchView.setPlanLike(plan.getPlanLike());

        return searchView;
    }
}
