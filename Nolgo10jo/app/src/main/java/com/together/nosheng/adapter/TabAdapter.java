package com.together.nosheng.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.together.nosheng.view.BoardFragmentActivity;
import com.together.nosheng.view.BudgetFragmentActivity;
import com.together.nosheng.view.MemberFragmentActivity;
import com.together.nosheng.view.PlanFragmentActivity;
import com.together.nosheng.view.TripInfoFragmentActivity;


public class TabAdapter extends FragmentStatePagerAdapter {

    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 :
                return new MemberFragmentActivity();
            case 1 :
                return new BudgetFragmentActivity();
            case 2 :
                return new BoardFragmentActivity();
            case 3 :
                return new PlanFragmentActivity();
            case 4 :
                return new TripInfoFragmentActivity();
            default :
                return new MemberFragmentActivity();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0 :
                title = "인원";
                break;
            case 1 :
                title = "예산";
                break;
            case 2 :
                title = "알림장";
                break;
            case 3 :
                title = "일정";
                break;
            case 4 :
                title = "정보";
                break;
        }
        return title;
    }
}
