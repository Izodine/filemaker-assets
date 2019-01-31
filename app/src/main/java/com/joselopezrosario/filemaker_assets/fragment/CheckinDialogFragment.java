package com.joselopezrosario.filemaker_assets.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.joselopezrosario.filemaker_assets.R;
import com.joselopezrosario.filemaker_assets.util.NetworkUtil;

import java.util.Calendar;

public class CheckinDialogFragment extends DialogFragment {
    private int recordId;
    public CheckinDialogFragment() {}

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if ( bundle != null) {
            recordId = getArguments().getInt("recordId", 0);
        }
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

        view.findViewById(R.id.dialog_checkin_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        view.findViewById(R.id.dialog_checkin_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.edit(recordId, "", "", "");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getDialog().dismiss();
                            }
                        });


                    }
                }).start();

            }
        });

        TextView dateTextview = view.findViewById(R.id.dialog_checkin_date_textview);

        dateTextview.setText(Calendar.getInstance().getTime().toString());

        Spinner locationSpinner = view.findViewById(R.id.dialog_location_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.checkin_spinner_choices, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        locationSpinner.setAdapter(adapter);

    }



}
