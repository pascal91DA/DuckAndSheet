package com.pascal91.duckandsheet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                .setPositiveButton(R.string.signin, (dialog, id) -> {
                    EditText editText = MyDialogFragment.this.getDialog().findViewById(R.id.username);
                    System.out.println("Логин: " + editText.getText());
                    Toast.makeText(getActivity(), "Логин: " + editText.getText(), Toast.LENGTH_LONG).show();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> MyDialogFragment.this.getDialog().cancel());
        return builder.create();
    }
}