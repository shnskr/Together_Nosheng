package com.together.nosheng.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.databinding.LayoutSearchItemDetailBinding;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.PlanViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPlanDetailActivity extends AppCompatActivity {
    private LayoutSearchItemDetailBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Map<String, Plan> temp = new HashMap<>();

    private PlanViewModel planViewModel;
    private List<Plan> planSearchList;//검색용 리스트

    private UserViewModel userViewModel;

    private List<String> bookmarkID = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutSearchItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("Title");
        String theme = getIntent().getStringExtra("Theme");
        int like = getIntent().getIntExtra("Like", 0);
        String key = getIntent().getStringExtra("Key");

        binding.searchDetailTitle.setText(title);
        binding.searchDetailTheme.setText(theme);
        binding.searchDetailLike.setText(Integer.toString(like));



        getUserBookmark();
        if (key != null) {
            binding.searchBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //addBookmark(key);
                    if (!(bookmarkID.contains(key))) {
                        bookmark(key);
                    } else {
                        Log.i("testing", "이미있지롱 ");
                    }
                }

            });
        } else{
            Toast.makeText(getApplicationContext(), "이미 북마크에 추가되어 있습니다", Toast.LENGTH_LONG).show();
        }
    }

    public void bookmark (String key){
        Map <String, List<String>> bookmarkList = new HashMap<>();
        if (bookmarkID != null){
                bookmarkID.add(key);

        }
        bookmarkList.put("bookmarkList", bookmarkID);

        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).set(bookmarkList,SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }


        public void getUserBookmark() {
        bookmarkID.clear();
        userViewModel= new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setBookmarkID();
        userViewModel.getBookmarkID().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> plans) {
                bookmarkID.addAll(plans);
                for (String p: bookmarkID){
                    Log.i("testing", p);
                }
            }
        });
        }
}
