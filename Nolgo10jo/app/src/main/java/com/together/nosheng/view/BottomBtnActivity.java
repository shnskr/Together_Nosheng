package com.together.nosheng.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.together.nosheng.R;


public class BottomBtnActivity  extends AppCompatActivity  implements View.OnClickListener {

    private FragmentManager fm;
    private FragmentTransaction ft;

    private com.together.nosheng.databinding.LayoutBottomBtnBinding bottomBtnBinding;

    private TripInfoFragmentActivity tripInfoFragmentActivity;
    private MemeberFragmentActivity memeberFragmentActivity;
    private BudgetFragmentActivity budgetFragmentActivity;
    private BoardFragmentActivity boardFragmentActivity;


    private GoogleMap mMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomBtnBinding = com.together.nosheng.databinding.LayoutBottomBtnBinding.inflate(getLayoutInflater());
        setContentView(bottomBtnBinding.getRoot());

        getSupportFragmentManager().beginTransaction().add(R.id.plan_fragment_container, new GoogleActivity()).commit();

        // 구글 place
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
////        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
////
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(@NotNull Place place) {
//                // TODO: Get info about the selected place.
//                Log.i("hi", "Place: " + place.getName() + ", " + place.getId());
//            }
//
//            @Override
//            public void onError(@NotNull Status status) {
//                // TODO: Handle the error.
//                Log.i("hi", "An error occurred: " + status);
//            }
//        });


        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.plan_fragment_container, new TripInfoFragmentActivity());
        ft.addToBackStack(null);
        ft.commit();

        bottomBtnBinding.btnMember.setOnClickListener(this);
        bottomBtnBinding.btnBudget.setOnClickListener(this);
        bottomBtnBinding.btnInfo.setOnClickListener(this);
        bottomBtnBinding.btnBoard.setOnClickListener(this);

        addButton(99);

    }

    @Override
    public void onClick(View view) {
        ft = fm.beginTransaction();

        switch (view.getId()) {
            case R.id.btn_info:
                tripInfoFragmentActivity = new TripInfoFragmentActivity();
                ft.replace(R.id.plan_fragment_container, tripInfoFragmentActivity);
                ft.commit();
                break;
            case R.id.btn_member:
                memeberFragmentActivity = new MemeberFragmentActivity();
                ft.replace(R.id.plan_fragment_container, memeberFragmentActivity).commit();
                break;
            case R.id.btn_budget:
                budgetFragmentActivity = new BudgetFragmentActivity();
                ft.replace(R.id.plan_fragment_container, budgetFragmentActivity).commit();
                break;
            case R.id.btn_board:
                boardFragmentActivity = new BoardFragmentActivity();
                ft.replace(R.id.plan_fragment_container, boardFragmentActivity).commit();
                break;
        }
    }

    public void addButton(int count){

        final String MY_FRAGMENT_TAG = "my_fragment";
        Fragment fragment = (Fragment) fm.findFragmentById(R.id.plan_fragment_container);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_bottom_btn);
        Button btn = bottomBtnBinding.btnBoard;

        for (int i = 0; count >= i; i++){
            btn = new Button(this);
            btn.setText((i+1)+"일차");
            layout.addView(btn);
            Log.i("add Button", "0");

            ft = fm.beginTransaction();
            if(fragment == null){
                ft.add(R.id.plan_fragment_container, new Fragment(), MY_FRAGMENT_TAG);
                ft.commitNow();
                Log.i("add Fragment", "2");
            }
        }
    }
}