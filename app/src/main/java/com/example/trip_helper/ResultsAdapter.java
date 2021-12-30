package com.example.trip_helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_helper.entities.Passenger;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.PassengerViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private List<Passenger> mPassengerList;

    interface OnItemClickListener {
        void onItemClick(Passenger passenger);
    }

    public ResultsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mPassengerList = null;
    }

    @NonNull
    @Override
    public ResultsAdapter.PassengerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View result_row = mLayoutInflater.inflate(R.layout.result_row, null);
        return new ResultsAdapter.PassengerViewHolder(result_row);
    }

    //wypełnia wiersz przechowywany w pojemniku danymi dla określonego wiersza
    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.PassengerViewHolder holder, int position) {
        Passenger current = mPassengerList.get(position);
        holder.textViewPassenger.setText(current.getMName() + ":");
        holder.textViewCharge.setText(current.getMTotalFee() + " zł");
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
    class PassengerViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewPassenger;
        private final TextView textViewCharge;

        public PassengerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPassenger = itemView.findViewById(R.id.textViewPassenger);
            textViewCharge = itemView.findViewById(R.id.textViewCharge);
        }

    }

    public Passenger getElementAtPosition(int adapterPosition) {
        return mPassengerList.get(adapterPosition);
    }
}
