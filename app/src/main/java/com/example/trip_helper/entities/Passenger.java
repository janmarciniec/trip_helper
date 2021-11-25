package com.example.trip_helper.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Passenger {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "rideId")
    private long mRideId;

    public Passenger(long mId, @NonNull String mName, long mRideId) {
        this.mId = mId;
        this.mName = mName;
        this.mRideId = mRideId;
    }

    @Ignore
    public Passenger(@NonNull String mName, long mRideId) {
        this.mName = mName;
        this.mRideId = mRideId;
    }

    public long getMId() {
        return mId;
    }

    public void setMId(long mId) {
        this.mId = mId;
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
