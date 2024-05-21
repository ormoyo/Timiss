package com.ormoyo.timiss.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ormoyo.timiss.R;

public class CreateMainAccountFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.choose_username);

// `       EditText name = view.findViewById(R.id.account_create_name);
//        builder.setPositiveButton(R.string.confirm, ((dialogInterface, i) -> {
//            try {
//                Prototype application = Prototype.getInstance();
//                Field field = Prototype.class.getDeclaredField("mainAccount");
//                field.setAccessible(true);
//                field.set(application, new Account(name.getText().toString()));
//
//                SharedPreferences preferences = getActivity().getSharedPreferences("MAIN", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("MainAccount", application.getMainAccount() != null ? application.getMainAccount().getName() : null);
//                editor.apply();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            Intent intent = new Intent(getActivity(), MainActivity.class);
//            startActivity(intent);
//
//            dismiss();
//        }));
        return builder.create();
    }
}
