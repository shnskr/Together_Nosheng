package com.together.nosheng.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.together.nosheng.databinding.ActivityFragmentHomeBinding;

import java.util.HashMap;
import java.util.Map;


public class HomeFragmentActivity extends Fragment {

    private ActivityFragmentHomeBinding homeBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityFragmentHomeBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();



        homeBinding.btnNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BottomBtnActivity.class);
                startActivity(intent);

                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String TAG = "DB 연동 가즈아~~~~~~~~~~~";

                // Create a new user with a first and last name
                Map<String, Object> project = new HashMap<>();

                db.collection("Project")
                        .add(project)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });
        return view;
    }

}
