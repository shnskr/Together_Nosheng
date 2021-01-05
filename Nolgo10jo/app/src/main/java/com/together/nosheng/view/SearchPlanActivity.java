package com.together.nosheng.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.together.nosheng.R;
import com.together.nosheng.adapter.SearchAdapter;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.user.User;
import com.together.nosheng.viewmodel.PlanViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPlanActivity  extends AppCompatActivity {
    private SearchAdapter adapter;
    private Map<String, Plan> planList;

    private List<Plan> planSearchList;//검색용 리스트
    private Map<String, Plan> searchList;//검색용 해쉬맵
    private HashBiMap<String, Plan> biMap;//키 검색용 해쉬맵
    private BiMap<Plan, String> biMap2;//키 검색용 해쉬맵

    private RecyclerView mRecyclerView;
    private PlanViewModel planViewModel;

    private EditText editText;
    private TextWatcher textWatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.editText);

        mRecyclerView = (RecyclerView) findViewById(R.id.search_listView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        planViewModel = new ViewModelProvider(this).get(PlanViewModel.class);
        //planViewModel.setPlanRepository();

        editText.addTextChangedListener(new TextWatcher() { //검색바 관련 코드
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
                adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    Intent intent = new Intent(getApplicationContext(), SearchPlanDetailActivity.class);
                                    String key = biMap2.get(planSearchList.get(position)); //DocID 보내주
                                    Log.i("TESING AT 2AM", "실행됨");


                                    intent.putExtra("Title", planSearchList.get(position).getPlanTitle());
                                    intent.putExtra("Theme", planSearchList.get(position).getPlanTheme());
                                    intent.putExtra("Like", planSearchList.get(position).getPlanLike().size());
                                    intent.putExtra("planId", key);


                                    //plan 정보 다 보내주기
                                    //intent.putExtra("Date", planSearchList.get(position).getPlanDate());
                                    startActivity(intent);
                                }});

                //                editText.setOnKeyListener(new View.OnKeyListener() {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//                        //Enter key Action
//                        if ((event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                            search(s.toString());
//                            adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View v, int position) {
//                                    Intent intent = new Intent(getApplicationContext(), SearchPlanDetailActivity.class);
//                                    String key = biMap2.get(planSearchList.get(position)); //DocID 보내주기
//
//                                    Log.i("TESING AT 2AM", "실행됨");
//
//
//                                    intent.putExtra("Title", planSearchList.get(position).getPlanTitle());
//                                    intent.putExtra("Theme", planSearchList.get(position).getPlanTheme());
//                                    intent.putExtra("Like", planSearchList.get(position).getPlanLike().size());
//                                    intent.putExtra("Key", key);
//
//
//                                    //plan 정보 다 보내주기
//                                    //intent.putExtra("Date", planSearchList.get(position).getPlanDate());
//                                    startActivity(intent);
//                                }
//                            });
//                            return true;
//                        }
//                        return false;
//                    }
//                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        planViewModel.setPublicPlans();
        planViewModel.getPublicPlans().observe(this, new Observer<Map<String, Plan>>() {
            @Override
            public void onChanged(Map<String, Plan> stringPlanMap) {
                planList = stringPlanMap;
                adapter = new SearchAdapter(planList);
                mRecyclerView.setAdapter(adapter);
                planSearchList = new ArrayList<>(stringPlanMap.values());//검색용 리스트에다가 플랜값 넣어주기
                biMap = HashBiMap.create(stringPlanMap);
                biMap2 = biMap.inverse();

                adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(getApplicationContext(), SearchPlanDetailActivity.class);
                        String planId = biMap2.get(planSearchList.get(position)); //DocID 보내주기

                        intent.putExtra("Title", planSearchList.get(position).getPlanTitle());
                        intent.putExtra("Theme", planSearchList.get(position).getPlanTheme());
                        intent.putExtra("Like", planSearchList.get(position).getPlanLike().size());
                        intent.putExtra("planId", planId);

                        startActivity(intent);
                    }
                });
            }

        });

    }
    public void search(String charText) {
        charText = charText.toLowerCase().trim();
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        searchList = new HashMap<>();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            searchList.putAll(planList);
            Log.i("testings", "testing from search"+searchList.toString());
        }
        // 문자 입력을 할때..
        else {

            // 리스트의 모든 데이터를 검색한다
            for(int x=0; x<planSearchList.size(); x++){
                String key;
                Plan plan;
                Log.i("testings", planSearchList.get(x).getPlanTitle());
                if (planSearchList.get(x).getPlanTitle().trim().contains(charText)){
                    Log.i("testings", "search started");
                    plan = planSearchList.get(x);
                    key = biMap2.get(plan);
                    searchList.put(key, plan);
                    Log.i("solsol","result found:"+searchList.toString());
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
//        adapter.notifyDataSetChanged();
        adapter = new SearchAdapter(searchList);
        mRecyclerView.setAdapter(adapter);
    }

}





