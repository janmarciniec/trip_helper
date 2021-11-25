package com.example.trip_helper.entities.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Ride;

import java.util.List;

public class RideWithPassengers {
    @Embedded public Ride ride;
    @Relation(
            parentColumn = "id",
            entityColumn = "rideId"
    )
    public List<Passenger> passengers;
}
