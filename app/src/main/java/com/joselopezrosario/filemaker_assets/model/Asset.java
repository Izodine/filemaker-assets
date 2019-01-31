package com.joselopezrosario.filemaker_assets.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Asset implements Parcelable {
    public static final String RECORD_ID = "recordId";
    public static final String MOD_ID = "modId";
    public static final String ASSET_ID = "ASSET ID MATCH FIELD";
    public static final String ITEM = "Item";
    public static final String STATUS = "Verbose Status";
    public static final String ASSIGNED_TO = "Assigned To";
    public static final String CATEGORY = "Category To";
    public static final String CONDITION = "Condition";
    public static final String LOCATION = "Location";
    public static final String DATE_CHECKED_IN = "Date Checked In";
    public static final String DATE_CHECKED_OUT = "Date Checked Out";
    public static final String COST = "Cost";
    public static final String THUMBNAIL_IMAGE = "Asset Thumbnail";
    public static final String FULLSIZE_IMAGE = "Asset Fullsize";

    private int recordId;
    private int modId;
    private int assetId;
    private String item;
    private String statusVerbose;
    private String assignedTo;
    private String category;
    private String condition;
    private String location;
    private String dateCheckedIn;
    private String dateCheckedOut;
    private double cost;
    private Date dateDue;
    private String thumbnailUrl;
    private String imageUrl;

    public Asset() {
    }

    public Asset(int recordId, int modId, int assetId, String item, String status_verbose, String assignedTo,
                 String category, String condition, String location, String dateCheckedIn, String dateCheckedOut,
                 double cost, Date dateDue,
                 String thumbnailUrl, String imageUrl) {
        this.recordId = recordId;
        this.modId = modId;
        this.assetId = assetId;
        this.item = item;
        this.statusVerbose = status_verbose;
        this.assignedTo = assignedTo;
        this.category = category;
        this.condition = condition;
        this.location = location;
        this.dateCheckedIn = dateCheckedIn;
        this.dateCheckedOut = dateCheckedOut;
        this.cost = cost;
        this.dateDue = dateDue;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getModId() {
        return modId;
    }

    public void setModId(int modId) {
        this.modId = modId;
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

    public String getDateCheckedIn() {
        return dateCheckedIn;
    }

    public void setDateCheckedIn(String dateCheckedIn) {
        this.dateCheckedIn = dateCheckedIn;
    }

    public String getDateCheckedOut() {
        return dateCheckedOut;
    }

    public void setDateCheckedOut(String dateCheckedOut) {
        this.dateCheckedOut = dateCheckedOut;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public String getDateDueAsString(){
        if ( this.dateDue == null){
            return "";
        }else{
            return this.dateDue.toString();
        }
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    protected Asset(Parcel in) {
        recordId = in.readInt();
        modId = in.readInt();
        assetId = in.readInt();
        item = in.readString();
        statusVerbose = in.readString();
        assignedTo = in.readString();
        category = in.readString();
        condition = in.readString();
        location = in.readString();
        dateCheckedIn = in.readString();
        dateCheckedOut = in.readString();
        cost = in.readDouble();
        long tmpDate_due = in.readLong();
        dateDue = tmpDate_due != -1 ? new Date(tmpDate_due) : null;
        thumbnailUrl = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recordId);
        dest.writeInt(modId);
        dest.writeInt(assetId);
        dest.writeString(item);
        dest.writeString(statusVerbose);
        dest.writeString(assignedTo);
        dest.writeString(category);
        dest.writeString(condition);
        dest.writeString(location);
        dest.writeString(dateCheckedIn);
        dest.writeString(dateCheckedOut);
        dest.writeDouble(cost);
        dest.writeLong(dateDue != null ? dateDue.getTime() : -1L);
        dest.writeString(thumbnailUrl);
        dest.writeString(imageUrl);
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