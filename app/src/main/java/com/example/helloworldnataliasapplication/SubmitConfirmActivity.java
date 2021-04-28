package com.example.helloworldnataliasapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubmitConfirmActivity extends AppCompatActivity {

    static final int RESULT_BACK = 1;

    TextView dobLabel;
    TextView nameLabel;
    TextView occupationLabel;
    TextView descriptionLabel;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_confirm);
        findViews();

        Intent intent = getIntent();
        nameLabel.setText(intent.getStringExtra(MainActivity.NAME_KEY));
        dobLabel.setText(intent.getStringExtra(MainActivity.DOB_KEY));
        occupationLabel.setText(intent.getStringExtra(MainActivity.OCCUPATION_KEY));
        descriptionLabel.setText(intent.getStringExtra(MainActivity.DESCRIPTION_KEY));

        backButton.setOnClickListener(v -> {
            setResult(RESULT_BACK);
            finish();
        });
    }

    private void findViews() {
        dobLabel = findViewById(R.id.dob_profile_label);
        nameLabel = findViewById(R.id.name_profile_label);
        descriptionLabel = findViewById(R.id.description_profile_label);
        occupationLabel = findViewById(R.id.occupation_profile_label);
        backButton = findViewById(R.id.back_button);
    }
}