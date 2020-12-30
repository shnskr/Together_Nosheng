package com.together.nosheng.adapter;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AlertDialog;

import com.together.nosheng.model.pin.Pin;
import com.together.nosheng.view.PlanListItem;
import com.together.nosheng.viewmodel.PlanViewModel;

import java.util.List;

public class PinListAdapter extends BaseAdapter {

    private List<Pin> pins;
    private String planId;
    private PlanViewModel planViewModel;

    public PinListAdapter(List<Pin> pins, String planId, PlanViewModel planViewModel) {
        this.pins = pins;
        this.planId = planId;
        this.planViewModel = planViewModel;
    }

    @Override
    public int getCount() {
        return (pins != null) ? pins.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return pins.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlanListItem planListItem = new PlanListItem(parent.getContext());

        Pin pin = (Pin) getItem(position);

        planListItem.setText(position, pin);

        planListItem.getBtnDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(parent.getContext());
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

        return planListItem;
    }
}
