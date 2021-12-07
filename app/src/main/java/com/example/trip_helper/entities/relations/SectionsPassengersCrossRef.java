package com.example.trip_helper.entities.relations;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"sectionId", "passengerId"})
public class SectionsPassengersCrossRef {
    @ColumnInfo(name = "sectionId")
    private long mSectionId;

    @ColumnInfo(name = "passengerId")
    private long mPassengerId;

    public SectionsPassengersCrossRef(long mSectionId, long mPassengerId) {
        this.mSectionId = mSectionId;
        this.mPassengerId = mPassengerId;
    }

    public long getMSectionId() {
        return mSectionId;
    }

    public void setMSectionId(long mSectionId) {
        this.mSectionId = mSectionId;
    }

    public long getMPassengerId() {
        return mPassengerId;
    }

    public void setMPassengerId(long mPassengerId) {
        this.mPassengerId = mPassengerId;
    }
}
