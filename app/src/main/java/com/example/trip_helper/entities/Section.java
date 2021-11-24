package com.example.trip_helper.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Section {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

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

    public Section(long mId, @NonNull String mOrigin, @NonNull String mDestination, @NonNull Double mDistance, long mRideId) {
        this.mId = mId;
        this.mOrigin = mOrigin;
        this.mDestination = mDestination;
        this.mDistance = mDistance;
        this.mRideId = mRideId;
    }

    @Ignore
    public Section(@NonNull String mOrigin, @NonNull String mDestination, @NonNull Double mDistance, long mRideId) {
        this.mOrigin = mOrigin;
        this.mDestination = mDestination;
        this.mDistance = mDistance;
        this.mRideId = mRideId;
    }

    public long getMId() {
        return mId;
    }

    public void setMId(long mId) {
        this.mId = mId;
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
}
