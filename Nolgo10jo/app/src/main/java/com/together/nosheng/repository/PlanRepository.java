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
    private MutableLiveData<Map<String, Plan>> publicPlans= new MutableLiveData<>();
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
                    Log.i("daldal", "시작");
                    for (DocumentSnapshot doc: docs) {
                        Log.i("daldal", doc.getData().toString());
//                        temp.put(doc.getId(), doc.toObject(Plan.class));
                        Log.i("daldal", "끝");
                    }
                    plans.setValue(temp);
                } else {
                    Log.d(TAG, "Current data:null");
                }
            }
        });
        return plans;


    }

    public MutableLiveData<Map<String, Plan>> getPublicPlans() {
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
                        if(doc.toObject(Plan.class).isOpen()) {
                            Log.i("testing1111","되고있는건감");
                            temp.put(doc.getId(), doc.toObject(Plan.class));
                        }
                    }
                    publicPlans.setValue(temp);
                } else {
                    Log.d(TAG, "Current data:null");
                }
            }
        });
        return publicPlans;
    }


}
