package com.example.trip_helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.Ride;
import com.example.trip_helper.entities.Section;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ride.class, Section.class, Passenger.class}, version = 1, exportSchema = false)
public abstract class RideRoomDatabase extends RoomDatabase {
    //abstrakcyjna metoda zwracająca DAO
    public abstract RideDao rideDao();
    //public abstract SectionDao sectionDao();

    //implementacja singletona
    private static volatile RideRoomDatabase INSTANCE;

    static RideRoomDatabase getDatabase(final Context context) {
        //tworzymy nowy obiekt tylko, gdy żaden nie istnieje
        if(INSTANCE == null)
        {
            synchronized (RideRoomDatabase.class) {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RideRoomDatabase.class, "trip_helper_db")
                            //ustawienie obiektu obsługującego zdarzenia związane z bazą (kod poniżej)
                            .addCallback(sRoomDatabaseCallback)
                            //najprostsza migracja - skasowanie i utworzenie bazy od nowa
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    //usługa wykonawcza do wykonywania zadań w osobnym wątku
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //obiekt obsługujący wywołania zwrotne (call backs) związane ze zdarzeniami bazy danych, np. onCreate, onOpen
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        //uruchamiane przy tworzeniu bazy (pierwsze uruchomienie aplikacji, gdy baza nie istnieje
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //wykonanie operacji w osobnym wątku
            //parametrem metody execute() jest obiekt implementujący interfejs Runnable, może być zastąpiony wyrażeniem lambda
            databaseWriteExecutor.execute(() -> {
                RideDao rideDao = INSTANCE.rideDao();
                //SectionDao sectionDao = INSTANCE.sectionDao();
                //tworzenie elementów (obiektów klasy Element) i dodawanie ich do bazy za pomocą metody insertRide() z obiektu dao
                //tutaj możemy określić początkową zawartość bazy danych
//                Ride ride1 = new Ride("Lublin - Warszawa",6.0, 5.80);
//                dao.insertRide(ride1);
//                Ride ride2 = new Ride("Lublin - Krasnystaw",5.7, 5.46);
//                dao.insertRide(ride2);
//                Ride ride3 = new Ride("Warszawa - Krasnystaw",5.8, 5.75);
//                dao.insertRide(ride3);
            });
        }
    };
}
