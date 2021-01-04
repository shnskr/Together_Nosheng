package com.together.nosheng.adapter;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;
import com.together.nosheng.model.project.CheckList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {

    private List<String> item;
    private List<Boolean> check;

    public CheckListAdapter(CheckList checkList) {
        this.item = checkList.getItem();
        this.check = checkList.getCheck();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checklist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.etName.setText(item.get(position));
        holder.cbCheck.setChecked(check.get(position));

        holder.etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                item.set(position, holder.etName.getText().toString());
            }
        });

        holder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check.set(position, isChecked);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.remove(position);
                check.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public List<String> getItem() {
        return item;
    }

    public List<Boolean> getCheck() {
        return check;
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private EditText etName;
        private CheckBox cbCheck;
        private ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            etName = itemView.findViewById(R.id.et_name);
            cbCheck = itemView.findViewById(R.id.cb_check);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
