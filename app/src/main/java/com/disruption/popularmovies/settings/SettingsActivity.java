package com.disruption.popularmovies.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.disruption.popularmovies.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.settings_label));
    }
}
