package com.disruption.popularmovies1.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.disruption.popularmovies1.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.settings_label));
    }
}
