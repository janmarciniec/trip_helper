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
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.trip_helper.entities.Ride;
import com.example.trip_helper.entities.Section;

public class RideActivity extends AppCompatActivity implements SectionListAdapter.OnItemClickListener {

    public final static int UPDATE_RIDE_REQUEST_CODE = 1;
    public final static int NEW_SECTION_REQUEST_CODE = 2;
    public final static int UPDATE_SECTION_REQUEST_CODE = 3;
    public final static int EDIT_PASSENGERS_REQUEST_CODE = 4;

    private RideViewModel mRideViewModel;
    private SectionListAdapter mAdapter;
    private Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        Bundle bundle = getIntent().getExtras();
        ride = bundle.getParcelable("ride");

        this.setTitle(ride.getMName());

        final Button buttonNewSection = findViewById(R.id.buttonNewSection);

        // ustawienie adaptera na liście, ustawienie layoutu elementów listy
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new SectionListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // odczytanie modelu widoku z dostawcy
        mRideViewModel = new ViewModelProvider(this).get(RideViewModel.class);

        // gdy zmienią się dane w obiekcie live data w modelu widoku zostanie wywołana metoda ustawiająca zmienioną listę elementów w adapterze
        mRideViewModel.getRideWithSections(ride.getMId()).observe(this, sections -> {
            mAdapter.setSectionList(sections);
        });

        buttonNewSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RideActivity.this, NewSectionActivity.class);
                intent.putExtra("id", "");
                intent.putExtra("origin", "");
                intent.putExtra("destination", "");
                intent.putExtra("distance", "");
                intent.putExtra("rideId", ride.getMId());
                startActivityForResult(intent, NEW_SECTION_REQUEST_CODE);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(RideActivity.this);
                        builder.setTitle(R.string.deleteSectionDialogTitle);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.dialogDeleteButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //odczytanie pozycji przeciągniętego elementu
                                int adapterPosition = viewHolder.getLayoutPosition();
                                Section section = mAdapter.getElementAtPosition(adapterPosition);
                                mRideViewModel.deleteSection(section);
                                Toast.makeText(RideActivity.this, R.string.deletedSectionToast, Toast.LENGTH_SHORT).show();
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

        if(requestCode == UPDATE_RIDE_REQUEST_CODE && resultCode==RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            long id = bundle.getLong("id");
            String name = bundle.getString("name");
            String fuelConsumption = bundle.getString("fuelConsumption");
            Double fuelConsumptionD = Double.parseDouble(fuelConsumption);
            String fuelPrice = bundle.getString("fuelPrice");
            Double fuelPriceD = Double.parseDouble(fuelPrice);
            ride.setMName(name);
            ride.setMFuelConsumption(fuelConsumptionD);
            ride.setMFuelPrice(fuelPriceD);
            mRideViewModel.updateRide(ride);
            this.setTitle(ride.getMName());
        }

        if(requestCode == NEW_SECTION_REQUEST_CODE && resultCode==RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            String origin = bundle.getString("origin");
            String destination = bundle.getString("destination");
            String distance = bundle.getString("distance");
            Double distanceD = Double.parseDouble(distance);
            Long rideId = bundle.getLong("rideId");
            Section section = new Section(origin, destination, distanceD, rideId);
            mRideViewModel.insertSection(section);
        }

        if(requestCode == UPDATE_SECTION_REQUEST_CODE && resultCode==RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            long id = bundle.getLong("id");
            String origin = bundle.getString("origin");
            String destination = bundle.getString("destination");
            String distance = bundle.getString("distance");
            Double distanceD = Double.parseDouble(distance);
            Long rideId = bundle.getLong("rideId");
            Section section = new Section(id, origin, destination, distanceD, rideId);
            mRideViewModel.updateSection(section);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //tworzenie menu w aplikacji na podstawie XMLa
        getMenuInflater().inflate(R.menu.ride, menu);

        // ustawienie czerwonego koloru opcji
        MenuItem itemDeleteRide = menu.getItem(2);
        SpannableString spannableString = new SpannableString((itemDeleteRide.getTitle().toString()));
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, spannableString.length(), 0);
        itemDeleteRide.setTitle(spannableString);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.itemEditRide) {
            //obsługa opcji
            Intent intent = new Intent(RideActivity.this, NewRideActivity.class);
            intent.putExtra("id", ride.getMId());
            intent.putExtra("name", ride.getMName());
            Double fuelConsumption = ride.getMFuelConsumption();
            String fuelConsumptionS = Double.toString(fuelConsumption);
            intent.putExtra("fuelConsumption", fuelConsumptionS);
            Double fuelPrice = ride.getMFuelPrice();
            //String fuelPriceS = String.format("%.2f", fuelPrice);
            String fuelPriceS = Double.toString(fuelPrice);
            intent.putExtra("fuelPrice", fuelPriceS);
            startActivityForResult(intent, UPDATE_RIDE_REQUEST_CODE);
            //zwrócenie true = zakończenie obsługi opcji
            return true;
        }

        if(id == R.id.itemEditPassengers) {
            //obsługa opcji
            Intent intent = new Intent(RideActivity.this, PassengersActivity.class);
            intent.putExtra("rideId", ride.getMId());
            startActivityForResult(intent, EDIT_PASSENGERS_REQUEST_CODE);
            //zwrócenie true = zakończenie obsługi opcji
            return true;
        }

        if(id == R.id.itemDeleteRide) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RideActivity.this);
            builder.setTitle(R.string.deleteRideDialogTitle);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.dialogDeleteButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mRideViewModel.deleteRide(ride);
                    Intent intent = new Intent(RideActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(RideActivity.this, R.string.deletedRideToast, Toast.LENGTH_SHORT).show();
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
    public void onItemClick(Section section) {
        Intent intent = new Intent(RideActivity.this, NewSectionActivity.class);
        intent.putExtra("id", section.getMId());
        intent.putExtra("origin", section.getMOrigin());
        intent.putExtra("destination", section.getMDestination());
        String distanceS = Double.toString(section.getMDistance());
        intent.putExtra("distance", distanceS);
        intent.putExtra("rideId", ride.getMId());
        startActivityForResult(intent, UPDATE_SECTION_REQUEST_CODE);
    }


}