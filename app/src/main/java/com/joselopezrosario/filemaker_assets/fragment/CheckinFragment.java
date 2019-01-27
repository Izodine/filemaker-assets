package com.joselopezrosario.filemaker_assets.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.joselopezrosario.filemaker_assets.R;

import java.util.Calendar;

public class CheckinFragment extends DialogFragment {

    public CheckinFragment() {}

    @Override
    public void onStart() {
        super.onStart();

        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dialog_checkin, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view

        final TextView dueDateEditText = view.findViewById(R.id.dialog_due_date_edittext);

//        final DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext());
//
//        dueDateEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                        int day = datePicker.getDayOfMonth();
//                        int month = datePicker.getMonth();
//                        int year =  datePicker.getYear();
//
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.set(year, month, day);
//
//                        dueDateEditText.setText(calendar.getTime().toString());
//                    }
//                });
//                datePickerDialog.show();
//            }
//        });
        // Fetch arguments from bundle and set title
        getDialog().setTitle("Check In Asset");
        // Show soft keyboard automatically and request focus to field

    }



}
