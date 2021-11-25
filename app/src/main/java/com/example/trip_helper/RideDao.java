package com.example.trip_helper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Ride;
import com.example.trip_helper.entities.Section;
import com.example.trip_helper.entities.relations.RideWithSections;
import com.example.trip_helper.entities.relations.RideWithPassengers;

import java.util.List;

@Dao
public interface RideDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertRide(Ride ride);

    @Update(onConflict = OnConflictStrategy.ABORT)
    void updateRide(Ride ride);

    @Delete
    void deleteRide(Ride ride);

    @Query("DELETE FROM ride")
    void deleteAllRides();

    @Query("SELECT * FROM ride ORDER BY id DESC")
    LiveData<List<Ride>> getAllRides();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertSection(Section section);

    @Update(onConflict = OnConflictStrategy.ABORT)
    void updateSection(Section section);

    @Delete
    void deleteSection(Section section);

    @Transaction
    @Query("SELECT * FROM section WHERE rideId = :rideId")
    LiveData<List<Section>> getRideWithSections(Long rideId);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertPassenger(Passenger passenger);

    @Update(onConflict = OnConflictStrategy.ABORT)
    void updatePassenger(Passenger passenger);

    @Delete
    void deletePassenger(Passenger passenger);

    @Transaction
    @Query("SELECT * FROM passenger WHERE rideId = :rideId")
    LiveData<List<Passenger>> getRideWithPassengers(Long rideId);
}
