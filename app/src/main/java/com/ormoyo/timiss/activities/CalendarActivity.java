package com.ormoyo.timiss.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ormoyo.timiss.R;

public class CalendarActivity extends AppCompatActivity
{
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (findViewById(R.id.calendarTasks) instanceof ConstraintLayout layout)
        {
            for (int i = 0; i < 24; i++)
            {
                TextView timeStamp = new TextView(this);

                timeStamp.setText((i < 10 ? "0" : "") + i + ":00");
                timeStamp.setTextColor(getResources().getColor(R.color.main_text, getTheme()));

                layout.addView(timeStamp);
            }
        }
    }
}