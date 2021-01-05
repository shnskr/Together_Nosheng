package com.together.nosheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.user.User;
import com.together.nosheng.view.SearchPlanDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private List<String> bookmarkIds;
    private Map<String,Plan> userBookmark;
    private Context context;

    public BookmarkAdapter(Map<String, Plan> userBookmark, Context context) {
        bookmarkIds = new ArrayList<>(userBookmark.keySet());
        this.userBookmark = userBookmark;
        this.context = context;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_setting_list_bookmark, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.BookmarkViewHolder holder, int position) {
        Plan plan = userBookmark.get(bookmarkIds.get(position));

        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        holder.content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        holder.content2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        holder.title.setGravity(Gravity.CENTER);

        holder.title.setText(plan.getPlanTitle());
        holder.content.setText(plan.getPlanTheme());
        holder.content2.setText(Integer.toString(plan.getPlanLike().size()));

        holder.setClickListener(position);
    }

    @Override
    public int getItemCount() {
        return bookmarkIds.size();
    }


    public class BookmarkViewHolder extends RecyclerView.ViewHolder {


        private TextView title; //북마크 플랜 제목
        private TextView content;//북마크 플랜 테마
        private TextView content2; //북마크 플랜 좋아요

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.bookmark_title);
            content = itemView.findViewById(R.id.bookmark_content);
            content2 = itemView.findViewById(R.id.bookmark_content2);
        }

        private void setClickListener(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SearchPlanDetailActivity.class);
                    String planId = bookmarkIds.get(position); //DocID 보내주기

                    intent.putExtra("Title", userBookmark.get(planId).getPlanTitle());
                    intent.putExtra("Theme", userBookmark.get(planId).getPlanTheme());
                    intent.putExtra("Like", userBookmark.get(planId).getPlanLike().size());
                    intent.putExtra("planId", planId);

                    context.startActivity(intent);
                }
            });
        }
    }
}
