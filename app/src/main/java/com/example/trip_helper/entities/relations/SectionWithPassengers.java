package com.example.trip_helper.entities.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Section;

import java.util.List;

public class SectionWithPassengers {
    @Embedded public Section section;
    @Relation(
            parentColumn = "sectionId",
            entityColumn = "passengerId",
            associateBy = @Junction(SectionsPassengersCrossRef.class)
    )
    public List<Passenger> passengers;
}
