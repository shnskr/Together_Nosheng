package com.together.nosheng.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.together.nosheng.R;
import com.together.nosheng.model.pin.Pin;
import com.together.nosheng.viewmodel.PlanViewModel;

import java.util.List;

public class PinRecyclerAdapter extends RecyclerView.Adapter<PinRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Pin> pins;
    private String planId;
    private PlanViewModel planViewModel;

    public PinRecyclerAdapter(Context context, List<Pin> pins, String planId, PlanViewModel planViewModel) {
        this.context = context;
        this.pins = pins;
        this.planId = planId;
        this.planViewModel = planViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pin pin = pins.get(position);

        holder.tvPosition.setText(String.valueOf(position + 1));
        holder.tvName.setText(pin.getPinName());
        holder.tvAddress.setText(pin.getAddress());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle("Pin 삭제");
                dlg.setMessage(pin.getPinName() + "\n" + pin.getAddress());
                dlg.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pins.remove(position);

                        planViewModel.updatePins(planId, pins);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pins.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPosition, tvName, tvAddress;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPosition = itemView.findViewById(R.id.tv_position);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
