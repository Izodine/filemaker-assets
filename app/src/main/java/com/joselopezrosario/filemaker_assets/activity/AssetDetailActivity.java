package com.joselopezrosario.filemaker_assets.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.joselopezrosario.androidfm.FmRecord;
import com.joselopezrosario.filemaker_assets.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

public class AssetDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);

        String jsonString = getIntent().getStringExtra("RECORD_EXTRA");
        JSONObject jsonObject;
        FmRecord record = null;

        try {
            jsonObject = new JSONObject(jsonString);
            Constructor<FmRecord> constructor = FmRecord.class.getDeclaredConstructor(JSONObject.class);
            constructor.setAccessible(true);
            record = constructor.newInstance(jsonObject);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | JSONException | InvocationTargetException e) {
            e.printStackTrace();
        }

        String assetName = record.getValue("Item");
        String availability = record.getValue("Verbose Status");
        String assignedTo = record.getValue("Assigned To");
        String condition = record.getValue("Condition");
        String location = record.getValue("Location");
        String cost = record.getValue("Cost");

        setText(R.id.detail_title_textview, assetName);
        setText(R.id.detail_availability_textview, availability);
        setText(R.id.detail_assigned_to_textview, assignedTo);
        setText(R.id.detail_condition_status, condition);
        setText(R.id.detail_location_textview, location);
        setText(R.id.detail_book_value_textview, String.format(Locale.ENGLISH, "$%s", cost));

        if(availability.equalsIgnoreCase("available"))
            setText(R.id.detail_checked_textview, "Checked In");
        else
            setText(R.id.detail_checked_textview, String.format("Due %s", record.getValue("Date Due Popover")));

    }

    private void setText(int id, String text) {
        ((TextView)findViewById(id)).setText(text);
    }
}
