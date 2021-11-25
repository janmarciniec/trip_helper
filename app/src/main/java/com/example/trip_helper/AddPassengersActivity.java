package com.example.trip_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.trip_helper.entities.Ride;

import java.util.ArrayList;
import java.util.List;

public class AddPassengersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passengers);

        this.setTitle(R.string.newRideTitle);

        Button buttonSavePassengers = findViewById(R.id.buttonSavePassengers);
        Button buttonCancelPassengers = findViewById(R.id.buttonCancelPassengers);

        Bundle bundle = getIntent().getExtras();
        String numOfPassengers = bundle.getString("numOfPassengers");
        Integer numOfPassengersI = Integer.parseInt(numOfPassengers);
        long rideId = bundle.getLong("rideId");

        LinearLayout editTextContainer = findViewById(R.id.editTextContainer);

        ArrayList<EditText> editTextList = new ArrayList<>();
        ArrayList<String> passengerNameList = new ArrayList<>();

        // Create EditTexts
        for(int i=0; i<numOfPassengersI; i++) {
            EditText editTextPassengerName = new EditText(this);
            editTextPassengerName.setHint("Pasażer nr " + (i+1));
            editTextPassengerName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            editTextPassengerName.setPadding(20, 20, 20, 20);

            // Add EditText to LinearLayout
            if (editTextContainer != null) {
                editTextContainer.addView(editTextPassengerName);
            }

            editTextList.add(editTextPassengerName);
        }

        buttonSavePassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<editTextList.size(); i++) {
                    String passengerName = editTextList.get(i).getText().toString();

                    if(passengerName.length() == 0) {
                        editTextList.get(i).setError(getString(R.string.emptyFieldError));
                    }

                    passengerNameList.add(passengerName);
                }

                //przekazanie wyników do MainActivity
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("passengerNameList", passengerNameList);
                bundle.putLong("rideId", rideId);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        buttonCancelPassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}