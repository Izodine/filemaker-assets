package com.joselopezrosario.filemaker_assets.activity;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joselopezrosario.androidfm.FmCookie;
import com.joselopezrosario.androidfm.FmRecord;
import com.joselopezrosario.filemaker_assets.R;
import com.joselopezrosario.filemaker_assets.fragment.CheckinFragment;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        String imageUrl = record.getValue("File 1 Container");

        setText(R.id.detail_title_textview, assetName);
        setText(R.id.detail_availability_textview, availability);
        setText(R.id.detail_assigned_to_textview, assignedTo);
        setText(R.id.detail_condition_status, condition);
        setText(R.id.detail_location_textview, location);
        setText(R.id.detail_book_value_textview, String.format(Locale.ENGLISH, "$%s", cost));

        ImageView image = findViewById(R.id.detail_imageview);
        populateImage(image, this, imageUrl);

        isCheckingIn = availability.equalsIgnoreCase("available");

        if(isCheckingIn) {
            populateCheckinStateFields(record);
        } else {
            populateCheckoutStateFields(record);
        }

    }

    private void populateCheckinStateFields(FmRecord record) {
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

    private void populateCheckoutStateFields(FmRecord record) {
        setText(R.id.detail_checked_textview, String.format("Due %s", record.getValue("Date Due Popover")));
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

    private void populateImage(@NonNull ImageView image, final Context context, String imageUrl) {
        OkHttpClient downloader = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        final Request original = chain.request();
                        final Request authorized = original.newBuilder()
                                .addHeader(FmCookie.getName(context), FmCookie.getValue(context))
                                .build();
                        return chain.proceed(authorized);
                    }
                })
                .cache(new Cache(context.getCacheDir(), 25 * 1024 * 1024))
                .build();

        Picasso.Builder builder = new Picasso
                .Builder(context)
                .downloader(new OkHttp3Downloader(downloader));

        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });

        builder.build().load(imageUrl).resize(150,150).centerCrop().into(image);
    }

    private void setText(int id, String text) {
        ((TextView)findViewById(id)).setText(text);
    }
}
