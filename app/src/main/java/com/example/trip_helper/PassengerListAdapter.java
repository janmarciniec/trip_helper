package com.example.trip_helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_helper.entities.Passenger;
import com.example.trip_helper.entities.relations.RideWithPassengers;

import java.util.List;

public class PassengerListAdapter extends RecyclerView.Adapter<PassengerListAdapter.PassengerViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private List<Passenger> mPassengerList;

    //interfejs do zaimplementowania w głównej aktywności - będzie informował ją o tym, jaki element wybrano
    interface OnItemClickListener {
        void onItemClick(Passenger passenger);
    }

    //w tej zmiennej będziemy przechowywać referencję do listenera (w naszym przypadku aktywności),
    //który będzie informowany o kliknięciu wiesza)
    private PassengerListAdapter.OnItemClickListener mOnItemClickListener;

    //w odróżnieniu od lab1c, adapter nie otrzymuje listy elementów jako parametru konstruktowa
    //w momencie tworzenia obiektu adaptera lista nie może być dostępna
    public PassengerListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mPassengerList = null;

        //rzutowanie kontekstu na OnItemClickListener i zapisanie w polu mOnItemClickListener
        //należy zapewnić obsługę wyjątku ClassCastException
        try
        {
            mOnItemClickListener = (PassengerListAdapter.OnItemClickListener) context;
        }
        catch (ClassCastException c)
        {}

    }

    //tworzy główny element Layout i tworzy pojemnik (holder) dla danego wiersza
    @NonNull
    @Override
    public PassengerListAdapter.PassengerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //utworzenie layoutu wiersza na podstawie XMLa
        View passenger_row = mLayoutInflater.inflate(R.layout.passenger_row, null);
        //zwrócenie nowego obiektu holdera
        return new PassengerListAdapter.PassengerViewHolder(passenger_row);
    }

    //wypełnia wiersz przechowywany w pojemniku danymi dla określonego wiersza
    @Override
    public void onBindViewHolder(@NonNull PassengerListAdapter.PassengerViewHolder holder, int position) {
        Passenger current = mPassengerList.get(position);
        holder.textViewPassenger.setText(current.getMName());
    }

    //zwraca liczbę elementów
    @Override
    public int getItemCount() {
        //w momencie tworzenia obiektu adaptera lista nie może być dostępna
        if(mPassengerList != null)
            return mPassengerList.size();
        return 0;
    }

    //ponieważ dane wyświetlane na liście będą się zmieniały, ta metoda umożliwia aktualizację
    //danych w adapterze (i w konserwacji) wyświetlanych w RecyclerView
    public void setPassengerList(List<Passenger> passengerList) {
        this.mPassengerList = passengerList;
        notifyDataSetChanged();
    }

    //pojemnik przechowujący referencje do potrzebnych elementów wiersza
    //nadaje się też jako obiekt implementujący listenery - każdy wiersz ma własny holder
    class PassengerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView textViewPassenger;

        public PassengerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPassenger = itemView.findViewById(R.id.textViewPassenger);

            //itemView to główny element wiersza listy (dziedziczony z klasy ViewHolder)
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //odczytanie numeru klikniętego wiersza
            int adapterPosition = getLayoutPosition();
            //powiadomienie aktywności (mOnItemClickListener) jaki element został wybrany
            Passenger passenger = mPassengerList.get(adapterPosition);
            mOnItemClickListener.onItemClick(passenger);
        }
    }

    public Passenger getElementAtPosition(int adapterPosition) {
        return mPassengerList.get(adapterPosition);
    }
}
