package com.joselopezrosario.filemaker_assets.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.joselopezrosario.filemaker_assets.model.Asset;
import com.joselopezrosario.filemaker_assets.R;
import com.joselopezrosario.filemaker_assets.adapter.AssetRecyclerViewAdapter;
import com.joselopezrosario.filemaker_assets.util.NetworkUtil;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AssetRecyclerViewAdapter assetAdapter;
    private RecyclerView assetRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        assetRecyclerView = findViewById(R.id.asset_recyclerview);
        assetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assetAdapter = new AssetRecyclerViewAdapter();
        assetRecyclerView.setAdapter(assetAdapter);

        final TextView resultsCounter = findViewById(R.id.results_count_textview);
        final Context context = this;
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<Asset> data = NetworkUtil.getAllRecords(context);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        assetAdapter.updateData(data);
                        int size = 0;
                        if (data != null) size = data.size();
                        resultsCounter.setText(String.format(Locale.ENGLISH,"Showing %d Results.", size));
                    }
                });


            }
        }).start();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Action goes here
            }
        });

    }
}
