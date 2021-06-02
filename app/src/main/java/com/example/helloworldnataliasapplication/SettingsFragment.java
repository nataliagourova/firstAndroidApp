package com.example.helloworldnataliasapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworldnataliasapplication.entity.Settings;
import com.example.helloworldnataliasapplication.viewmodels.SettingsViewModel;

import java.util.Arrays;
import java.util.List;

public class SettingsFragment extends Fragment {
    public TextView email;
    public EditText reminderTime;
    public EditText searchRange;
    public EditText gender;
    public EditText privateAccount;
    public EditText ageRangeStart;
    public EditText ageRangeEnd;
    public Button saveSettingsButton;
    private SettingsViewModel settingsViewModel;
    private Settings settings;
    List<EditText> inputs;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_settings, container, false);

        findViews(fragmentView);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        settingsViewModel
                .loadSettings(this.getContext())
                .observe(getViewLifecycleOwner(), this::onSettingsLoaded);

        saveSettingsButton.setOnClickListener(v -> saveSettingsFromInputs());

        return fragmentView;
    }

    private void findViews(View fragmentView) {
        email = fragmentView.findViewById(R.id.email);
        reminderTime = fragmentView.findViewById(R.id.reminderTime);
        searchRange = fragmentView.findViewById(R.id.searchRange);
        gender = fragmentView.findViewById(R.id.gender);
        privateAccount = fragmentView.findViewById(R.id.privateAccount);
        ageRangeStart = fragmentView.findViewById(R.id.ageRangeStart);
        ageRangeEnd = fragmentView.findViewById(R.id.ageRangeEnd);
        saveSettingsButton = fragmentView.findViewById(R.id.save_settings_btn);

        inputs = Arrays.asList(
                reminderTime,
                searchRange,
                gender,
                privateAccount,
                ageRangeStart,
                ageRangeEnd);
    }

    private void onSettingsLoaded(Settings settings) {
        if (settings == null) {
            settingsViewModel.saveDefaultSettings(getContext());
            return;
        }

        this.settings = settings;
        updateViews();
    }

    private void updateViews() {
        email.setText(this.settings.getSettingsKey());
        reminderTime.setText(this.settings.getReminderTime());
        searchRange.setText(this.settings.getSearchRange().toString());
        gender.setText(this.settings.getGender());
        privateAccount.setText(this.settings.getPrivateAccount().toString());
        ageRangeStart.setText(this.settings.getAgeRangeStart().toString());
        ageRangeEnd.setText(this.settings.getAgeRangeEnd().toString());
    }

    private void saveSettingsFromInputs() {
        if (settings == null) {
            return;
        }
        Settings newSettings = new Settings();
        newSettings.setSettingsKey(settings.settingsKey);
        newSettings.setSearchRange(Integer.parseInt(searchRange.getText().toString()));
        newSettings.setAgeRangeEnd(Integer.parseInt(ageRangeEnd.getText().toString()));
        newSettings.setAgeRangeStart(Integer.parseInt(ageRangeStart.getText().toString()));
        newSettings.setReminderTime(reminderTime.getText().toString());
        newSettings.setGender(gender.getText().toString());
        newSettings.setPrivateAccount(Boolean.parseBoolean(privateAccount.getText().toString()));
        settingsViewModel.updateSettings(getContext(), newSettings);
    }
}
