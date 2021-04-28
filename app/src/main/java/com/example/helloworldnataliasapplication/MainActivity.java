package com.example.helloworldnataliasapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submitButton = findViewById(R.id.submit_btn);
        submitButton.setEnabled(false);

        EditText nameEdit = findViewById(R.id.name_input);
        EditText emailEdit = findViewById(R.id.email_input);
        EditText userNameEdit = findViewById(R.id.username_input);
        EditText dobEdit = findViewById(R.id.dob_input);
        validateInputs(submitButton, nameEdit, emailEdit, userNameEdit, dobEdit);

        submitButton.setOnClickListener(v -> {
            String name = nameEdit.getText().toString();
            Intent intent = new Intent(getBaseContext(), SubmitConfirmActivity.class);
            intent.putExtra("USERNAME", name);
            startActivity(intent);
        });

        dobEdit.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this);
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                dobEdit.setText(String.format("%d/%d/%d", month, dayOfMonth, year));
            });
            datePickerDialog.show();
        });
    }

    private void validateInputs(Button submitButton, EditText ... inputs) {
        for(EditText input : inputs) {
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Nothing to do
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    boolean isSubmitEnabled = true;
                    for(EditText input : inputs) {
                        if (input.getText().toString().length() == 0) {
                            isSubmitEnabled = false;
                            break;
                        }
                    }
                    submitButton.setEnabled(isSubmitEnabled);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Nothing to do
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
}