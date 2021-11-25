package com.example.trip_helper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPassengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_passenger);

        EditText editTextPassengerName = findViewById(R.id.editTextPassengerName);
        Button buttonSavePassenger = findViewById(R.id.buttonSavePassenger);
        Button buttonCancelPassenger = findViewById(R.id.buttonCancelPassenger);
        Button buttonDeletePassenger = findViewById(R.id.buttonDeletePassenger);

        Bundle bundle = getIntent().getExtras();
        long id = bundle.getLong("id");
        String name = bundle.getString("name");
        Long rideId = bundle.getLong("rideId");

        if(id == 0) {
            this.setTitle(R.string.newPassengerTitle);
            buttonDeletePassenger.setVisibility(View.INVISIBLE);
        } else {
            this.setTitle(R.string.editPassengerTitle);
            buttonDeletePassenger.setVisibility(View.VISIBLE);
        }

        editTextPassengerName.setText(name);

        buttonSavePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextPassengerName.getText().toString();

                if(name.length()==0) {
                    editTextPassengerName.setError(getString(R.string.emptyFieldError));
                } else{
                    //przekazanie wyników do PassengersActivity
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", id);
                    bundle.putString("name", name);
                    bundle.putLong("rideId", rideId);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        buttonCancelPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonDeletePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewPassengerActivity.this);
                builder.setTitle(R.string.deletePassengerDialogTitle);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.dialogDeleteButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NewPassengerActivity.this, R.string.deletedPassengerToast, Toast.LENGTH_SHORT).show();
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