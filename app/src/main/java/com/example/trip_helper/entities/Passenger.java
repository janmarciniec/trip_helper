package com.example.trip_helper.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Passenger {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "passengerId")
    private long mPassengerId;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "rideId")
    private long mRideId;

    public Passenger(long mPassengerId, @NonNull String mName, long mRideId) {
        this.mPassengerId = mPassengerId;
        this.mName = mName;
        this.mRideId = mRideId;
    }

    @Ignore
    public Passenger(@NonNull String mName, long mRideId) {
        this.mName = mName;
        this.mRideId = mRideId;
    }

    public long getMPassengerId() {
        return mPassengerId;
    }

    public void setMPassengerId(long mPassengerId) {
        this.mPassengerId = mPassengerId;
    }

    @NonNull
    public String getMName() {
        return mName;
    }

    public void setMName(@NonNull String mName) {
        this.mName = mName;
    }

    public long getMRideId() {
        return mRideId;
    }

    public void setMRideId(long mRideId) {
        this.mRideId = mRideId;
    }
}
