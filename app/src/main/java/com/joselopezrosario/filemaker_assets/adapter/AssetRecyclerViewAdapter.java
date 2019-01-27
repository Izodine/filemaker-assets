package com.joselopezrosario.filemaker_assets.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joselopezrosario.filemaker_assets.Asset;
import com.joselopezrosario.filemaker_assets.R;
import com.joselopezrosario.filemaker_assets.activity.AssetDetailActivity;

import java.util.ArrayList;

public class AssetRecyclerViewAdapter extends RecyclerView.Adapter<AssetRecyclerViewAdapter.AssetViewHolder> {

    private ArrayList<Asset> records;

    public void updateData(ArrayList<Asset>  records) {
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
        assetViewModel.listImage.setImageBitmap(record.getThumbnailImage());
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
