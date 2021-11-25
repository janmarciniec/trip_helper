package com.example.trip_helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Ride;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RideListAdapter.OnItemClickListener {

    public final static int NEW_RIDE_REQUEST_CODE = 1;
    public final static int UPDATE_RIDE_REQUEST_CODE = 2;
    public final static int ADD_PASSENGERS_REQUEST_CODE = 3;

    private RideViewModel mRideViewModel;
    private RideListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(R.string.mainTitle);

        final FloatingActionButton fabNewRide = findViewById(R.id.fabNewRide);

        // ustawienie adaptera na liście, ustawienie layoutu elementów listy
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new RideListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // odczytanie modelu widoku z dostawcy
        mRideViewModel = new ViewModelProvider(this).get(RideViewModel.class);

        // gdy zmienią się dane w obiekcie live data w modelu widoku zostanie wywołana metoda ustawiająca zmienioną listę elementów w adapterze
        mRideViewModel.getAllRides().observe(this, rides -> {
            mAdapter.setRideList(rides);
        });

        fabNewRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewRideActivity.class);
                intent.putExtra("id", "");
                intent.putExtra("name", "");
                intent.putExtra("fuelConsumption", "");
                intent.putExtra("fuelPrice", "");
                intent.putExtra("numOfPassengers", "");
                startActivityForResult(intent, NEW_RIDE_REQUEST_CODE);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(R.string.deleteRideDialogTitle);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.dialogDeleteButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //odczytanie pozycji przeciągniętego elementu
                                int adapterPosition = viewHolder.getLayoutPosition();
                                Ride ride = mAdapter.getElementAtPosition(adapterPosition);
                                mRideViewModel.deleteRide(ride);
                                Toast.makeText(MainActivity.this, R.string.deletedRideToast, Toast.LENGTH_SHORT).show();
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

        if(requestCode == NEW_RIDE_REQUEST_CODE && resultCode==RESULT_OK) {
            Bundle bundle = data.getExtras();
            String name = bundle.getString("name");
            String fuelConsumption = bundle.getString("fuelConsumption");
            Double fuelConsumptionD = Double.parseDouble(fuelConsumption);
            String fuelPrice = bundle.getString("fuelPrice");
            Double fuelPriceD = Double.parseDouble(fuelPrice);
            String numOfPassengers = bundle.getString("numOfPassengers");

            long rideId;
            // jeśli nie ma żadnych przejazdów, ustaw id=1
            // w przeciwnym wypadku ustaw id o 1 większe niż pierwszego elementu (najpóźniej dodanego)
            if(mRideViewModel.getAllRides().getValue().size() == 0) {
                rideId = 1;
            } else {
                rideId = mRideViewModel.getAllRides().getValue().get(0).getMId() + 1;
            }

            Ride ride = new Ride(rideId, name, fuelConsumptionD, fuelPriceD);
            mRideViewModel.insertRide(ride);

            Intent intent = new Intent(MainActivity.this, AddPassengersActivity.class);
            intent.putExtra("numOfPassengers", numOfPassengers);
            intent.putExtra("rideId", rideId);
            startActivityForResult(intent, ADD_PASSENGERS_REQUEST_CODE);
        }

        if(requestCode == ADD_PASSENGERS_REQUEST_CODE && resultCode==RESULT_OK) {
            Bundle bundle = data.getExtras();
            ArrayList<String> passengerNameList = bundle.getStringArrayList("passengerNameList");
            long rideId = bundle.getLong("rideId");

            for(int i=0; i<passengerNameList.size(); i++) {
                Passenger passenger = new Passenger(passengerNameList.get(i), rideId);
                mRideViewModel.insertPassenger(passenger);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //tworzenie menu w aplikacji na podstawie XMLa
        getMenuInflater().inflate(R.menu.main, menu);

        // ustawienie czerwonego koloru opcji
        MenuItem itemDeleteRide = menu.getItem(0);
        SpannableString spannableString = new SpannableString((itemDeleteRide.getTitle().toString()));
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, spannableString.length(), 0);
        itemDeleteRide.setTitle(spannableString);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.itemDeleteAllRides)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.deleteAllRidesDialogTitle);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.dialogDeleteButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mRideViewModel.deleteAllRides();
                    Toast.makeText(MainActivity.this, R.string.deletedAllRidesToast, Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton(R.string.dialogCancelButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Ride ride) {
        Intent intent = new Intent(MainActivity.this, RideActivity.class);
        intent.putExtra("ride", ride);
        startActivityForResult(intent, 1);
    }
}