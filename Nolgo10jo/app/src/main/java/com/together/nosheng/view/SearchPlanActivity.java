package com.together.nosheng.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.adapter.SearchAdapter;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.viewmodel.PlanViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPlanActivity  extends AppCompatActivity {
    private SearchAdapter adapter;
    private ListView listview;

    private Map<String, Plan> searchList;//검색용 리스트
    private Map<String, Plan> planList;
    private PlanViewModel planViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        planViewModel = new ViewModelProvider(this).get(PlanViewModel.class);
        planViewModel.setPlanRepository();

        listview = (ListView) findViewById(R.id.search_listView);

        planViewModel.getPlans().observe(this, new Observer<Map<String, Plan>>() {
            @Override
            public void onChanged(Map<String, Plan> stringPlanMap) {
                planList = stringPlanMap;
                adapter = new SearchAdapter(planList);
                listview.setAdapter(adapter);
                searchList = new HashMap<>(planList); //값 복사해주기

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() { //검섹칭을 끄면 다 모든 값들을 보여줌
                planList.clear();
                planList.putAll(searchList);
                adapter = new SearchAdapter(planList);
                listview.setAdapter(adapter);
                return false;
            }
        });

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("검색 ㄱㄱ");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                search(query); 아직 서치 부분이 잘 작동하지 않아서 주석 처리 해놓겠습니다! 
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("Testing21", "search complete: " + newText);
                Toast.makeText(getApplicationContext(), "입력중입니다", Toast.LENGTH_SHORT).show();
                if (newText.length() == 0) {
                    planList.clear();
                    planList.putAll(searchList);
                    adapter = new SearchAdapter(planList);
                    listview.setAdapter(adapter);
                }
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.i("testing", "일단 실행");
        switch (item.getItemId()) {
            case R.id.search_title:
                //sorting method
                Log.i("testing", "타이틀 눌러짐 ");
                return true;
            case R.id.search_theme:
                //sorting method
                Log.i("testing", "테마 눌러짐");
                return true;
            default:
                return true;

        }

    }
    @Override
    public void onBackPressed() {
        Log.i("testing123", "adapter reset");
        adapter = new SearchAdapter(searchList);
        listview.setAdapter(adapter);
    }

    public void search(String charText) {

        planList.clear();
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.


        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            planList.putAll(searchList);
        }
        // 문자 입력을 할때..
        else {
            Log.i("확인", searchList.get(charText).toString());
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < searchList.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (searchList.get(i).getPlanTitle().toLowerCase().contains(charText)) {
                    Log.i("searching", "search complete and found : " + searchList.get(i).toString());
                    // 검색된 데이터를 리스트에 추가한다.
                    planList.put(Integer.toString(planList.size()),searchList.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
}


