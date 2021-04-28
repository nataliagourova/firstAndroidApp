package com.example.helloworldnataliasapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int SUBMIT_CONFIRMATION_ACTIVITY = 1;
    private boolean dobTooEarly = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView errorMessage = findViewById(R.id.error_message);

        Button submitButton = findViewById(R.id.submit_btn);
        submitButton.setEnabled(false);

        EditText nameEdit = findViewById(R.id.name_input);
        EditText emailEdit = findViewById(R.id.email_input);
        EditText userNameEdit = findViewById(R.id.username_input);
        EditText dobEdit = findViewById(R.id.dob_input);
        validateInputs(submitButton, errorMessage, nameEdit, emailEdit, userNameEdit, dobEdit);

        submitButton.setOnClickListener(v -> {
            String name = nameEdit.getText().toString();
            Intent intent = new Intent(getBaseContext(), SubmitConfirmActivity.class);
            intent.putExtra("USERNAME", name);
            startActivityForResult(intent, SUBMIT_CONFIRMATION_ACTIVITY);
        });

        dobEdit.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this);
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                dobEdit.setText(String.format("%d/%d/%d", month, dayOfMonth, year));
                long numberOfYears = getNumberOfYearsSince(year, month, dayOfMonth);

                dobTooEarly = numberOfYears < 18;
                submitButton.setEnabled(!dobTooEarly);
                if (dobTooEarly) {
                    errorMessage.setText(R.string.err_dob_too_early);
                } else {
                    errorMessage.setText("");
                }
            });
            datePickerDialog.show();
        });
    }

    private long getNumberOfYearsSince(int year, int month, int dayOfMonth) {
        LocalDate start = LocalDate.of(year, month, dayOfMonth);
        LocalDate end = LocalDate.now();
        return ChronoUnit.YEARS.between(start, end);
    }

    private void validateInputs(Button submitButton, TextView errorMessage, EditText... inputs) {
        for (EditText input : inputs) {
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Nothing to do
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (dobTooEarly) {
                        // already handle this
                        return;
                    }

                    boolean isSubmitEnabled = true;
                    for (EditText input : inputs) {
                        if (input.getText().toString().length() == 0) {
                            isSubmitEnabled = false;
                            break;
                        }
                    }
                    submitButton.setEnabled(isSubmitEnabled);

                    if (!isSubmitEnabled) {
                        errorMessage.setText(R.string.err_empty_form);
                    } else {
                        errorMessage.setText("");
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SUBMIT_CONFIRMATION_ACTIVITY
                && resultCode == SubmitConfirmActivity.RESULT_BACK) {
            List<TextView> views = Arrays.asList(
                    findViewById(R.id.error_message),
                    findViewById(R.id.name_input),
                    findViewById(R.id.email_input),
                    findViewById(R.id.username_input),
                    findViewById(R.id.dob_input)
            );
            for (TextView view : views) {
                view.setText("");
            }

            TextView errorMessage = findViewById(R.id.error_message);
            errorMessage.setText(R.string.err_empty_form);
            Button submitButton = findViewById(R.id.submit_btn);
            submitButton.setEnabled(false);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}