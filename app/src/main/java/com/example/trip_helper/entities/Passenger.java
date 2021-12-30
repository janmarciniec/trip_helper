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

    @ColumnInfo(name = "totalFee")
    private Double mTotalFee;

    public Passenger(long mPassengerId, @NonNull String mName, long mRideId, Double mTotalFee) {
        this.mPassengerId = mPassengerId;
        this.mName = mName;
        this.mRideId = mRideId;
        this.mTotalFee = mTotalFee;
    }

    @Ignore
    public Passenger(@NonNull String mName, long mRideId, Double mTotalFee) {
        this.mName = mName;
        this.mRideId = mRideId;
        this.mTotalFee = mTotalFee;
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

    public Double getMTotalFee() {
        return mTotalFee;
    }

    public void setMTotalFee(Double mTotalFee) {
        this.mTotalFee = mTotalFee;
    }
}
