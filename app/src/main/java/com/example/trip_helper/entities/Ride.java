package com.example.trip_helper.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ride implements Parcelable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "fuelConsumption")
    private Double mFuelConsumption;

    @NonNull
    @ColumnInfo(name = "fuelPrice")
    private Double mFuelPrice;

    public Ride(long mId, @NonNull String mName, @NonNull Double mFuelConsumption, @NonNull Double mFuelPrice) {
        this.mId = mId;
        this.mName = mName;
        this.mFuelConsumption = mFuelConsumption;
        this.mFuelPrice = mFuelPrice;
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

    @NonNull
    public Double getMFuelConsumption() {
        return mFuelConsumption;
    }

    public void setMFuelConsumption(@NonNull Double mFuelConsumption) {
        this.mFuelConsumption = mFuelConsumption;
    }

    @NonNull
    public Double getMFuelPrice() {
        return mFuelPrice;
    }

    public void setMFuelPrice(@NonNull Double mFuelPrice) {
        this.mFuelPrice = mFuelPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeDouble(mFuelConsumption);
        dest.writeDouble(mFuelPrice);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Ride> CREATOR = new Parcelable.Creator<Ride>() {
        public Ride createFromParcel(Parcel in) {
            return new Ride(in);
        }

        public Ride[] newArray(int size) {
            return new Ride[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Ride(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mFuelConsumption = in.readDouble();
        mFuelPrice = in.readDouble();
    }
}
