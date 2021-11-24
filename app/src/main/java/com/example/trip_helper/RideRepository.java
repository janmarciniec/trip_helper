package com.example.trip_helper;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.trip_helper.entities.Ride;
import com.example.trip_helper.entities.Section;
import com.example.trip_helper.entities.relations.RideWithSections;

import java.util.List;

public class RideRepository {
    private RideDao mRideDao;
    private LiveData<List<Ride>> mAllRides;

    RideRepository(Application application) {
        RideRoomDatabase rideRoomDatabase = RideRoomDatabase.getDatabase(application);
        //repozytorium korzysta z obiektu DAO do odwołań do bazy
        mRideDao = rideRoomDatabase.rideDao();
        mAllRides = mRideDao.getAllRides();   //odczytanie wszystkich elementów z DAO
    }

    LiveData<List<Ride>> getAllElements() {
        return mAllRides;    //metoda zwraca wszystkie elementy
    }

    void insertRide(Ride ride) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.insertRide(ride); //dodanie nowego elementu za pomocą DAO
        });
    }

    void updateRide(Ride ride) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.updateRide(ride); //aktualizacja elementu za pomocą DAO
        });
    }

    void deleteRide(Ride ride) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.deleteRide(ride); //skasowanie elementu za pomocą DAO
        });
    }

    void deleteAllRides() {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.deleteAllRides(); //skasowanie wszystkich elementów za pomocą DAO
        });
    }

    void insertSection(Section section) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.insertSection(section); //dodanie nowego elementu za pomocą DAO
        });
    }

    void updateSection(Section section) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.updateSection(section); //aktualizacja elementu za pomocą DAO
        });
    }

    void deleteSection(Section section) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.deleteSection(section); //skasowanie elementu za pomocą DAO
        });
    }

    LiveData<List<Section>> getRideWithSections(Long rideId) {
        return mRideDao.getRideWithSections(rideId);
    }
}
