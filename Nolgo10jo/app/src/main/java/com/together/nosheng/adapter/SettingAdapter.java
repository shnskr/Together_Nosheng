package com.together.nosheng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;

import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.Holder>{
    private Context context;
//    private List<User> List = new ArrayList<>();
    private ArrayList<String> List;

    public SettingAdapter(Context context, ArrayList<String> List) {
        this.context = context;
        this.List = List;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_setting_list, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.title.setText(List.get(position));
        holder.content.setText(List.get(position));
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView content;

        public Holder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.list_title);
            content = (TextView) view.findViewById(R.id.list_content);
        }
    }

}