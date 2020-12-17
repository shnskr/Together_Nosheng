package com.together.nosheng.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.together.nosheng.adapter.TabAdapter;
import com.together.nosheng.databinding.LayoutTabBinding;
import com.together.nosheng.viewmodel.ProjectViewModel;

public class TabActivity extends AppCompatActivity {

    private ProjectViewModel projectViewModel;

    private LayoutTabBinding tabBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabBinding = LayoutTabBinding.inflate(getLayoutInflater());
        setContentView(tabBinding.getRoot());

        String projectId = getIntent().getStringExtra("projectId");

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        projectViewModel.setCurrentProject(projectId);

        TabAdapter viewPagerAdapter = new TabAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabBinding.viewPager.setAdapter(viewPagerAdapter);

        tabBinding.layTab.setupWithViewPager(tabBinding.viewPager);
        tabBinding.layTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("daldal", "선택");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("daldal", "선택취소");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("daldal", "재선택");
            }
        });
    }

    //keyboard controller
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


}
