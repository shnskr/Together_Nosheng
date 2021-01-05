package com.together.nosheng.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.MutableData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.model.pin.Pin;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanRepository {
    private FirebaseFirestore db;

    private final String TAG = "PlanRepostiory";

    public PlanRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<Map<String, Plan>> getPublicPlans() {
        MutableLiveData<Map<String, Plan>> publicPlans = new MutableLiveData<>();

        db.collection("Plan").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                if (value != null) {
                    Map<String, Plan> temp = new HashMap<>();
                    List<DocumentSnapshot> docs = value.getDocuments();
                    for (DocumentSnapshot doc : docs) {
                        if (doc.toObject(Plan.class).isOpen()) {
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

    public MutableLiveData<Plan> getCurrentPlan(String planId) {
        MutableLiveData<Plan> planLiveData = new MutableLiveData<>();
        db.collection("Plan").document(planId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                }

                if (value != null && value.exists()) {
                    planLiveData.setValue(value.toObject(Plan.class));
                }
            }
        });

        return planLiveData;
    }

    public void updatePins(String planId, List<Pin> pins) {
        db.collection("Plan").document(planId).update("pins", pins);
    }

    public MutableLiveData<Map<String, Plan>> getUserBookmark() {
        MutableLiveData<Map<String, Plan>> userBookmarkList = new MutableLiveData<>();
        Map<String, Plan> bookmarkList = new HashMap<>();

        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "addShapshotListener failed", error);
                    return;
                }

                if (value != null) {
                    bookmarkList.clear();
                    userBookmarkList.setValue(bookmarkList);
                    List<String> bookmarkListId = value.toObject(User.class).getBookmarkList();

                    for (String bookmarkId : bookmarkListId) {
                        db.collection("Plan").document(bookmarkId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                bookmarkList.put(documentSnapshot.getId(), documentSnapshot.toObject(Plan.class));
                                userBookmarkList.setValue(bookmarkList);
                            }
                        });
                    }
                }
            }
        });

        return userBookmarkList;
    }

    public void updatePlan(String planId, Plan plan) {
        db.collection("Plan").document(planId).set(plan, SetOptions.merge());
    }
}
