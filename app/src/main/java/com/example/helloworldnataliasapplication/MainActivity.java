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

    static final String NAME_KEY = "NAME";
    static final String USERNAME_KEY = "USERNAME";
    static final String DOB_KEY = "DOB";
    static final String OCCUPATION_KEY = "OCCUPATION";
    static final String DESCRIPTION_KEY = "DESCRIPTION";
    static final String EMAIL_KEY = "EMAIL";

    private boolean dobTooEarly = true;

    TextView errorMessage;
    Button submitButton;
    EditText nameEdit;
    EditText emailEdit;
    EditText userNameEdit;
    EditText dobEdit;
    EditText descriptionEdit;
    EditText occupationEdit;

    List<EditText> allFormFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        submitButton.setEnabled(false);

        validateEmptyInputs();

        submitButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), SubmitConfirmActivity.class);
            intent.putExtra(USERNAME_KEY, userNameEdit.getText().toString());
            intent.putExtra(NAME_KEY, nameEdit.getText().toString());
            intent.putExtra(DESCRIPTION_KEY, descriptionEdit.getText().toString());
            intent.putExtra(OCCUPATION_KEY, occupationEdit.getText().toString());
            intent.putExtra(EMAIL_KEY, emailEdit.getText().toString());
            intent.putExtra(DOB_KEY, dobEdit.getText().toString());

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

    private void findViews() {
        errorMessage = findViewById(R.id.error_message);
        submitButton = findViewById(R.id.submit_btn);
        nameEdit = findViewById(R.id.name_input);
        emailEdit = findViewById(R.id.email_input);
        userNameEdit = findViewById(R.id.username_input);
        dobEdit = findViewById(R.id.dob_input);
        occupationEdit = findViewById(R.id.occupation_edit);
        descriptionEdit = findViewById(R.id.description_edit);

        allFormFields = Arrays.asList(
                nameEdit,
                emailEdit,
                userNameEdit,
                dobEdit,
                occupationEdit,
                descriptionEdit);
    }

    private long getNumberOfYearsSince(int year, int month, int dayOfMonth) {
        LocalDate start = LocalDate.of(year, month, dayOfMonth);
        LocalDate end = LocalDate.now();
        return ChronoUnit.YEARS.between(start, end);
    }

    private void validateEmptyInputs() {
        for (EditText input : allFormFields) {
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
                    for (EditText input : allFormFields) {
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
            for (TextView view : allFormFields) {
                view.setText("");
            }

            errorMessage.setText(R.string.err_empty_form);
            submitButton.setEnabled(false);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}