package com.example.trip_helper.entities.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.trip_helper.entities.Ride;
import com.example.trip_helper.entities.Section;

import java.util.List;

public class RideWithSections {
    @Embedded public Ride ride;
    @Relation(
            parentColumn = "id",
            entityColumn = "rideId"
    )
    public List<Section> sections;
}
