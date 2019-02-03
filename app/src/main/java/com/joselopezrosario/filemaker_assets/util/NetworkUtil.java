package com.joselopezrosario.filemaker_assets.util;

import android.content.Context;

import com.joselopezrosario.androidfm.Fm;
import com.joselopezrosario.androidfm.FmCookie;
import com.joselopezrosario.androidfm.FmData;
import com.joselopezrosario.androidfm.FmEdit;
import com.joselopezrosario.androidfm.FmFind;
import com.joselopezrosario.androidfm.FmRecord;
import com.joselopezrosario.androidfm.FmRequest;
import com.joselopezrosario.androidfm.FmResponse;
import com.joselopezrosario.androidfm.FmScript;
import com.joselopezrosario.filemaker_assets.interfaces.CheckinDialogValuesConnection;
import com.joselopezrosario.filemaker_assets.model.Asset;
import com.joselopezrosario.filemaker_assets.BuildConfig;

import java.util.ArrayList;

public final class NetworkUtil {
    final private static String URL = BuildConfig.API_HOST;
    final private static String LAYOUT_ASSETS = BuildConfig.DB_LAYOUT;
    final private static String ACCOUNT = BuildConfig.API_ACCOUNT;
    final private static String PASSWORD = BuildConfig.API_PASSWORD;


    private NetworkUtil() { /*Suppressed Constructor*/}

    public static ArrayList<Asset> getAllRecords(Context context) {
        ArrayList<Asset> assets = new ArrayList<>();
        FmRequest request = new FmRequest().login(URL, ACCOUNT, PASSWORD).build();
        FmResponse response = Fm.execute(request);
        String token = response.getToken();
        FmFind fmFind = new FmFind().newRequest().set(Asset.ASSET_ID, "*");
        request = new FmRequest().findRecords(URL, token, LAYOUT_ASSETS, fmFind).build();
        response = Fm.execute(request);
        if (response == null || !response.isOk()) {
            return null;
        }
        FmCookie.setCookie(context, URL, token,LAYOUT_ASSETS );
        Fm.execute(new FmRequest().logout(URL, token));
        FmData fmData = new FmData(response);
        int size = fmData.size();
        int i = 0;
        while (i < size) {
            FmRecord record = fmData.getRecord(i);
            Asset asset = new Asset();
            asset.setRecordId(record.getRecordId());
            asset.setModId(record.getModId());
            asset.setAssetId(record.getInt(Asset.ASSET_ID));
            asset.setItem(record.getString(Asset.ITEM));
            asset.setStatusVerbose(record.getString(Asset.STATUS));
            asset.setAssignedTo(record.getString(Asset.ASSIGNED_TO));
            asset.setCondition(record.getString(Asset.CONDITION));
            asset.setLocation(record.getString(Asset.LOCATION));
            asset.setDateCheckedIn(record.getString(Asset.DATE_CHECKED_IN));
            asset.setDateCheckedOut(record.getString(Asset.DATE_CHECKED_OUT));
            asset.setCost(record.getDouble(Asset.COST));
            asset.setThumbnailUrl(record.getString(Asset.THUMBNAIL_IMAGE));
            asset.setImageUrl(record.getString(Asset.FULLSIZE_IMAGE));
            assets.add(asset);
            i ++;
        }
        return assets;
    }

    public static Asset getRecordById(Context context, int recordId) {

        if(recordId < 0) {
            throw new IllegalArgumentException("Record ID cannot be less than 0.");
        }

        FmRequest request = new FmRequest().login(URL, ACCOUNT, PASSWORD).build();
        FmResponse response = Fm.execute(request);
        String token = response.getToken();
        FmFind fmFind = new FmFind().newRequest().set(Asset.ASSET_ID, Integer.toString(recordId));

        request = new FmRequest().findRecords(URL, token, LAYOUT_ASSETS, fmFind).build();

        response = Fm.execute(request);

        if (response == null || !response.isOk()) {
            return null;
        }

        FmCookie.setCookie(context, URL, token,LAYOUT_ASSETS );
        Fm.execute(new FmRequest().logout(URL, token));

        FmData fmData = new FmData(response);

        FmRecord record = fmData.getRecord(0);
        Asset asset = new Asset();
        asset.setRecordId(record.getRecordId());
        asset.setModId(record.getModId());
        asset.setAssetId(record.getInt(Asset.ASSET_ID));
        asset.setItem(record.getString(Asset.ITEM));
        asset.setStatusVerbose(record.getString(Asset.STATUS));
        asset.setAssignedTo(record.getString(Asset.ASSIGNED_TO));
        asset.setCondition(record.getString(Asset.CONDITION));
        asset.setLocation(record.getString(Asset.LOCATION));
        asset.setDateCheckedIn(record.getString(Asset.DATE_CHECKED_IN));
        asset.setDateCheckedOut(record.getString(Asset.DATE_CHECKED_OUT));
        asset.setCost(record.getDouble(Asset.COST));
        asset.setThumbnailUrl(record.getString(Asset.THUMBNAIL_IMAGE));
        asset.setImageUrl(record.getString(Asset.FULLSIZE_IMAGE));

        return asset;
    }

    public static FmResponse edit(int recordId, CheckinDialogValuesConnection dvc) {
        FmResponse login = Fm.execute(new FmRequest().login(URL, ACCOUNT, PASSWORD).build());
        if ( !login.isOk()){
            return login;
        }
        String token = login.getToken();
        FmEdit edit = new FmEdit()
                .set(Asset.CONDITION, dvc.getConditionChoice())
                .set(Asset.DATE_CHECKED_IN, dvc.getDateChoice())
                .set(Asset.LOCATION, dvc.getLocationChoice());
        FmScript checkInTrigger = new FmScript().setScript("Trigger | On Check-In Close");
        FmRequest checkInAsset = new FmRequest()
                .edit(URL, token, LAYOUT_ASSETS, recordId, edit)
                .setScriptParams(checkInTrigger)
                .build();
        FmResponse response = Fm.execute(checkInAsset);
        Fm.execute(new FmRequest().logout(URL, token));
        return response;
    }

    public static FmResponse editCheckOutTest(int recordId, String location, String dateCheckedIn, String condition) {
        FmResponse login = Fm.execute(new FmRequest().login(URL, ACCOUNT, PASSWORD).build());
        if ( !login.isOk()){
            return login;
        }
        String token = login.getToken();
        FmEdit edit = new FmEdit()
                .set(Asset.DATE_CHECKED_OUT, "02/1/2019")
                .set("Date Due", "01/19/2020");
        FmScript checkInTrigger = new FmScript().setScript("Trigger | On Check-Out Close");
        FmRequest checkInAsset = new FmRequest()
                .edit(URL, token, LAYOUT_ASSETS, recordId, edit)
                .setScriptParams(checkInTrigger)
                .build();
        FmResponse response = Fm.execute(checkInAsset);
        Fm.execute(new FmRequest().logout(URL, token));
        return response;
    }

}

