package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.MutableData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.together.nosheng.model.plan.Plan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanRepository {
    private FirebaseFirestore db;
    private MutableLiveData<Map<String, Plan>> plans= new MutableLiveData<>();
    private final String TAG = "PlanRepostiory";

    public PlanRepository(){ db= FirebaseFirestore.getInstance();
    getPlans(); }

    public MutableLiveData<Map<String, Plan>> getPlans() {
        db.collection("Plan").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                if(value != null) {
                    Map<String, Plan> temp = new HashMap<>();
                    List<DocumentSnapshot> docs = value.getDocuments();
                    for (DocumentSnapshot doc: docs) {
                        temp.put(doc.getId(), doc.toObject(Plan.class));
                    }
                    plans.setValue(temp);
                } else {
                    Log.d(TAG, "Current data:null");
                }
            }
        });
        return plans;
    }






}
