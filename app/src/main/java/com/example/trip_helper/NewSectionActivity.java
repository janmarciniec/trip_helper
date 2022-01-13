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
import com.example.trip_helper.entities.Ride;
import com.example.trip_helper.entities.Section;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NewSectionActivity extends AppCompatActivity {

    private RideViewModel mRideViewModel;
    EditText editTextOrigin;
    EditText editTextDestination;
    EditText editTextDistance;
    private ArrayList<CheckBox> checkBoxList = new ArrayList<>();
    private ArrayList<Long> passengerIdList = new ArrayList<>();
    private ArrayList<String> passengersOfSection = new ArrayList<>();
//    String sType;
//    double lat1 = 0;
//    double long1 = 0;
//    double lat2 = 0;
//    double long2 = 0;
//    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_section);

        editTextOrigin = findViewById(R.id.editTextOrigin);
        editTextDestination = findViewById(R.id.editTextDestination);
        editTextDistance = findViewById(R.id.editTextDistance);
        Button buttonSaveSection = findViewById(R.id.buttonSaveSection);
        Button buttonCancelSection = findViewById(R.id.buttonCancelSection);

        //Places.initialize(getApplicationContext(), "AIzaSyBnw0iwVx4NPt-JTNrLU-Zf8HhiAUXsshs");

//        editTextOrigin.setFocusable(false);
//        editTextOrigin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sType = "source";
//                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
//                Intent intent = new Autocomplete.IntentBuilder(
//                        AutocompleteActivityMode.OVERLAY, fields
//                ).build(NewSectionActivity.this);
//
//                startActivityForResult(intent, 100);
//            }
//        });
//
//        editTextDestination.setFocusable(false);
//        editTextDestination.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sType = "destination";
//                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
//                Intent intent = new Autocomplete.IntentBuilder(
//                        AutocompleteActivityMode.OVERLAY, fields
//                ).build(NewSectionActivity.this);
//
//                startActivityForResult(intent, 100);
//            }
//        });
//
//        editTextDistance.setFocusable(false);
//        editTextDistance.setText("0.0 km");

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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 100 && resultCode==RESULT_OK) {
//            Place place = Autocomplete.getPlaceFromIntent(data);
//
//            if(sType.equals("source")) {
//                flag++;
//                editTextOrigin.setText(place.getAddress());
//                String sSource = String.valueOf(place.getLatLng());
//                sSource = sSource.replaceAll("lat/lng: ", "");
//                sSource = sSource.replace("(", "");
//                sSource = sSource.replace(")", "");
//                String[] split = sSource.split(",");
//                lat1 = Double.parseDouble(split[0]);
//                long1 = Double.parseDouble(split[1]);
//            } else {
//                flag++;
//                editTextDestination.setText(place.getAddress());
//                String sDestination = String.valueOf(place.getLatLng());
//                sDestination = sDestination.replaceAll("lat/lng: ", "");
//                sDestination = sDestination.replace("(", "");
//                sDestination = sDestination.replace(")", "");
//                String[] split = sDestination.split(",");
//                lat2 = Double.parseDouble(split[0]);
//                long2 = Double.parseDouble(split[1]);
//            }
//
//            if(flag >= 2) {
//                // calculate distance
//                distance(lat1, long1, lat2, long2);
//            }
//        } else if(requestCode == AutocompleteActivity.RESULT_ERROR) {
//            Status status = Autocomplete.getStatusFromIntent(data);
//            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void distance(double lat1, double long1, double lat2, double long2) {
//        double longDiff = long1 - long2;
//        double distance = Math.sin(toRadian(lat1)) * Math.sin(toRadian(lat2)) + Math.cos(toRadian(lat1)) * Math.cos(toRadian(lat2)) * Math.cos(toRadian(longDiff));
//        distance = Math.acos(distance);
//        distance = toDegree(distance);
//
//        // convert to kilometers
//        distance = distance * 1.609344;
//
//        editTextDistance.setText(String.format(Locale.US, "%2f Kilometers", distance));
//    }
//
//    // converts radian to degree
//    private double toDegree(double distance) {
//        return (distance * 180.0 / Math.PI);
//    }
//
//    // converts degree to radian
//    private double toRadian(double lat1) {
//        return (lat1 * Math.PI / 180.0);
//    }
}