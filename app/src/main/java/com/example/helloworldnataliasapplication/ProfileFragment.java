package com.example.helloworldnataliasapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    TextView dobLabel;
    TextView nameLabel;
    TextView occupationLabel;
    TextView descriptionLabel;
    Button backButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(
            String name,
            String dob,
            String occupation,
            String description) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.NAME_KEY, name);
        args.putString(MainActivity.DOB_KEY, dob);
        args.putString(MainActivity.OCCUPATION_KEY, occupation);
        args.putString(MainActivity.DESCRIPTION_KEY, description);
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
        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(fragmentView);
        nameLabel.setText(getArguments().getString(MainActivity.NAME_KEY));
        dobLabel.setText(getArguments().getString(MainActivity.DOB_KEY));
        occupationLabel.setText(getArguments().getString(MainActivity.OCCUPATION_KEY));
        descriptionLabel.setText(getArguments().getString(MainActivity.DESCRIPTION_KEY));
        return fragmentView;
    }

    private void findViews(View fragmentView) {
        dobLabel = fragmentView.findViewById(R.id.dob_profile_label);
        nameLabel = fragmentView.findViewById(R.id.name_profile_label);
        descriptionLabel = fragmentView.findViewById(R.id.description_profile_label);
        occupationLabel = fragmentView.findViewById(R.id.occupation_profile_label);
        backButton = fragmentView.findViewById(R.id.back_button);
    }
}