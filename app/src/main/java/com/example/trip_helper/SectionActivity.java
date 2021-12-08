package com.example.trip_helper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Section;
import com.example.trip_helper.entities.relations.SectionWithPassengers;

import java.util.ArrayList;
import java.util.List;

public class SectionActivity extends AppCompatActivity {

    private RideViewModel mRideViewModel;
    private PassengerOfSectionListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        TextView textViewOriginValue = findViewById(R.id.textViewOriginValue);
        TextView textViewDestinationValue = findViewById(R.id.textViewDestinationValue);
        TextView textViewDistanceValue = findViewById(R.id.textViewDistanceValue);
        Button buttonReturn = findViewById(R.id.buttonReturn);
        Button buttonDeleteSection = findViewById(R.id.buttonDeleteSection);

        Bundle bundle = getIntent().getExtras();
        Section section = bundle.getParcelable("section");
        long sectionId = section.getMSectionId();
        String origin = section.getMOrigin();
        String destination = section.getMDestination();
        String distance = section.getMDistance().toString();

        this.setTitle("Odcinek: " + origin + " - " + destination);

        textViewOriginValue.setText(origin);
        textViewDestinationValue.setText(destination);
        textViewDistanceValue.setText(distance);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new PassengerOfSectionListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // odczytanie modelu widoku z dostawcy
        mRideViewModel = new ViewModelProvider(this).get(RideViewModel.class);

        // gdy zmienią się dane w obiekcie live data w modelu widoku zostanie wywołana metoda ustawiająca zmienioną listę elementów w adapterze
        mRideViewModel.getPassengersOfSection(sectionId).observe(this, passengers -> {
            mAdapter.setPassengerList(passengers.get(0).passengers);
        });

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonDeleteSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SectionActivity.this);
                builder.setTitle(R.string.deleteSectionDialogTitle);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.dialogDeleteButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRideViewModel.deleteSection(section);
                    }
                });

                builder.setNegativeButton(R.string.dialogCancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}