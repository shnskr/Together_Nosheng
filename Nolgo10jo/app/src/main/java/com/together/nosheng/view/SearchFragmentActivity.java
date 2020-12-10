package com.together.nosheng.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityFragmentSearchBinding;
import com.together.nosheng.viewmodel.PlanViewModel;

public class SearchFragmentActivity extends Fragment {
    private ActivityFragmentSearchBinding searchBinding;
    private PlanViewModel planViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        searchBinding = ActivityFragmentSearchBinding.inflate(inflater, container, false);
        View view = searchBinding.getRoot();

        //무슨 버튼을 누르던 일단 searchplan activity 로 가는데 입력한 버튼값으로 필터값 들어가는 형식
        searchBinding.btnSearchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchPlanActivity.class);
                startActivity(intent);
            }
        });

        searchBinding.btnSearchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchPlanActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
