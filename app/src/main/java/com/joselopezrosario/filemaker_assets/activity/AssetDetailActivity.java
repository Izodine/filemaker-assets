package com.joselopezrosario.filemaker_assets.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.joselopezrosario.filemaker_assets.Asset;
import com.joselopezrosario.filemaker_assets.R;

import java.util.Locale;

public class AssetDetailActivity extends AppCompatActivity {

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
        setImage(R.id.imageView, image);

        if(availability.equalsIgnoreCase("available"))
            setText(R.id.detail_checked_textview, "Checked In");
        else
            setText(R.id.detail_checked_textview, String.format("Due %s", dateDue));
    }
    private void setText(int id, String text) {
        ((TextView)findViewById(id)).setText(text);
    }

    private void setImage(int id, Bitmap image) {
        ((ImageView)findViewById(id)).setImageBitmap(image);
    }
}
