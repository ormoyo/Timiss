package com.ormoyo.timiss.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ormoyo.timiss.R;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AddTaskFragment extends DialogFragment
{
    public AddTaskFragment()
    {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.fragment_add_task, null);

        builder.setTitle("Add Task");
        builder.setView(view);

        EditText start = view.findViewById(R.id.startTime);
        EditText end = view.findViewById(R.id.endTime);

        builder.setPositiveButton(R.string.add_task, ((dialog, which) -> {
            SharedPreferences preferences = requireActivity().getSharedPreferences("MAIN", Context.MODE_PRIVATE);
            Set<String> oldSet = preferences.getStringSet("Tasks", new HashSet<>());

            Set<String> set = new HashSet<>(oldSet);
            set.add(start.getText() + "-" + end.getText());

            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet("Tasks", set);

            editor.apply();
            dismiss();
        }));

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedI_nstanceState) {

        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }
}