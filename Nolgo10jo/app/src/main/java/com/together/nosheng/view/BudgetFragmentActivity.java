package com.together.nosheng.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityFragmentBudgetBinding;

import java.text.DecimalFormat;

public class BudgetFragmentActivity extends Fragment {

    private ActivityFragmentBudgetBinding budgetBinding;
    private FirebaseFirestore db;

    private DecimalFormat df;
    private DecimalFormat dfnd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        budgetBinding = ActivityFragmentBudgetBinding.inflate(inflater, container, false);
        View view = budgetBinding.getRoot();


        return view;
    }

}