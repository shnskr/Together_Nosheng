package com.together.nosheng.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;
import com.together.nosheng.adapter.PinRecyclerAdapter;
import com.together.nosheng.databinding.LayoutSearchItemDetailBinding;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.PlanViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class SearchPlanDetailActivity extends AppCompatActivity {

    private LayoutSearchItemDetailBinding binding;

    private PlanViewModel planViewModel;
    private UserViewModel userViewModel;

    private User currentUser;
    private Plan currentPlan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutSearchItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String planId = getIntent().getStringExtra("planId");

        Context context = SearchPlanDetailActivity.this;

        getSupportFragmentManager().beginTransaction().replace(binding.flContainer.getId(), new FragmentItemGoogle()).commit();

        binding.rvPinContainer.setLayoutManager(new LinearLayoutManager(SearchPlanDetailActivity.this));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        planViewModel = new ViewModelProvider(this).get(PlanViewModel.class);

        planViewModel.setCurrentPlan(planId);

        planViewModel.getCurrentPlan().observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(Plan plan) {
                currentPlan = plan;
                binding.searchDetailTitle.setText(currentPlan.getPlanTitle());
                binding.searchDetailTheme.setText(currentPlan.getPlanTheme());

                SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
                binding.searchDetailDate.setText(format.format(currentPlan.getPlanDate()));

                binding.searchDetailLike.setText(String.valueOf(currentPlan.getPlanLike().size()));

                if (currentPlan.getPlanLike().contains(GlobalApplication.firebaseUser.getUid())) {
                    binding.searchDetailLikeBtn.setBackgroundResource(R.drawable.ic_button_favorite_foreground);
                } else {
                    binding.searchDetailLikeBtn.setBackgroundResource(R.drawable.ic_button_favorite_border_foreground);
                }

                binding.rvPinContainer.setAdapter(new PinRecyclerAdapter(context, currentPlan.getPins(), planId, planViewModel));
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

        userViewModel.setLiveUser();
        userViewModel.getLiveUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                currentUser = user;
                if (user.getBookmarkList().contains(planId)) {
                    binding.searchBookmark.setText("북마크에서 삭-제!");
                } else {
                    binding.searchBookmark.setText("북마크에 저-장!");
                }
            }
        });

        binding.searchBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.updateUserBookmarList(planId, currentUser.getBookmarkList());
            }
        });

        binding.searchDetailLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> planLike = currentPlan.getPlanLike();
                String uid = GlobalApplication.firebaseUser.getUid();

                if (planLike.contains(uid)) {
                    planLike.remove(uid);
                } else {
                    planLike.add(uid);
                }

                currentPlan.setPlanLike(planLike);

                planViewModel.updatePlan(planId, currentPlan);
            }
        });
    }

}


