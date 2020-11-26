package com.together.nosheng.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.together.nosheng.R;

public class BottomBtnActivity  extends AppCompatActivity {

    final static String MY_FRAGMENT_TAG = "my_fragment";
    private Button btn_add;
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = (Fragment) fm.findFragmentById(R.id.plan_fragment);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bottom_btn);

        btn_add = (Button) findViewById(R.id.btn_add_temp);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton();
            }
        });

        //받기
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("some") != null){
                Toast.makeText(getApplicationContext(), "data" + bundle.getString("some"), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void addButton() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_bottom_btn);
        btn_add = new Button(this);
        btn_add.setText("n일차");
        layout.addView(btn_add);
        Log.i("add button", "1");

        FragmentTransaction ft = fm.beginTransaction();
        if(fragment == null){
            ft.add(R.id.plan_fragment, new Fragment(), MY_FRAGMENT_TAG);
            ft.commitNow();
            Log.i("add fragment", "2");
        }

    }
}
