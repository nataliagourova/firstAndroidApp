package com.example.helloworldnataliasapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

        ProfileFragment profileFragment =
                ProfileFragment.newInstance(name, dob, occupation, description);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, profileFragment)
                .commit();
    }

}