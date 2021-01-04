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

    public SettingAdapter(List<User> friendList){
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_setting_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdapter.SettingViewHolder holder, int position) {
        User user = friendList.get(position);

        holder.nickName.setText(user.getNickName());
        holder.email.setText(user.geteMail());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public class SettingViewHolder extends RecyclerView.ViewHolder {

        private TextView nickName; //친구 닉네임?
        private TextView email; // 부가 내용

        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);

            nickName = itemView.findViewById(R.id.tv_nickname);
            email = itemView.findViewById(R.id.tv_email);
        }

    }
}