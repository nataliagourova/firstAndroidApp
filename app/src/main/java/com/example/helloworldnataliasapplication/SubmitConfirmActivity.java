package com.example.helloworldnataliasapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class SubmitConfirmActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 321;
    static final int RESULT_BACK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_confirm);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.NAME_KEY);
        String dob = intent.getStringExtra(MainActivity.DOB_KEY);
        String occupation = intent.getStringExtra(MainActivity.OCCUPATION_KEY);
        String description = intent.getStringExtra(MainActivity.DESCRIPTION_KEY);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        ProfileFragment profileFragment =
                ProfileFragment.newInstance(name, dob, occupation, description);
        SettingsFragment settingsFragment = SettingsFragment.newInstance();
        MatchesFragment matchesFragment = new MatchesFragment();

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.profile_tab:
                    fragment = profileFragment;
                    break;
                case R.id.matches_tab:
                    fragment = matchesFragment;
                    break;
                case R.id.settings_tab:
                    fragment = settingsFragment;
                    break;
                default:
                    fragment = profileFragment;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        });

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            setResult(RESULT_BACK);
            finish();
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, profileFragment)
                .commit();

        if (!arePermissionsGranted()) {
            requestPermissions(
                    new String[]{
                            ACCESS_FINE_LOCATION,
                            ACCESS_BACKGROUND_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }

    private boolean arePermissionsGranted() {
        return checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
                || checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (!arePermissionsGranted()) {
            requestPermissions(
                    new String[]{
                            ACCESS_FINE_LOCATION,
                            ACCESS_BACKGROUND_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }
}