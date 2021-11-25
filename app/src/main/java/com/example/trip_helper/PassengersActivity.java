package com.example.trip_helper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_helper.entities.Passenger;

public class PassengersActivity extends AppCompatActivity implements PassengerListAdapter.OnItemClickListener {

    public final static int UPDATE_PASSENGER_REQUEST_CODE = 1;
    public final static int NEW_PASSENGER_REQUEST_CODE = 2;

    private RideViewModel mRideViewModel;
    private PassengerListAdapter mAdapter;
    private long rideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers);

        Bundle bundle = getIntent().getExtras();
        rideId = bundle.getLong("rideId");

        this.setTitle(R.string.editPassengersTitle);

        final Button buttonNewPassenger = findViewById(R.id.buttonNewPassenger);

        // ustawienie adaptera na liście, ustawienie layoutu elementów listy
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new PassengerListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // odczytanie modelu widoku z dostawcy
        mRideViewModel = new ViewModelProvider(this).get(RideViewModel.class);

        // gdy zmienią się dane w obiekcie live data w modelu widoku zostanie wywołana metoda ustawiająca zmienioną listę elementów w adapterze
        mRideViewModel.getRideWithPassengers(rideId).observe(this, passengers -> {
            mAdapter.setPassengerList(passengers);
        });

        buttonNewPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassengersActivity.this, NewPassengerActivity.class);
                intent.putExtra("id", "");
                intent.putExtra("name", "");
                intent.putExtra("rideId", rideId);
                startActivityForResult(intent, NEW_PASSENGER_REQUEST_CODE);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PassengersActivity.this);
                        builder.setTitle(R.string.deletePassengerDialogTitle);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.dialogDeleteButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //odczytanie pozycji przeciągniętego elementu
                                int adapterPosition = viewHolder.getLayoutPosition();
                                Passenger passenger = mAdapter.getElementAtPosition(adapterPosition);
                                mRideViewModel.deletePassenger(passenger);
                                Toast.makeText(PassengersActivity.this, R.string.deletedPassengerToast, Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton(R.string.dialogCancelButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ponowne załadowanie aktywności, żeby usunięty wiersz z RecyclerView pojawił się z powrotem
                                finish();
                                startActivity(getIntent());
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_PASSENGER_REQUEST_CODE && resultCode==RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            long id = bundle.getLong("id");
            String name = bundle.getString("name");
            long rideId = bundle.getLong("rideId");
            Passenger passenger = new Passenger(name, rideId);
            mRideViewModel.insertPassenger(passenger);
        }

        if(requestCode == UPDATE_PASSENGER_REQUEST_CODE && resultCode==RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            long id = bundle.getLong("id");
            String name = bundle.getString("name");
            long rideId = bundle.getLong("rideId");
            Passenger passenger = new Passenger(id, name, rideId);
            mRideViewModel.updatePassenger(passenger);
        }
    }

    @Override
    public void onItemClick(Passenger passenger) {
        Intent intent = new Intent(PassengersActivity.this, NewPassengerActivity.class);
        intent.putExtra("id", passenger.getMId());
        intent.putExtra("name", passenger.getMName());
        intent.putExtra("rideId", passenger.getMRideId());
        startActivityForResult(intent, UPDATE_PASSENGER_REQUEST_CODE);
    }
}
