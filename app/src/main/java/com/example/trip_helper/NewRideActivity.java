package com.example.trip_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride);

        EditText editTextRideName = findViewById(R.id.editTextRideName);
        EditText editTextFuelConsumption = findViewById(R.id.editTextFuelConsumption);
        EditText editTextFuelPrice = findViewById(R.id.editTextFuelPrice);
        TextView textViewNumOfPassengers = findViewById(R.id.textViewNumOfPassengers);
        EditText editTextNumOfPassengers = findViewById(R.id.editTextNumOfPassengers);
        Button buttonSaveNewRide = findViewById(R.id.buttonSaveNewRide);
        Button buttonCancelNewRide = findViewById(R.id.buttonCancelNewRide);

        Bundle bundle = getIntent().getExtras();
        long id = bundle.getLong("id");
        String name = bundle.getString("name");
        String fuelConsumption = bundle.getString("fuelConsumption");
        String fuelPrice = bundle.getString("fuelPrice");
        String numOfPassengers = bundle.getString("numOfPassengers");

        if(id == 0) {
            this.setTitle(R.string.newRideTitle);
            textViewNumOfPassengers.setVisibility(View.VISIBLE);
            editTextNumOfPassengers.setVisibility(View.VISIBLE);
            buttonSaveNewRide.setText(R.string.buttonSaveNewRide);
        } else {
            this.setTitle(R.string.editRideTitle);
            textViewNumOfPassengers.setVisibility(View.GONE);
            editTextNumOfPassengers.setVisibility(View.GONE);
            buttonSaveNewRide.setText(R.string.buttonSave);
        }

        editTextRideName.setText(name);
        editTextFuelConsumption.setText(fuelConsumption);
        editTextFuelPrice.setText(fuelPrice);
        editTextNumOfPassengers.setText(numOfPassengers);

        buttonSaveNewRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextRideName.getText().toString();
                String fuelConsumption = editTextFuelConsumption.getText().toString();
                String fuelPrice = editTextFuelPrice.getText().toString();
                String numOfPassengers = editTextNumOfPassengers.getText().toString();

                if(name.length()==0)
                    editTextRideName.setError(getString(R.string.emptyFieldError));
                else if(fuelConsumption.length()==0)
                    editTextFuelConsumption.setError(getString(R.string.emptyFieldError));
                else if(fuelPrice.length()==0)
                    editTextFuelPrice.setError(getString(R.string.emptyFieldError));
                else if(numOfPassengers.length()==0)
                    editTextNumOfPassengers.setError(getString(R.string.emptyFieldError));
                else
                {
                    //przekazanie wyników do MainActivity (podczas dodawania) lub do RideActivity (podczas edycji)
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", id);
                    bundle.putString("name", name);
                    bundle.putString("fuelConsumption", fuelConsumption);
                    bundle.putString("fuelPrice", fuelPrice);
                    bundle.putString("numOfPassengers", numOfPassengers);
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