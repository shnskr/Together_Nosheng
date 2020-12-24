package com.together.nosheng.adapter;

import android.content.Context;
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

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    private List<Plan> bookmarkList;
    private Context context;
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    private BookmarkAdapter.OnItemClickListener mListener = null;
    public void setOnItemClickListener (BookmarkAdapter.OnItemClickListener listener){
        this.mListener = listener;
    }



    public BookmarkAdapter(List<Plan> bookmarkList, Context context) {
        this.bookmarkList = bookmarkList;
        this.context = context;
    }


    @NonNull
    @Override
    public BookmarkAdapter.BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkViewHolder((LayoutInflater.from(parent.getContext())).inflate(R.layout.layout_setting_list_bookmark, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.BookmarkViewHolder holder, int position) {
        Plan plan = bookmarkList.get(position);

        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        holder.content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        holder.content2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        holder.title.setGravity(Gravity.CENTER);

        holder.title.setText(plan.getPlanTitle());
        holder.content.setText(plan.getPlanTheme());
        holder.content2.setText(Integer.toString(plan.getPlanLike()));

    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener !=null){
                            mListener.onItemClick(v,pos);
                        }
                    }
                }
            });
        }
    }
}