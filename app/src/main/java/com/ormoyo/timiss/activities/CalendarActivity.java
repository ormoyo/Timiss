package com.ormoyo.timiss.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.ormoyo.timiss.R;
import com.ormoyo.timiss.Timiss;
import com.ormoyo.timiss.tasks.Task;
import com.ormoyo.timiss.ui.fragments.AddTaskFragment;

import java.security.InvalidParameterException;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity
{
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setSupportActionBar(findViewById(R.id.toolbar));
        for (Task task : Timiss.getInstance().getTaskManager().getTasks())
            showTask(task);

        if (findViewById(R.id.addTask) instanceof Button button)
        {
            button.setOnClickListener((v) -> {
                AddTaskFragment fragment = new AddTaskFragment();
                fragment.show(getSupportFragmentManager(), "add_task");
            });
        }
    }

    private void showTask(Task task)
    {
        Calendar startTime = task.getStartTime();
        Calendar endTime = task.getEndTime();

        int startHour = startTime.get(Calendar.HOUR);
        int endHour = endTime.get(Calendar.HOUR);
        int startMinute = startTime.get(Calendar.MINUTE);
        int endMinute = endTime.get(Calendar.MINUTE);

        View startLine = findViewById(getTimeId(startHour));
        View endLine = findViewById(endMinute == 0 ? getTimeId(endHour) : getTimeId(endHour + 1));
        TextView textView = new TextView(this);
        textView.setText(task.getName());
        setupTVTask(textView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) findViewById(R.id.calendarTasks));
        constraintSet.center(textView.getId(),
                startLine.getId(), ConstraintSet.BOTTOM, (int) (20 * startMinute/60f + 0.5f),
                endLine.getId(), ConstraintSet.TOP, (int) (20 * endMinute/60f + 0.5f),
                0.5f);

        constraintSet.applyTo(findViewById(R.id.calendarTasks));
    }

    private void setupTVTask(TextView task)
    {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, 0);
        task.setLayoutParams(params);

        task.setTextColor(Color.WHITE);
        task.setBackgroundColor(getResources().getColor(R.color.main_text, getTheme()));
    }

    private static int getTimeId(int hour)
    {
        return switch (hour)
        {
            case 0 -> R.id.timeLine0;
            case 1 -> R.id.timeLine1;
            case 2 -> R.id.timeLine2;
            case 3 -> R.id.timeLine3;
            case 4 -> R.id.timeLine4;
            case 5 -> R.id.timeLine5;
            case 6 -> R.id.timeLine6;
            case 7 -> R.id.timeLine7;
            case 8 -> R.id.timeLine8;
            case 9 -> R.id.timeLine9;
            case 10 -> R.id.timeLine10;
            case 11 -> R.id.timeLine11;
            case 12 -> R.id.timeLine12;
            case 13 -> R.id.timeLine13;
            case 14 -> R.id.timeLine14;
            case 15 -> R.id.timeLine15;
            case 16 -> R.id.timeLine16;
            case 17 -> R.id.timeLine17;
            case 18 -> R.id.timeLine18;
            case 19 -> R.id.timeLine19;
            case 20 -> R.id.timeLine20;
            case 21 -> R.id.timeLine21;
            case 22 -> R.id.timeLine22;
            case 23 -> R.id.timeLine23;
            default -> throw new InvalidParameterException("");
        };
    }
}
