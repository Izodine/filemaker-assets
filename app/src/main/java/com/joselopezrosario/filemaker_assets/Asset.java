package com.joselopezrosario.filemaker_assets;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Asset implements Parcelable {
    public static String ASSET_ID = "ASSET ID MATCH FIELD";
    public static String ITEM = "Item";
    public static String STATUS = "Verbose Status";
    public static String ASSIGNED_TO = "Assigned To";
    public static String CATEGORY = "Category To";
    public static String CONDITION = "Condition";
    public static String LOCATION = "Location";
    public static String COST = "Cost";
    public static String THUMBNAIL_IMAGE = "Asset Thumbnail";

    private int asset_id;
    private String item;
    private String status_verbose;
    private String assigned_to;
    private String category;
    private String condition;
    private String location;
    private double cost;
    private Date date_due;
    private String thumbnail_url;
    private Bitmap thumbnail_image;

    public Asset() {
    }

    public Asset(int asset_id, String item, String status_verbose, String assigned_to,
                 String category, String condition, String location, double cost, Date date_due,
                 String thumbnail_url, Bitmap thumbnail_image) {
        this.asset_id = asset_id;
        this.item = item;
        this.status_verbose = status_verbose;
        this.assigned_to = assigned_to;
        this.category = category;
        this.condition = condition;
        this.location = location;
        this.cost = cost;
        this.date_due = date_due;
        this.thumbnail_url = thumbnail_url;
        this.thumbnail_image = thumbnail_image;
    }

    public int getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(int asset_id) {
        this.asset_id = asset_id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStatus_verbose() {
        return status_verbose;
    }

    public void setStatus_verbose(String status_verbose) {
        this.status_verbose = status_verbose;
    }

    public String getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDate_due() {
        return date_due;
    }

    public void setDate_due(Date date_due) {
        this.date_due = date_due;
    }

    public String getDate_due_AsString(){
        if ( this.date_due == null){
            return "";
        }else{
            return this.date_due.toString();
        }
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Bitmap getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(Bitmap thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    protected Asset(Parcel in) {
        asset_id = in.readInt();
        item = in.readString();
        status_verbose = in.readString();
        assigned_to = in.readString();
        category = in.readString();
        condition = in.readString();
        location = in.readString();
        cost = in.readDouble();
        long tmpDate_due = in.readLong();
        date_due = tmpDate_due != -1 ? new Date(tmpDate_due) : null;
        thumbnail_url = in.readString();
        thumbnail_image = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(asset_id);
        dest.writeString(item);
        dest.writeString(status_verbose);
        dest.writeString(assigned_to);
        dest.writeString(category);
        dest.writeString(condition);
        dest.writeString(location);
        dest.writeDouble(cost);
        dest.writeLong(date_due != null ? date_due.getTime() : -1L);
        dest.writeString(thumbnail_url);
        dest.writeValue(thumbnail_image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Asset> CREATOR = new Parcelable.Creator<Asset>() {
        @Override
        public Asset createFromParcel(Parcel in) {
            return new Asset(in);
        }

        @Override
        public Asset[] newArray(int size) {
            return new Asset[size];
        }
    };
}