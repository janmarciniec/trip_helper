package com.example.trip_helper;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Ride;
import com.example.trip_helper.entities.Section;
import com.example.trip_helper.entities.relations.PassengerWithSections;
import com.example.trip_helper.entities.relations.SectionWithPassengers;
import com.example.trip_helper.entities.relations.SectionsPassengersCrossRef;

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

    LiveData<Integer> getSectionsCount() {
        return mRideDao.getSectionsCount();
    }

    LiveData<List<Section>> getRideWithSections(Long rideId) {
        return mRideDao.getRideWithSections(rideId);
    }

    void insertPassenger(Passenger passenger) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.insertPassenger(passenger); //dodanie nowego elementu za pomocą DAO
        });
    }

    void updatePassenger(Passenger passenger) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.updatePassenger(passenger); //aktualizacja elementu za pomocą DAO
        });
    }

    void deletePassenger(Passenger passenger) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.deletePassenger(passenger); //skasowanie elementu za pomocą DAO
        });
    }

    LiveData<List<Passenger>> getRideWithPassengers(Long rideId) {
        return mRideDao.getRideWithPassengers(rideId);
    }

    void insertSectionPassengerCrossRef(SectionsPassengersCrossRef crossRef) {
        RideRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRideDao.insertSectionPassengerCrossRef(crossRef); //dodanie nowego elementu za pomocą DAO
        });
    }

    List<PassengerWithSections> getSectionsOfPassenger(Long passengerId) {
        return mRideDao.getSectionsOfPassenger(passengerId);
    }

    List<SectionWithPassengers> getPassengersOfSection(Long sectionId) {
        return mRideDao.getPassengersOfSection(sectionId);
    }
}
