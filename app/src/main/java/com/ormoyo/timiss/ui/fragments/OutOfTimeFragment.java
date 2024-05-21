package com.ormoyo.timiss.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class OutOfTimeFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Your daily use time for this app has run out");

        builder.setNegativeButton("Close app", (dialogInterface, i) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        builder.setPositiveButton("Continue using app", (dialogInterface, i) -> {
            getActivity().finish();

            Intent intent = new Intent("brAppUseTimeServiceContinueUsingApp");
            intent.putExtra("App", getActivity().getIntent().getStringExtra("App"));
            getActivity().sendBroadcast(intent);
        });
        setCancelable(false);
        return builder.create();
    }
}
