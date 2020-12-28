package com.together.nosheng.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.together.nosheng.R;
import com.together.nosheng.adapter.BookmarkAdapter;
import com.together.nosheng.databinding.SettingFragmentBookmarkBinding;
import com.together.nosheng.model.plan.Plan;

import com.together.nosheng.viewmodel.PlanViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingFragmentBookmark extends Fragment {
    private SettingFragmentBookmarkBinding binding;
    private RecyclerView mRecyclerView;
    private UserViewModel userViewModel;
    private BookmarkAdapter bookmarkAdapter;
    private List<String> planId = new ArrayList<>();

    private List<Plan> bookmarkList = new ArrayList<>();

    public static SettingFragmentBookmark newInstance() {
        return new SettingFragmentBookmark();
    }

//    private List<String> s = new ArrayList<>();

    private HashBiMap<String, Plan> biMap;//키 검색용 해쉬맵
    private BiMap<Plan, String> biMap2;//키 검색용 해쉬맵

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment_bookmark, container, false);
        View view = binding.getRoot();
        Context context = view.getContext();

        mRecyclerView = view.findViewById(R.id.setting_listView_bookmark);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        PlanViewModel planViewModel = new ViewModelProvider(requireActivity()).get(PlanViewModel.class);
        planViewModel.setPlanRepository();

        userViewModel.setBookmarkList();
        userViewModel.getBookmarkList().observe(getViewLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> plans) {
                if (plans == null) {
                    Toast.makeText(context, "북마크 없어!", Toast.LENGTH_LONG).show();
                } else {
                    bookmarkList.addAll(plans);
                    bookmarkAdapter = new BookmarkAdapter(plans, context);
                    binding.settingListViewBookmark.setAdapter(bookmarkAdapter);
                    bookmarkAdapter.setOnItemClickListener(new BookmarkAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            String testing = plans.get(position).getPlanTitle();
                            Log.i("뭐지 이게", testing);

                            planViewModel.getPlans().observe(getViewLifecycleOwner(), new Observer<Map<String, Plan>>() {
                                @Override
                                public void onChanged(Map<String, Plan> stringPlanMap) {
                                    List<Plan> keyList = new ArrayList<>();
                                    keyList.addAll(stringPlanMap.values());
                                    Log.i("onChanged",Integer.toString(keyList.size()));
                                    String key = null;
                                    
                                    for (int x =0; x< keyList.size(); x++){
                                        if(keyList.get(x).getPlanTitle().equals(plans.get(position).getPlanTitle())){

                                            biMap = HashBiMap.create(stringPlanMap);
                                            biMap2 = biMap.inverse();

                                            key= biMap2.get(keyList.get(x)); //선택된 플랜의 키 값 보내주기
                                        }
                                    }
                                    Intent intent = new Intent(context, SearchPlanDetailActivity.class);
                                    intent.putExtra("Key", key);

                                    //plan 정보 다 보내주기
                                    startActivity(intent);
                                    }
                                }
                            );

                            
                        }
                    });

                }
            }
        });
        return view;
    }

    }