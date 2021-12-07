package com.example.trip_helper.entities.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Section;

import java.util.List;

public class PassengerWithSections {
    @Embedded public Passenger passenger;
    @Relation(
            parentColumn = "passengerId",
            entityColumn = "sectionId",
            associateBy = @Junction(SectionsPassengersCrossRef.class)
    )
    public List<Section> sections;
}
