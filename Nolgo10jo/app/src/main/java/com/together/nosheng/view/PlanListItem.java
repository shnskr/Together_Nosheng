package com.together.nosheng.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.together.nosheng.R;
import com.together.nosheng.model.pin.Pin;

public class PlanListItem extends LinearLayout {

    private TextView tvPosition, tvName, tvAddress;
    private ImageButton btnDelete;

    public PlanListItem(Context context) {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_plan_list,this,true);

        tvPosition = findViewById(R.id.tv_position);
        tvName = findViewById(R.id.tv_name);
        tvAddress = findViewById(R.id.tv_address);
        btnDelete = findViewById(R.id.btn_delete);
    }

    public void setText(int position, Pin pin) {
        tvPosition.setText(String.valueOf(position + 1));
        tvName.setText(pin.getPinName());
        tvAddress.setText(pin.getAddress());
    }

    public ImageButton getBtnDelete() {
        return btnDelete;
    }
}
