package com.example.trip_helper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trip_helper.entities.Ride;

public class ResultsActivity extends AppCompatActivity {

    private RideViewModel mRideViewModel;
    private ResultsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button buttonReturn = findViewById(R.id.buttonReturn);
        Bundle bundle = getIntent().getExtras();
        Ride ride = bundle.getParcelable("ride");

        this.setTitle(ride.getMName() + " - Podsumowanie");

        Long rideId = ride.getMId();
        Double fuelConsumption = ride.getMFuelConsumption();
        Double fuelPrice = ride.getMFuelPrice();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ResultsAdapter(this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRideViewModel = new ViewModelProvider(this).get(RideViewModel.class);

        // obliczenie składki dla każdej sekcji
        mRideViewModel.getRideWithSections(rideId).observe(this, sections -> {
            for (int i=0; i<sections.size(); i++) {
                Double sectionPrice = sections.get(i).getMDistance() * fuelConsumption * 0.01 * fuelPrice;
                // pasażerowie z konkretnej sekcji
                int finalI = i;
                mRideViewModel.getPassengersOfSection(sections.get(i).getMSectionId()).observe(this, passengersOfSection -> {
                    int numOfPassengers = passengersOfSection.get(0).passengers.size();
                    // składka dla sekcji
                    Double passengerFee = sectionPrice/numOfPassengers;
                    passengerFee = Math.round(passengerFee*100.0)/100.0;

                    sections.get(finalI).setMPassengerFee(passengerFee);
                    mRideViewModel.updateSection(sections.get(finalI));
                });
            }
        });

        // obliczenie i wyświetlenie składki dla każdego pasażera
        mRideViewModel.getRideWithPassengers(rideId).observe(this, passengers -> {
            for(int i=0; i<passengers.size(); i++) {
                int finalI = i;
                mRideViewModel.getSectionsOfPassenger(passengers.get(i).getMPassengerId()).observe(this, sectionsOfPassenger -> {
                    Double totalFee = 0.0;
                    for(int j=0; j<sectionsOfPassenger.get(0).sections.size(); j++) {
                        totalFee += sectionsOfPassenger.get(0).sections.get(j).getMPassengerFee();
                    }

                    totalFee = Math.round(totalFee*100.0)/100.0;
                    passengers.get(finalI).setMTotalFee(totalFee);
                    mRideViewModel.updatePassenger(passengers.get(finalI));
                });
            }

            mAdapter.setPassengerList(passengers);
        });

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}