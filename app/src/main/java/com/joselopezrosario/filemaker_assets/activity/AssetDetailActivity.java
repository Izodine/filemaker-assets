package com.joselopezrosario.filemaker_assets.activity;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joselopezrosario.filemaker_assets.Asset;
import com.joselopezrosario.filemaker_assets.R;
import com.joselopezrosario.filemaker_assets.fragment.CheckinFragment;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AssetDetailActivity extends AppCompatActivity {

    public static final String CHECK_IN_FLAG = "CHECK_IN_FLAG";
    private boolean isCheckingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);

        Asset asset = getIntent().getParcelableExtra("RECORD_EXTRA");

        String assetName = asset.getItem();
        String availability = asset.getStatus_verbose();
        String assignedTo = asset.getAssigned_to();
        String condition = asset.getCondition();
        String location = asset.getLocation();
        String cost = String.valueOf(asset.getCost());
        String dateDue = asset.getDate_due_AsString();
        Bitmap image = asset.getThumbnail_image();

        setText(R.id.detail_title_textview, assetName);
        setText(R.id.detail_availability_textview, availability);
        setText(R.id.detail_assigned_to_textview, assignedTo);
        setText(R.id.detail_condition_status, condition);
        setText(R.id.detail_location_textview, location);
        setText(R.id.detail_book_value_textview, String.format(Locale.ENGLISH, "$%s", cost));
        setImage(R.id.detail_imageview, image);

        isCheckingIn = availability.equalsIgnoreCase("available");

        if(isCheckingIn) {
            populateCheckinStateFields();
        } else {
            populateCheckoutStateFields(asset);
        }

    }

    private void populateCheckinStateFields() {
        setText(R.id.detail_checked_textview, getString(R.string.checked_in));
        Button checkOutButton = findViewById(R.id.check_button);
        checkOutButton.setText(R.string.check_out);

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                CheckinFragment editNameDialogFragment = new CheckinFragment();
                editNameDialogFragment.show(fm, "check-out-fragment");
            }
        });
    }

    private void populateCheckoutStateFields(Asset record) {
        setText(R.id.detail_checked_textview, String.format("Due %s", record.getDate_due_AsString()));
        Button checkInButton = findViewById(R.id.check_button);
        checkInButton.setText(R.string.check_in);

        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        ((TextView)findViewById(id)).setText(text);
    }

    private void setImage(int id, Bitmap image) {
        ((ImageView)findViewById(id)).setImageBitmap(image);
    }
}
