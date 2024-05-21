package com.ormoyo.timiss.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ormoyo.timiss.AppInfo;
import com.ormoyo.timiss.R;
import com.ormoyo.timiss.Timiss;

import java.util.HashSet;
import java.util.Set;

public class CreateProfileFragment extends DialogFragment {
    private DialogInterface.OnCancelListener cancelListener;
    private final AppInfo app;

    public CreateProfileFragment(AppInfo app) {
        this.app = app;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog(getActivity()) {
            @Override
            public void onBackPressed() {
                cancelListener.onCancel(this);
                super.onBackPressed();
            }

            @Override
            public boolean onTouchEvent(@NonNull MotionEvent event) {
                cancelListener.onCancel(this);
                return super.onTouchEvent(event);
            }
        };

//        View view = getLayoutInflater().inflate(R.layout.fragment_create_profile, null);

        dialog.setTitle(R.string.set_use_time);
//        dialog.setView(view);

//        EditText hours = view.findViewById(R.id.app_hour_field);
//        EditText minutes = view.findViewById(R.id.app_minute_field);
//        EditText seconds = view.findViewById(R.id.app_second_field);
//
//        dialog.setOnCancelListener(cancelListener);
//
//        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), (dialogInterface, i) -> cancelListener.onCancel(dialogInterface));
//        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.confirm), (dialogInterface, i) -> {
//            if(hours.getText().toString().isEmpty() && minutes.getText().toString().isEmpty() && seconds.getText().toString().isEmpty()) {
//                Toast.makeText(getActivity(), R.string.invalid_use_time,
//                        Toast.LENGTH_SHORT).show();
//                cancelListener.onCancel(dialog);
//                return;
//            }
//
//            int hour = 0;
//            int minute = 0;
//            int second = 0;
//
//            if(!hours.getText().toString().isEmpty())
//                hour = Integer.parseInt(hours.getText().toString().replaceAll("[^0-9]", "")) * 3600;
//            if(!minutes.getText().toString().isEmpty())
//                minute = Integer.parseInt(minutes.getText().toString().replaceAll("[^0-9]", "")) * 60;
//            if(!seconds.getText().toString().isEmpty())
//                second = Integer.parseInt(seconds.getText().toString().replaceAll("[^0-9]", ""));
//
//            dismiss();
//        });

        return dialog;
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener listener) {
        this.cancelListener = listener;
    }

    public AppInfo getApp() {
        return app;
    }
}
