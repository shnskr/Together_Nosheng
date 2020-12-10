package com.together.nosheng.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.together.nosheng.R;

public class SearchView extends LinearLayout {

    TextView plan_title, plan_theme, plan_like;

    public SearchView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_search_list_item, this, true);
        plan_title = findViewById(R.id.plan_title);
        plan_theme = findViewById(R.id.plan_theme);
        plan_like = findViewById(R.id.plan_like);
    }

    public void setPlanTitle(String tt) {
        plan_title.setText(tt);
    }

    public void setPlanTheme(String tt) {
        plan_theme.setText(tt);
    }

    public void setPlanLike(int tt) {
        plan_like.setText(Integer.toString(tt));
    }

}
