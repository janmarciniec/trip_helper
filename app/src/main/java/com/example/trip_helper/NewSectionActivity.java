package com.example.trip_helper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Section;

import java.util.ArrayList;
import java.util.List;

public class NewSectionActivity extends AppCompatActivity {

    private RideViewModel mRideViewModel;
    private ArrayList<CheckBox> checkBoxList = new ArrayList<>();
    private ArrayList<Long> passengerIdList = new ArrayList<>();
    private ArrayList<String> passengersOfSection = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_section);

        EditText editTextOrigin = findViewById(R.id.editTextOrigin);
        EditText editTextDestination = findViewById(R.id.editTextDestination);
        EditText editTextDistance = findViewById(R.id.editTextDistance);
        Button buttonSaveSection = findViewById(R.id.buttonSaveSection);
        Button buttonCancelSection = findViewById(R.id.buttonCancelSection);

        LinearLayout checkboxContainer = findViewById(R.id.checkboxContainer);

        Bundle bundle = getIntent().getExtras();
        long id = bundle.getLong("id");
        String origin = bundle.getString("origin");
        String destination = bundle.getString("destination");
        String distance = bundle.getString("distance");
        Long rideId = bundle.getLong("rideId");

        if(id == 0) {
            this.setTitle(R.string.newSectionTitle);
        } else {
            this.setTitle(R.string.editSectionTitle);
        }

        editTextOrigin.setText(origin);
        editTextDestination.setText(destination);
        editTextDistance.setText(distance);

        mRideViewModel = new ViewModelProvider(this).get(RideViewModel.class);

        mRideViewModel.getRideWithPassengers(rideId).observe(this, passengers -> {
            if(passengers.size() == 0) {
                TextView textView = new TextView(this);
                textView.setText(R.string.textViewNoPassengers);
                textView.setTextSize(18);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                textView.setPadding(20, 20, 20, 20);

                if (checkboxContainer != null) {
                    checkboxContainer.addView(textView);
                }
            } else {
                for (int i = 0; i < passengers.size(); i++) {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(passengers.get(i).getMName());
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox.setPadding(20, 20, 20, 20);

                    // dodanie checkboxa do LinearLayout
                    if (checkboxContainer != null) {
                        checkboxContainer.addView(checkBox);
                    }

                    checkBoxList.add(checkBox);
                    passengerIdList.add(passengers.get(i).getMPassengerId());
                }
            }
        });

        buttonSaveSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String origin = editTextOrigin.getText().toString();
                String destination = editTextDestination.getText().toString();
                String distance = editTextDistance.getText().toString();

                for(int i=0; i<checkBoxList.size(); i++) {
                    if(checkBoxList.get(i).isChecked()) {
                        passengersOfSection.add(Long.toString(passengerIdList.get(i)));
                    }
                }

                if(origin.length()==0)
                    editTextOrigin.setError(getString(R.string.emptyFieldError));
                else if(destination.length()==0)
                    editTextDestination.setError(getString(R.string.emptyFieldError));
                else if(distance.length()==0)
                    editTextDistance.setError(getString(R.string.emptyFieldError));
                else
                {
                    //przekazanie wyników do RideActivity
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", id);
                    bundle.putString("origin", origin);
                    bundle.putString("destination", destination);
                    bundle.putString("distance", distance);
                    bundle.putLong("rideId", rideId);
                    bundle.putStringArrayList("passengersOfSection", passengersOfSection);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        buttonCancelSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}