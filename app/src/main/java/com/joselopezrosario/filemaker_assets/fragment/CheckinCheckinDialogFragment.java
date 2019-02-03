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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.joselopezrosario.filemaker_assets.R;
import com.joselopezrosario.filemaker_assets.interfaces.CheckinDialogValuesConnection;
import com.joselopezrosario.filemaker_assets.interfaces.OnDialogCommitListener;
import com.joselopezrosario.filemaker_assets.util.NetworkUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CheckinCheckinDialogFragment extends DialogFragment implements CheckinDialogValuesConnection {

    private int recordId;
    private OnDialogCommitListener closedListener;

    public CheckinCheckinDialogFragment() {}

    public void attachCloseListener(OnDialogCommitListener listener) {
        closedListener = listener;
    }

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

        final CheckinDialogValuesConnection dvc = this;

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
                        NetworkUtil.edit(recordId, dvc);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                closedListener.onDialogCommit();
                                getDialog().dismiss();
                            }
                        });


                    }
                }).start();

            }
        });

        TextView dateTextview = view.findViewById(R.id.dialog_checkin_date_textview);

        String formattedDate = getDateChoice();

        dateTextview.setText(formattedDate);

        Spinner locationSpinner = view.findViewById(R.id.dialog_location_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.checkin_spinner_choices, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        locationSpinner.setAdapter(adapter);

    }

    @Override
    public String getDateChoice() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.UK);
        return format.format(cal.getTime());
    }

    @Override
    public String getLocationChoice() {
        Spinner spinner = this.getView().findViewById(R.id.dialog_location_spinner);
        return spinner.getSelectedItem().toString();
    }

    @Override
    public String getConditionChoice() {
        RadioGroup group = this.getView().findViewById(R.id.dialog_checkin_condition_radiogroup);

        switch(group.getCheckedRadioButtonId()) {
            case R.id.dialog_checkin_good_radiobutton:

                return getString(R.string.condition_good);

            case R.id.dialog_checkin_fair_radiobutton:

                return getString(R.string.condition_fair);

            case R.id.dialog_checkin_damaged_radiobutton:

                return getString(R.string.condition_damaged);

            case R.id.dialog_checkin_needsrepair_radiobutton:

                return getString(R.string.condition_needsrepair);

            case R.id.dialog_checkin_broken_radiobutton:

                return getString(R.string.condition_broken);

            default:
                throw new IllegalStateException("Invalid Checkin Condition box State");
        }
    }
}
