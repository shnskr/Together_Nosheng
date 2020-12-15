package com.together.nosheng.adapter;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;
import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.view.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder> {
    private Map<String, Plan> plans;
    private List<Plan> planList;
    private List<String> planId;


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    private OnItemClickListener mListener = null;
    public void setOnItemClickListener (OnItemClickListener listener){
        this.mListener = listener;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView plan_title;
        protected TextView plan_theme;
        protected TextView plan_like;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plan_title = (TextView) itemView.findViewById(R.id.plan_title);
            this.plan_theme=(TextView) itemView.findViewById(R.id.plan_theme);
            this.plan_like = (TextView) itemView.findViewById(R.id.plan_like);

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

    public SearchAdapter(Map<String, Plan> plans) {
        this.plans = plans;
        planId = new ArrayList<>(plans.keySet());
        }




    @NonNull
    @Override
    public SearchAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_list_item, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.CustomViewHolder holder, int position) {


        holder.plan_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        holder.plan_theme.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        holder.plan_like.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);

        holder.plan_title.setGravity(Gravity.CENTER);
        holder.plan_theme.setGravity(Gravity.CENTER);
        holder.plan_like.setGravity(Gravity.CENTER);

        holder.plan_title.setText(plans.get(planId.get(position)).getPlanTitle());
        holder.plan_theme.setText(plans.get(planId.get(position)).getPlanTheme());
        holder.plan_like.setText(Integer.toString(plans.get(planId.get(position)).getPlanLike()));
    }

    @Override
    public int getItemCount() {
        return (null != plans ? plans.size() :0);
    }


}
