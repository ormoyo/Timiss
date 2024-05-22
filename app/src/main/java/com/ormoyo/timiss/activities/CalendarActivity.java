package com.ormoyo.timiss.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ormoyo.timiss.R;
import com.ormoyo.timiss.ui.fragments.AddTaskFragment;

import java.util.Set;

public class CalendarActivity extends AppCompatActivity
{
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setSupportActionBar(findViewById(R.id.toolbar));

        SharedPreferences preferences = getSharedPreferences("MAIN", Context.MODE_PRIVATE);
        Set<String> tasks = preferences.getStringSet("Tasks", null);

        if (findViewById(R.id.addTask) instanceof Button button)
        {
            button.setOnClickListener((v) -> {
                AddTaskFragment fragment = new AddTaskFragment();
                fragment.show(getSupportFragmentManager(), "add_task");
            });
        }
    }
}