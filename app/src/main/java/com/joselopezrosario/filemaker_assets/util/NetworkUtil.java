package com.joselopezrosario.filemaker_assets.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.joselopezrosario.androidfm.Fm;
import com.joselopezrosario.androidfm.FmData;
import com.joselopezrosario.androidfm.FmFind;
import com.joselopezrosario.androidfm.FmRecord;
import com.joselopezrosario.androidfm.FmRequest;
import com.joselopezrosario.androidfm.FmResponse;
import com.joselopezrosario.filemaker_assets.Asset;
import com.joselopezrosario.filemaker_assets.BuildConfig;

import java.util.ArrayList;

public final class NetworkUtil {

    private NetworkUtil() { /*Suppressed Constructor*/}

    public static ArrayList<Asset> getAllRecords(Context context) {
        ArrayList<Asset> assets = new ArrayList<>();
        String url = BuildConfig.API_HOST;
        FmRequest request = new FmRequest().login(url, BuildConfig.API_ACCOUNT, BuildConfig.API_PASSWORD).build();
        FmResponse response = Fm.execute(request);
        String token = response.getToken();
        FmFind fmFind = new FmFind().newRequest().set(Asset.ASSET_ID, "*");
        request = new FmRequest().findRecords(url, token, BuildConfig.DB_LAYOUT, fmFind).build();
        response = Fm.execute(request);
        if (response == null || !response.isOk()) {
            return null;
        }
        FmData fmData = new FmData(response);
        int size = fmData.size();
        int i = 0;
        while (i < size) {
            FmRecord record = fmData.getRecord(i);
            Asset asset = new Asset();
            asset.setAssetId(record.getInt(Asset.ASSET_ID));
            asset.setItem(record.getString(Asset.ITEM));
            asset.setStatusVerbose(record.getString(Asset.STATUS));
            asset.setAssignedTo(record.getString(Asset.ASSIGNED_TO));
            asset.setCondition(record.getString(Asset.CONDITION));
            asset.setLocation(record.getString(Asset.LOCATION));
            asset.setCost(record.getDouble(Asset.COST));
            String thumbnailUrl = record.getString(Asset.THUMBNAIL_IMAGE);
            Bitmap thumbnail = DownloadImage.execute(thumbnailUrl);
            asset.setThumbnailImage(thumbnail);
            assets.add(asset);
            i ++;
        }
        return assets;
    }
}

