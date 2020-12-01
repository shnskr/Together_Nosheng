package com.together.nosheng.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.together.nosheng.databinding.LayoutBottomBtnBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BottomBtnActivity  extends AppCompatActivity {

    private LayoutBottomBtnBinding binding;

    private GoogleMap mMap;

    final static String MY_FRAGMENT_TAG = "my_fragment";
    private Button btn_add;
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = (Fragment) fm.findFragmentById(R.id.plan_fragment);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutBottomBtnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().add(R.id.plan_fragment, new GoogleActivity()).commit();

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
