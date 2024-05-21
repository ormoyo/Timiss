package com.ormoyo.timiss.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ormoyo.timiss.R;
import com.ormoyo.timiss.ui.fragments.OutOfTimeFragment;

public class OutOfTimeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.OutOfTimeDialogTheme);
        super.onCreate(savedInstanceState);

        OutOfTimeFragment fragment = new OutOfTimeFragment();
        fragment.show(getSupportFragmentManager(), "out_of_time");
    }
}