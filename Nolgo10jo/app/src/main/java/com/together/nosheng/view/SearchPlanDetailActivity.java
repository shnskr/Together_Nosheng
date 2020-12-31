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
import com.together.nosheng.R;
import com.together.nosheng.databinding.LayoutSearchItemDetailBinding;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.user.User;
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
    private Map<String, Plan> planMap = new HashMap<>();
    private Plan plan;
    private User tempUser;

    private int backgrnd =0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutSearchItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String key = getIntent().getStringExtra("Key"); //플랜 KEY

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
                userCheck();
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
            planLike.addAll(planMap.get(key).getPlanLike()); // 모든 플랜을 불러와서 특정 플랜의 플랜 라이크 불러오기
            planViewModel.userLiked(planLike, key);
        } else { //그다음 라이크
            if (plan.getPlanLike().contains(GlobalApplication.firebaseUser.getUid())){
                Log.i("detailActivity", " 이미 좋아요 눌렀음");
            } else {
                if (key != null) { //키 존재시,
                    Log.i(" 시금치를 흔들어", "뽑빠ㅃ이 ");
                    planLike.addAll(planMap.get(key).getPlanLike());
                    planViewModel.userLiked(planLike, key);
                }
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


