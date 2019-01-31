package com.joselopezrosario.filemaker_assets.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Asset implements Parcelable {
    public static final String ASSET_ID = "ASSET ID MATCH FIELD";
    public static final String ITEM = "Item";
    public static final String STATUS = "Verbose Status";
    public static final String ASSIGNED_TO = "Assigned To";
    public static final String CATEGORY = "Category To";
    public static final String CONDITION = "Condition";
    public static final String LOCATION = "Location";
    public static final String COST = "Cost";
    public static final String THUMBNAIL_IMAGE = "Asset Thumbnail";

    private int assetId;
    private String item;
    private String statusVerbose;
    private String assignedTo;
    private String category;
    private String condition;
    private String location;
    private double cost;
    private Date date_due;
    private String thumbnailUrl;
    private Bitmap thumbnailImage;

    public Asset() {
    }

    public Asset(int assetId, String item, String status_verbose, String assignedTo,
                 String category, String condition, String location, double cost, Date date_due,
                 String thumbnailUrl, Bitmap thumbnailImage) {
        this.assetId = assetId;
        this.item = item;
        this.statusVerbose = status_verbose;
        this.assignedTo = assignedTo;
        this.category = category;
        this.condition = condition;
        this.location = location;
        this.cost = cost;
        this.date_due = date_due;
        this.thumbnailUrl = thumbnailUrl;
        this.thumbnailImage = thumbnailImage;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStatusVerbose() {
        return statusVerbose;
    }

    public void setStatusVerbose(String statusVerbose) {
        this.statusVerbose = statusVerbose;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
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

    public Date getDateDue() {
        return date_due;
    }

    public void setDateDue(Date dateDue) {
        this.date_due = dateDue;
    }

    public String getDateDueAsString(){
        if ( this.date_due == null){
            return "";
        }else{
            return this.date_due.toString();
        }
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Bitmap getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(Bitmap thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    protected Asset(Parcel in) {
        assetId = in.readInt();
        item = in.readString();
        statusVerbose = in.readString();
        assignedTo = in.readString();
        category = in.readString();
        condition = in.readString();
        location = in.readString();
        cost = in.readDouble();
        long tmpDate_due = in.readLong();
        date_due = tmpDate_due != -1 ? new Date(tmpDate_due) : null;
        thumbnailUrl = in.readString();
        thumbnailImage = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(assetId);
        dest.writeString(item);
        dest.writeString(statusVerbose);
        dest.writeString(assignedTo);
        dest.writeString(category);
        dest.writeString(condition);
        dest.writeString(location);
        dest.writeDouble(cost);
        dest.writeLong(date_due != null ? date_due.getTime() : -1L);
        dest.writeString(thumbnailUrl);
        dest.writeValue(thumbnailImage);
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