package com.together.nosheng.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.together.nosheng.model.pin.Pin;
import com.together.nosheng.view.PlanListItem;

import java.util.List;

public class PinListAdapter extends BaseAdapter {

    private List<Pin> pins;

    public PinListAdapter(List<Pin> pins) {
        this.pins = pins;
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

        planListItem.setText(position, (Pin) getItem(position));

        return planListItem;
    }
}
