package com.example.helloworldnataliasapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubmitConfirmActivity extends AppCompatActivity {

    static final int RESULT_BACK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_confirm);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.NAME_KEY);
        String welcomeText = getString(R.string.thanks_signup, name);
        TextView welcomeLabel = findViewById(R.id.welcome_label);
        welcomeLabel.setText(welcomeText);
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            setResult(RESULT_BACK);
            finish();
        });
    }
}