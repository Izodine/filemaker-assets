package com.joselopezrosario.filemaker_assets.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joselopezrosario.androidfm.FmCookie;
import com.joselopezrosario.filemaker_assets.model.Asset;
import com.joselopezrosario.filemaker_assets.R;
import com.joselopezrosario.filemaker_assets.activity.AssetDetailActivity;
import com.joselopezrosario.filemaker_assets.util.DownloadImage;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AssetRecyclerViewAdapter extends RecyclerView.Adapter<AssetRecyclerViewAdapter.AssetViewHolder> {
    private Context context;
    private ArrayList<Asset> records;

    public void updateData(Context context, ArrayList<Asset> records) {
        this.context = context;
        this.records = records;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View container = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.asset_list_item, parent, false);
        return new AssetViewHolder(container);
    }

    @Override
    public void onBindViewHolder(@NonNull final AssetViewHolder assetViewModel, int position) {
        final Asset record = records.get(position);
        assetViewModel.titleText.setText(record.getItem());
        assetViewModel.assignedToText.setText(record.getAssignedTo());
        assetViewModel.categoryText.setText(record.getCategory());
        assetViewModel.verboseStatusText.setText(record.getStatusVerbose());
        String dateDue = record.getDateDueAsString();
        if(dateDue.isEmpty()) {
            assetViewModel.dueDateText.setVisibility(View.GONE);
        } else {
            assetViewModel.dueDateText.setText(dateDue);
        }
        assetViewModel.locationText.setText(record.getLocation());
        assetViewModel.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AssetDetailActivity.class);
                intent.putExtra("RECORD_EXTRA", record);
                view.getContext().startActivity(intent);
            }
        });
        DownloadImage.set(assetViewModel.listImage, context, record.getThumbnailUrl(), 250,250);
    }

    @Override
    public int getItemCount() {
        return records==null ? 0 : records.size();
    }

    final static class AssetViewHolder extends RecyclerView.ViewHolder{

        ImageView listImage;
        TextView titleText;
        TextView assignedToText;
        TextView categoryText;
        TextView verboseStatusText;
        TextView dueDateText;
        TextView locationText;

        AssetViewHolder(@NonNull View itemView) {
            super(itemView);
            listImage = itemView.findViewById(R.id.asset_list_imageview);
            titleText = itemView.findViewById(R.id.asset_title_textview);
            assignedToText = itemView.findViewById(R.id.assigned_to_textview);
            categoryText = itemView.findViewById(R.id.category_textview);
            verboseStatusText = itemView.findViewById(R.id.verbose_status_textview);
            dueDateText = itemView.findViewById(R.id.due_date_textview);
            locationText = itemView.findViewById(R.id.location_textview);

        }
    }
}
