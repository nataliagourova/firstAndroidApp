package com.example.helloworldnataliasapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SubmitConfirmActivity extends AppCompatActivity {

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
        MatchesFragment matchesFragment = MatchesFragment.newInstance();

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


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, profileFragment)
                .commit();
    }

}