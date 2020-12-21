package com.together.nosheng.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;
import com.together.nosheng.databinding.LayoutSearchItemDetailBinding;
import com.together.nosheng.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder>{
    private List<User> friendList;
    private SettingRecyclerViewClickListener listener; //이게 필요한지는 모르겠으니까 일단 넣어두고 보겠어
    private Context context;

    public SettingAdapter(List<User> friendList, SettingRecyclerViewClickListener listener){
        this.friendList = friendList;
        this.listener = listener;
    }

    public SettingAdapter(List<User> friendList, SettingRecyclerViewClickListener listener, Context context){
        this.friendList =friendList;
        this.listener =listener;
        this.context =context;
    }


    @NonNull
    @Override
    public SettingAdapter.SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_setting_list, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdapter.SettingViewHolder holder, int position) {
        User user = friendList.get(position);
        Log.i("adapter---", user.toString());

        holder.title.setText(user.getNickName());
        holder.content.setText(user.geteMail());
        holder.content.setText(user.getRegDate().toString());

    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    //클릭 리스너 와 뷰 홀더 관련 코딩
    public interface SettingRecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class SettingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private SettingRecyclerViewClickListener listener = new SettingRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                listener.onClick(itemView, getAdapterPosition());
            }
        };

        private TextView title; //친구 닉네임?
        private TextView content; // 부가 내용
        private TextView content2;//부가 내용 2

        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.list_title);
            content = itemView.findViewById(R.id.list_content);
            content2 = itemView.findViewById(R.id.list_content2);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());

        }

    }
}