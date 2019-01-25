package com.joselopezrosario.filemaker_assets.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joselopezrosario.androidfm.FmCookie;
import com.joselopezrosario.androidfm.FmRecord;
import com.joselopezrosario.filemaker_assets.R;
import com.joselopezrosario.filemaker_assets.activity.AssetDetailActivity;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AssetRecyclerViewAdapter extends RecyclerView.Adapter<AssetRecyclerViewAdapter.AssetViewHolder> {

    private List<FmRecord> records;

    public void updateData(List<FmRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View container = LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_list_item,
                parent, false);

        return new AssetViewHolder(container);
    }

    @Override
    public void onBindViewHolder(@NonNull final AssetViewHolder assetViewModel, int position) {
        final FmRecord currentRecord = records.get(position);

        final Context context = assetViewModel.itemView.getContext();
        final String imageUrl = currentRecord.getValue("File 1 Container");

        populateImage(assetViewModel.listImage, context, imageUrl);

        assetViewModel.titleText.setText(currentRecord.getValue("Item"));
        assetViewModel.assignedToText.setText(currentRecord.getValue("Assigned To"));
        assetViewModel.categoryText.setText(currentRecord.getValue("Category"));
        assetViewModel.verboseStatusText.setText(currentRecord.getValue("Verbose Status"));

        String dateDue = currentRecord.getValue("Date Due");
        if(dateDue.isEmpty()) {
            assetViewModel.dueDateText.setVisibility(View.GONE);
        } else {
            assetViewModel.dueDateText.setText(currentRecord.getValue("Date Due"));
        }

        assetViewModel.locationText.setText(currentRecord.getValue("Location"));

        assetViewModel.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AssetDetailActivity.class);

                try {
                    Field field = currentRecord.getClass().getDeclaredField("record");
                    field.setAccessible(true);
                    JSONObject object = (JSONObject)field.get(currentRecord);
                    intent.putExtra("RECORD_EXTRA", object.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                view.getContext().startActivity(intent);
            }
        });
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

        builder.build().load(imageUrl).resize(0,50).centerCrop().into(image);
    }

    @Override
    public int getItemCount() {
        return records==null ? 0 : records.size();
    }

    final static class AssetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView listImage;
        protected TextView titleText;
        protected TextView assignedToText;
        protected TextView categoryText;
        protected TextView verboseStatusText;
        protected TextView dueDateText;
        protected TextView locationText;

        public AssetViewHolder(@NonNull View itemView) {
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
