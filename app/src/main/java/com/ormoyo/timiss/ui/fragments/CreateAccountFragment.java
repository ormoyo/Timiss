package com.ormoyo.prototype.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ormoyo.timiss.R;

public class CreateAccountFragment extends DialogFragment {
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
////        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////        View view = getLayoutInflater().inflate(R.layout.fragment_create_account, null);
////
////        builder.setTitle(R.string.add_account);
////        builder.setView(view);
////
////        EditText name = view.findViewById(R.id.account_create_name);
////        EditText password = view.findViewById(R.id.account_create_password);
////
////        builder.setNegativeButton(R.string.cancel, null);
////        builder.setPositiveButton(R.string.confirm, ((dialogInterface, i) -> {
////            if(name.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
////                Toast.makeText(getActivity(), R.string.missing_username_password,
////                        Toast.LENGTH_SHORT).show();
////                return;
////            }
////
////            for(Account account : Prototype.getInstance().getOnlineAccounts()) {
////                if(account.getName().equals(name.getText().toString())) {
////                    Toast.makeText(getActivity(), "Account already exists",
////                            Toast.LENGTH_SHORT).show();
////                    return;
////                }
////            }
////
////            Prototype application = Prototype.getInstance();
////            application.addAccount(name.getText().toString(), password.getText().toString());
////
////            dismiss();
////        }));
////        return builder.create();
//    }
}