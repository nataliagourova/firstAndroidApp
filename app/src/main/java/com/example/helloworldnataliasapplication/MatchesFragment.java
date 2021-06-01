//package com.example.helloworldnataliasapplication;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//public class MatchesFragment extends Fragment {
//    public MatchesFragment() {
//        // Required empty public constructor
//    }
//
//    public static MatchesFragment newInstance() {
//        MatchesFragment fragment = new MatchesFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_matches, container, false);
//    }
//}
//code above is from previous assignment

package com.example.helloworldnataliasapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworldnataliasapplication.viewmodels.FirebaseMatchesViewModel;

import java.util.function.Consumer;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MatchesFragment extends Fragment {
    private static final long MIN_EVERY_SECOND = 1000;
    private static final float NO_MIN_DISTANCE = 0.0f;
    private static final double MATCH_RADIUS_10_MI = 10.0;

    LocationManager locationManager;
    MatchesCardRecyclerViewAdapter adapter;
    LocationListener locationListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        locationListener = this::onLocationUpdated;
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        View view = inflater.inflate(R.layout.matches_grid_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        adapter = new MatchesCardRecyclerViewAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new MatchItemDecoration(largePadding, smallPadding));


        FirebaseMatchesViewModel viewModel = new FirebaseMatchesViewModel();
        adapter.setMatchLikedListener(
                new Consumer<MatchEntry>() {
                    @Override
                    public void accept(MatchEntry match) {
                        viewModel.saveMatchLike(match);
                    }
                });
        viewModel.getMatches(adapter::setMatches);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isLocationEnabled()) {
            return;
        }

        if (!arePermissionsGranted()) {
            Toast.makeText(
                    getActivity(),
                    R.string.location_permissions_not_granted_message,
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        locationManager
                .requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_EVERY_SECOND,
                        NO_MIN_DISTANCE,
                        locationListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private boolean isLocationEnabled() {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        } else {
            showAlert();
            return false;
        }
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.enable_location_dialog_title)
                .setCancelable(false)
                .setMessage(getString(R.string.enable_location_services_message))
                .setPositiveButton(R.string.location_settings_btn, (paramDialogInterface, paramInt) -> {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                });
        dialog.show();
    }

    private boolean arePermissionsGranted() {
        return getActivity().checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
                || getActivity().checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED;
    }

    private void onLocationUpdated(Location location) {
        adapter.filterMatches(location, MATCH_RADIUS_10_MI);
        Toast.makeText(
                getActivity(),
                "Location: " + location.getLongitude() + " longitude, " + location.getLatitude() + "latitude",
                Toast.LENGTH_SHORT)
                .show();

    }
}