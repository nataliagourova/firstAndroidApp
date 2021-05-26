package com.example.helloworldnataliasapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworldnataliasapplication.entity.Settings;
import com.example.helloworldnataliasapplication.viewmodels.SettingsViewModel;

public class SettingsFragment extends Fragment {
    public TextView email;
    public TextView reminderTime;
    public TextView searchRange;
    public TextView gender;
    public TextView privateAccount;
    public TextView ageRangeStart;
    public TextView ageRangeEnd;
    public TextView userPhotoUrl;
    private SettingsViewModel settingsViewModel;
    private Settings settings;

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
        searchRange.setText(String.format("%s miles distance", this.settings.getSearchRange()));
        gender.setText(String.format("female: ", this.settings.getGender()));
        privateAccount.setText(this.settings.getPrivateAccount().toString());
        ageRangeStart.setText(String.format("matches preference from %s years old", this.settings.getAgeRangeStart()));
        ageRangeEnd.setText(String.format("to %s years old", this.settings.getAgeRangeEnd()));
    }
}
