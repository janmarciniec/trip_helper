package com.example.trip_helper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trip_helper.entities.Section;

public class NewSectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_section);

        EditText editTextOrigin = findViewById(R.id.editTextOrigin);
        EditText editTextDestination = findViewById(R.id.editTextDestination);
        EditText editTextDistance = findViewById(R.id.editTextDistance);
        Button buttonSaveSection = findViewById(R.id.buttonSaveSection);
        Button buttonCancelSection = findViewById(R.id.buttonCancelSection);
        Button buttonDeleteSection = findViewById(R.id.buttonDeleteSection);

        Bundle bundle = getIntent().getExtras();
        long id = bundle.getLong("id");
        String origin = bundle.getString("origin");
        String destination = bundle.getString("destination");
        String distance = bundle.getString("distance");
        Long rideId = bundle.getLong("rideId");

        if(id == 0) {
            this.setTitle(R.string.newSectionTitle);
            buttonDeleteSection.setVisibility(View.INVISIBLE);
        } else {
            this.setTitle(R.string.editSectionTitle);
            buttonDeleteSection.setVisibility(View.VISIBLE);
        }

        editTextOrigin.setText(origin);
        editTextDestination.setText(destination);
        editTextDistance.setText(distance);

        buttonSaveSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String origin = editTextOrigin.getText().toString();
                String destination = editTextDestination.getText().toString();
                String distance = editTextDistance.getText().toString();

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

        buttonDeleteSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewSectionActivity.this);
                builder.setTitle(R.string.deleteSectionDialogTitle);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.dialogDeleteButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NewSectionActivity.this, R.string.deletedSectionToast, Toast.LENGTH_SHORT).show();
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