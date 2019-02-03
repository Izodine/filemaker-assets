package com.joselopezrosario.filemaker_assets.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joselopezrosario.filemaker_assets.R;
import com.joselopezrosario.filemaker_assets.interfaces.OnDialogCommitListener;
import com.joselopezrosario.filemaker_assets.fragment.CheckinCheckinDialogFragment;
import com.joselopezrosario.filemaker_assets.fragment.CheckoutDialogFragment;
import com.joselopezrosario.filemaker_assets.model.Asset;
import com.joselopezrosario.filemaker_assets.util.DownloadImage;
import com.joselopezrosario.filemaker_assets.util.NetworkUtil;

import java.util.Locale;

public class AssetDetailActivity extends AppCompatActivity implements OnDialogCommitListener {

    public static final String RECORD_EXTRA = "RECORD_EXTRA";

    private static final String CHECK_IN_FLAG = "CHECK_IN_FLAG";
    private boolean isCheckingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);

        Asset asset = getIntent().getParcelableExtra(RECORD_EXTRA);

        final int recordId = asset.getRecordId();

        String assetName = asset.getItem();
        String availability = asset.getStatusVerbose();
        String assignedTo = asset.getAssignedTo();
        String condition = asset.getCondition();
        String location = asset.getLocation();
        String cost = String.valueOf(asset.getCost());
        String imageUrl = asset.getImageUrl();

        setText(R.id.detail_title_textview, assetName);
        setText(R.id.detail_availability_textview, availability);
        setText(R.id.detail_assigned_to_textview, assignedTo);
        setText(R.id.detail_condition_status, condition);
        setText(R.id.detail_location_textview, location);
        setText(R.id.detail_book_value_textview, String.format(Locale.ENGLISH, "$%s", cost));
        ImageView imageView = findViewById(R.id.detail_imageview);
        DownloadImage.set(imageView, getApplicationContext(), imageUrl, 450, 450);

        isCheckingIn = availability.equalsIgnoreCase("available");

        if (isCheckingIn) {
            populateCheckinStateFields(recordId);
        } else {
            populateCheckoutStateFields(asset);
        }

    }

    private void populateCheckinStateFields(final int recordId) {
        setText(R.id.detail_checked_textview, getString(R.string.checked_in));
        Button checkOutButton = findViewById(R.id.check_button);
        checkOutButton.setText(R.string.check_out);

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                CheckoutDialogFragment checkoutFragment = new CheckoutDialogFragment();
                checkoutFragment.show(fm, "check-out-fragment");
                final Handler handler = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.editCheckOutTest(recordId,"","","");
                    }
                }).start();
            }
        });
    }

    private void populateCheckoutStateFields(final Asset record) {
        setText(R.id.detail_checked_textview, String.format(getString(R.string.date_due_detail), record.getDateDueAsString()));
        Button checkInButton = findViewById(R.id.check_button);
        checkInButton.setText(R.string.check_in);
        final AssetDetailActivity thisActivity = this;

        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                CheckinCheckinDialogFragment editNameDialogFragment = new CheckinCheckinDialogFragment();
                editNameDialogFragment.attachCloseListener(thisActivity);
                Bundle bundle = new Bundle();
                bundle.putInt("recordId", record.getRecordId());
                editNameDialogFragment.setArguments(bundle);
                editNameDialogFragment.show(fm, "check-in-fragment");
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        isCheckingIn = savedInstanceState.getBoolean(CHECK_IN_FLAG);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(CHECK_IN_FLAG, isCheckingIn);
    }

    private void setText(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }

    @Override
    public void onDialogCommit() {

    }


}
