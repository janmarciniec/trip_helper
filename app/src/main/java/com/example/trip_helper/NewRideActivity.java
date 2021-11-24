package com.example.trip_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride);

        EditText editTextRideName = findViewById(R.id.editTextRideName);
        EditText editTextFuelConsumption = findViewById(R.id.editTextFuelConsumption);
        EditText editTextFuelPrice = findViewById(R.id.editTextFuelPrice);
        Button buttonSaveNewRide = findViewById(R.id.buttonSaveNewRide);
        Button buttonCancelNewRide = findViewById(R.id.buttonCancelNewRide);

        Bundle bundle = getIntent().getExtras();
        long id = bundle.getLong("id");
        String name = bundle.getString("name");
        String fuelConsumption = bundle.getString("fuelConsumption");
        String fuelPrice = bundle.getString("fuelPrice");

        if(id == 0) {
            this.setTitle(R.string.newRideTitle);
        } else {
            this.setTitle(R.string.editRideTitle);
        }

        editTextRideName.setText(name);
        editTextFuelConsumption.setText(fuelConsumption);
        editTextFuelPrice.setText(fuelPrice);

        buttonSaveNewRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextRideName.getText().toString();
                String fuelConsumption = editTextFuelConsumption.getText().toString();
                String fuelPrice = editTextFuelPrice.getText().toString();

                if(name.length()==0)
                    editTextRideName.setError(getString(R.string.emptyFieldError));
                else if(fuelConsumption.length()==0)
                    editTextFuelConsumption.setError(getString(R.string.emptyFieldError));
                else if(fuelPrice.length()==0)
                    editTextFuelPrice.setError(getString(R.string.emptyFieldError));
                else
                {
                    //przekazanie wyników do RideActivity
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", id);
                    bundle.putString("name", name);
                    bundle.putString("fuelConsumption", fuelConsumption);
                    bundle.putString("fuelPrice", fuelPrice);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        buttonCancelNewRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}