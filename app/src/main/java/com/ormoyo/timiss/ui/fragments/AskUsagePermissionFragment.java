package com.ormoyo.timiss.ui.fragments;

import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ormoyo.timiss.R;
import com.ormoyo.timiss.Timiss;
import com.ormoyo.timiss.activities.MainActivity;


public class AskUsagePermissionFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AppOpsManager appOps = (AppOpsManager) getActivity().getSystemService(Context.APP_OPS_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.ask_usage_permission);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.settings, (dialogInterface, i) -> {
            Intent in = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(in);
        });

        appOps.startWatchingMode(AppOpsManager.OPSTR_GET_USAGE_STATS,
                getActivity().getApplicationContext().getPackageName(),
                new AppOpsManager.OnOpChangedListener() {
                    @Override
                    public void onOpChanged(String op, String packageName) {
                        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                                android.os.Process.myUid(), Timiss.getInstance().getPackageName());

                        if(mode != AppOpsManager.MODE_ALLOWED)
                            return;

                        appOps.stopWatchingMode(this);

                        Intent intent = new Intent(Timiss.getInstance(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                        Timiss.getInstance().startActivity(intent);
                        dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        setCancelable(false);
        return dialog;
    }
}
