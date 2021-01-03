package com.together.nosheng.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {

    private Map<String, Boolean> checkLists;
    private List<String> checkKey;

    public CheckListAdapter(Map<String, Boolean> checkLists) {
        this.checkLists = checkLists;
        this.checkKey = new ArrayList<>(checkLists.keySet());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checklist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String key = checkKey.get(position);

        if (key.equals(" ")) {
            holder.etName.setText("");
        } else {
            holder.etName.setText(key);
        }

        if (checkLists.get(key)) {
            holder.cbCheck.setChecked(true);
        }

        holder.etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String newKey = holder.etName.getText().toString();

                    checkKey.add(position, newKey);
                    checkKey.remove(position+1);

                    checkLists.put(newKey, holder.cbCheck.isChecked());
                    if (key.equals(" ")) {
                        checkLists.remove(" ");
                    } else if (!key.equals(newKey)){
                        checkLists.remove(key);
                    }

                    Log.i("daldal", checkLists.toString());
                    Log.i("daldal", checkKey.toString());
                }
            }
        });

        holder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.etName.clearFocus();
                String newKey = holder.etName.getText().toString();

                if (newKey.equals("")) {
                    checkLists.put(" ", isChecked);
                } else {
                    checkLists.put(newKey, isChecked);
                }

                Log.i("daldal", checkLists.toString());
                Log.i("daldal", checkKey.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return checkKey.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private EditText etName;
        private CheckBox cbCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            etName = itemView.findViewById(R.id.et_name);
            cbCheck = itemView.findViewById(R.id.cb_check);
        }
    }
}
