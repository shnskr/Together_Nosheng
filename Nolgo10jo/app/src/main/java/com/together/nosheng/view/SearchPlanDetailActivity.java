package com.together.nosheng.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.together.nosheng.R;
import com.together.nosheng.adapter.PinRecyclerAdapter;
import com.together.nosheng.databinding.LayoutSearchItemDetailBinding;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.PlanViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
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
    private Map<String, Plan> planMap = new HashMap<>();
    private Plan plan;
    private User tempUser;

    private int backgrnd =0;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutSearchItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String key = getIntent().getStringExtra("Key");

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setLiveUser();
        userViewModel.getLiveUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tempUser = user;
            }
        });
        binding.searchDetailLikeBtn.setBackgroundResource(R.drawable.ic_button_favorite_border_foreground);


        planViewModel = new ViewModelProvider(this).get(PlanViewModel.class);
        planViewModel.setPlanRepository();
        planViewModel.getPlans().observe(this, new Observer<Map<String, Plan>>() {
            @Override
            public void onChanged(Map<String, Plan> stringPlanMap) {
                planMap.putAll(stringPlanMap);
                plan = stringPlanMap.get(key);
                binding.searchDetailTitle.setText(plan.getPlanTitle());
                binding.searchDetailTheme.setText(plan.getPlanTheme());
                binding.searchDetailLike.setText(Integer.toString(plan.getPlanLike().size()));

                SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");

                binding.searchDetailDate.setText(format.format(plan.getPlanDate()));
                userCheck();
            }
        });

        planViewModel.setCurrentPlan(key);
        planViewModel.getCurrentPlan().observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(Plan plan) {
                binding.rvPinContainer.setLayoutManager(new LinearLayoutManager(SearchPlanDetailActivity.this));
                PinRecyclerAdapter adapter = new PinRecyclerAdapter(SearchPlanDetailActivity.this, plan.getPins(), key, planViewModel);
                binding.rvPinContainer.setAdapter(adapter);
                binding.rvPinContainer.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    @Override
                    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                        View child = rv.findChildViewUnder(e.getX(), e.getY());
                        int pinPosition = rv.getChildAdapterPosition(child);

                        if (e.getAction() == MotionEvent.ACTION_UP) {
                            Bundle marker = new Bundle();
                            marker.putInt("pin", pinPosition);
                            getSupportFragmentManager().setFragmentResult("marker", marker);
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





        binding.searchDetailLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(backgrnd==0){ //좋아요를 누르지 않았을때혀ㅏㅏ
                    userLike(key);
                }
                userCheck();
            }
        });


        if (key != null) {

            binding.searchBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    getUserBookmark();
                    //addBookmark(key);
                    if (!(bookmarkID.contains(key))) {
                        bookmark(key);
                    } else {
                        Log.i("testing", "이미있지롱 ");
                    }
                }

            });
        } else {
            Toast.makeText(getApplicationContext(), "이미 북마크에 추가되어 있습니다", Toast.LENGTH_LONG).show();
        }


        Fragment mapFragment = new FragmentItemGoogle();
        getSupportFragmentManager().beginTransaction().replace(binding.flContainer.getId(), mapFragment).commit();
    }

    public void bookmark(String key) {
        Map<String, List<String>> bookmarkList = new HashMap<>();
        if (bookmarkID != null) {
            bookmarkID.add(key);

        }
        bookmarkList.put("bookmarkList", bookmarkID);

        db.collection("User").document(GlobalApplication.firebaseUser.getUid()).set(bookmarkList, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }


    public void getUserBookmark() {
        bookmarkID.clear();
        bookmarkID.addAll(tempUser.getBookmarkList());

//        userViewModel.setBookmarkID();
//        userViewModel.getBookmarkID().observe(this, new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> plans) {
//                bookmarkID.addAll(plans);
//                for (String p: bookmarkID){
//                    Log.i("testing", p);
//                }
//            }
//        });
    }

    public void userLike(String key) {
        Log.i("detailActivity", " 실행중");
        List<String> planLike = new ArrayList<>();
        Log.i("detailActivity", plan.getPlanTitle());

        if (plan.getPlanLike().size() == 0) { //첫 라이크
            planLike.addAll(planMap.get(key).getPlanLike());
            planViewModel.userLiked(planLike, key);
        } else { //그다음 라이크

//            for(String s : plan.getPlanLike()){
//                Log.i("detailActivity", " 흠내흠내당근당근2");
//                if (s.equals(GlobalApplication.firebaseUser.getUid())) {
//                    Log.i("detailActivity", " 이미 좋아요 눌렀음");
//                }else {
//                    Log.i("detailActivity", " 흠내흠내당근당근3");
//                    if (key != null) {
//                        Log.i("detailActivity", " 흠내흠내");
//                        planLike.addAll(planMap.get(key).getPlanLike());
//                        planViewModel.userLiked(planLike, key);
//                    }
//                }
//            }
            if (plan.getPlanLike().contains(GlobalApplication.firebaseUser.getUid())){
                Log.i("detailActivity", " 흠내흠내 이미 있어");
            } else {
                Log.i("detailActivity", " 추가추가");
                planLike.addAll(planMap.get(key).getPlanLike());
                planViewModel.userLiked(planLike, key);
            }
        }
    }

    public void userCheck(){
        for (String s : plan.getPlanLike()){
            if(s.equals(GlobalApplication.firebaseUser.getUid())){
                binding.searchDetailLikeBtn.setBackgroundResource(R.drawable.ic_button_favorite_foreground);
                backgrnd =1;
            }else{
                binding.searchDetailLikeBtn.setBackgroundResource(R.drawable.ic_button_favorite_border_foreground);
            }
        }
    }


}


