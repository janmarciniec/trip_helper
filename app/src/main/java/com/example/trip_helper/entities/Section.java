package com.example.trip_helper.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Section implements Parcelable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "sectionId")
    private long mSectionId;

    @NonNull
    @ColumnInfo(name = "origin")
    private String mOrigin;

    @NonNull
    @ColumnInfo(name = "destination")
    private String mDestination;

    @NonNull
    @ColumnInfo(name = "distance")
    private Double mDistance;

    @NonNull
    @ColumnInfo(name = "rideId")
    private long mRideId;

    public Section(long mSectionId, @NonNull String mOrigin, @NonNull String mDestination, @NonNull Double mDistance, long mRideId) {
        this.mSectionId = mSectionId;
        this.mOrigin = mOrigin;
        this.mDestination = mDestination;
        this.mDistance = mDistance;
        this.mRideId = mRideId;
    }

    public long getMSectionId() {
        return mSectionId;
    }

    public void setMSectionId(long mSectionId) {
        this.mSectionId = mSectionId;
    }

    @NonNull
    public String getMOrigin() {
        return mOrigin;
    }

    public void setMOrigin(@NonNull String mOrigin) {
        this.mOrigin = mOrigin;
    }

    @NonNull
    public String getMDestination() {
        return mDestination;
    }

    public void setMDestination(@NonNull String mDestination) {
        this.mDestination = mDestination;
    }

    @NonNull
    public Double getMDistance() {
        return mDistance;
    }

    public void setMDistance(@NonNull Double mDistance) {
        this.mDistance = mDistance;
    }

    public long getMRideId() {
        return mRideId;
    }

    public void setMRideId(long mRideId) {
        this.mRideId = mRideId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mSectionId);
        dest.writeString(mOrigin);
        dest.writeString(mDestination);
        dest.writeDouble(mDistance);
        dest.writeLong(mRideId);
    }

    public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>() {
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        public Section[] newArray(int size) {
            return new Section[size];
        }
    };

    private Section(Parcel in) {
        mSectionId = in.readLong();
        mOrigin = in.readString();
        mDestination = in.readString();
        mDistance = in.readDouble();
        mRideId = in.readLong();
    }
}
