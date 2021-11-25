package com.example.trip_helper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Ride;
import com.example.trip_helper.entities.Section;
import com.example.trip_helper.entities.relations.RideWithSections;
import com.example.trip_helper.entities.relations.RideWithPassengers;

import java.util.List;

public class RideViewModel extends AndroidViewModel {
    private final RideRepository mRepository;
    private final LiveData<List<Ride>> mAllRides;

    public RideViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RideRepository(application);
        mAllRides = mRepository.getAllElements();    //pobranie wszystkich elementów z repozytorium
    }

    LiveData<List<Ride>> getAllRides() {
        return mAllRides;
    }

    public void insertRide(Ride ride) {
        mRepository.insertRide(ride);    //dodanie nowego elementu do repozytorium
    }

    public void updateRide(Ride ride) {
        mRepository.updateRide(ride);    //aktualizacja elementu w repozytorium
    }

    public void deleteRide(Ride ride) {
        mRepository.deleteRide(ride);    //skasowanie elementu z repozytorium
    }

    public void deleteAllRides() {
        mRepository.deleteAllRides();    //skasowanie wszystkich elementów z repozytorium
    }

    public void insertSection(Section section) {
        mRepository.insertSection(section);    //dodanie nowego elementu do repozytorium
    }

    public void updateSection(Section section) {
        mRepository.updateSection(section);    //aktualizacja elementu w repozytorium
    }

    public void deleteSection(Section section) {
        mRepository.deleteSection(section);    //skasowanie elementu z repozytorium
    }

    LiveData<List<Section>> getRideWithSections(Long rideId) {
        return mRepository.getRideWithSections(rideId);
    }

    public void insertPassenger(Passenger passenger) {
        mRepository.insertPassenger(passenger);    //dodanie nowego elementu do repozytorium
    }

    public void updatePassenger(Passenger passenger) {
        mRepository.updatePassenger(passenger);    //aktualizacja elementu w repozytorium
    }

    public void deletePassenger(Passenger passenger) {
        mRepository.deletePassenger(passenger);    //skasowanie elementu z repozytorium
    }

    LiveData<List<Passenger>> getRideWithPassengers(Long rideId) {
        return mRepository.getRideWithPassengers(rideId);
    }
}
