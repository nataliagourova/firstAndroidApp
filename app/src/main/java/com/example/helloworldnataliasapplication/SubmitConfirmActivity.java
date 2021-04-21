package com.example.helloworldnataliasapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SubmitConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_confirm);
        Intent intent = getIntent();
        String name = intent.getStringExtra("USERNAME");
        String welcomeText = getString(R.string.thanks_signup, name);
        TextView welcomeLabel = findViewById(R.id.welcome_label);
        welcomeLabel.setText(welcomeText);
    }
}