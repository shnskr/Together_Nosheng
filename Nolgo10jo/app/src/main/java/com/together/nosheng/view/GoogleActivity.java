package com.together.nosheng.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;
import com.together.nosheng.R;
import com.together.nosheng.databinding.LayoutGoogleBinding;
import com.together.nosheng.model.pin.Pin;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.viewmodel.ProjectViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GoogleActivity extends Fragment implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private LayoutGoogleBinding binding;
    private ProjectViewModel projectViewModel;

    private GoogleMap mMap;

    private int page;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutGoogleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String projectId = requireActivity().getIntent().getStringExtra("projectId");

        getParentFragmentManager().setFragmentResultListener("result", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                page = result.getInt("position");
                Log.i("daldal2", page + "");
            }
        });

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {

            }
        });

        // Google Map 초기화
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        // onMapReady() 호출
        mapFragment.getMapAsync(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                // TODO: Get info about the selected place.

                MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions
                        .position(place.getLatLng())
                        .title(place.getName())
                        .snippet(place.getAddress());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
                mMap.addMarker(markerOptions);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(requireContext());
                        dlg.setTitle("Pin 추가");
                        dlg.setMessage(place.getName() + "\n" + place.getAddress());
                        dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Pin pin = new Pin();
                                pin.setLatitude(place.getLatLng().latitude);
                                pin.setLongitude(place.getLatLng().longitude);
                                pin.setAddress(place.getAddress());
                                pin.setPinName(place.getName());

                                Log.i("pin정보", pin.toString());
                            }
                        });
                        dlg.setNegativeButton("지우기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                marker.remove();
                            }
                        });
                        dlg.show();
                        return false;
                    }
                });
            }

            @Override
            public void onError(@NotNull Status status) {
                // TODO: Handle the error.
                Log.i("daldal", "An error occurred: " + status);
            }
        });

        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("daldal", "지도 load 완료");
        mMap = googleMap;

        updateUI();

    }

    private boolean isLocationPermissionGranted() {
        SharedPreferences preference = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true);

        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 거부만 한 경우 사용자에게 왜 필요한지 이유를 설명
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "위치 권한이 필요합니다.", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("권한승인", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                });
                snackbar.show();
            } else {
                // 처음 물어봤는지 여부를 저장
                if (isFirstCheck) {
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply();
                    // 권한 요청
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                } else {
                    // 사용자가 권한을 거부하면서 다시 묻지 않음 옵션을 선택한 경우
                    // requestPermission을 요청해도 창이 나타나지 않기 때문에 설정창으로 이동
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "위치 권한이 필요합니다.", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", "com.together.nosheng", null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                    snackbar.show();
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private void updateUI() {
        if (mMap == null) {
            return;
        }
        try {
            mMap.getUiSettings().setZoomControlsEnabled(true);

            if (isLocationPermissionGranted()) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
