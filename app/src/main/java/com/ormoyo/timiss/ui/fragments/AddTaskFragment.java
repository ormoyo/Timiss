package com.ormoyo.timiss.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.ormoyo.timiss.R;
import com.ormoyo.timiss.Timiss;
import com.ormoyo.timiss.tasks.Task;
import com.ormoyo.timiss.tasks.TaskManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddTaskFragment extends DialogFragment
{
    public AddTaskFragment()
    {
    }

    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @NonNull
    @Override
    @SuppressWarnings("deprecation")
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        this.requireContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.fragment_add_task, null);

        builder.setTitle("Add Task");
        builder.setView(view);

        View.OnClickListener listener = (v) ->
                onTimePicker((EditText) v);
        View.OnClickListener dateListener = (v) ->
                onDatePicker((EditText) v);
        View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
            if (hasFocus)
                onTimePicker((EditText) v);
        };
        View.OnFocusChangeListener focusDateListener = (v, hasFocus) -> {
            if (hasFocus)
                onDatePicker((EditText) v);
        };

        EditText name = view.findViewById(R.id.name);
        EditText date = view.findViewById(R.id.date);
        EditText start = view.findViewById(R.id.startTime);
        EditText end = view.findViewById(R.id.endTime);

        start.setOnClickListener(listener);
        start.setOnFocusChangeListener(focusListener);

        end.setOnClickListener(listener);
        end.setOnFocusChangeListener(focusListener);

        date.setOnClickListener(dateListener);
        date.setOnFocusChangeListener(focusDateListener);

        builder.setPositiveButton(R.string.add_task, ((dialog, which) -> {
            TaskManager manager = Timiss.getInstance().getTaskManager();

            String[] s = start.getText().toString().split(":");
            String[] e = start.getText().toString().split(":");

            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();

            try
            {
                Date d = formatter.parse(date.getText().toString());
                int hour = Integer.parseInt(s[0]), minute = Integer.parseInt(s[1]);

                assert d != null;
                startTime.set(d.getYear(), d.getMonth(), d.getDay(), hour, minute);
                hour = Integer.parseInt(s[0]); minute = Integer.parseInt(s[1]);
                endTime.set(d.getYear(), d.getMonth(), d.getDay(), hour, minute);

                manager.addTask(new Task(name.getText().toString(), startTime, endTime));
            } catch (ParseException ex)
            {
                throw new RuntimeException(ex);
            }
        }));

        return builder.create();
    }

    private void onTimePicker(EditText text)
    {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(AddTaskFragment.this.getContext(),
                (picker, hour, min) -> {
                    String h = hour < 10 ? "0" + hour : Integer.toString(hour);
                    String m = min < 10 ? "0" + min : Integer.toString(min);

                    text.setText(h + ":" + m);
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }

    private void onDatePicker(EditText text)
    {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(AddTaskFragment.this.getContext(),
                (picker, year, month, day) -> {
                    String date = formatter.format(new Date(year, month, day));
                    text.setText(date);
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.DAY_OF_WEEK));
        dialog.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedI_nstanceState) {

        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }
}
