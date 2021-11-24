package com.example.trip_helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_helper.entities.Ride;

import java.util.List;

public class RideListAdapter extends RecyclerView.Adapter<RideListAdapter.RideViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private List<Ride> mRideList;

    //interfejs do zaimplementowania w głównej aktywności - będzie informował ją o tym, jaki element wybrano
    interface OnItemClickListener {
        void onItemClick(Ride ride);
    }

    //w tej zmiennej będziemy przechowywać referencję do listenera (w naszym przypadku aktywności),
    //który będzie informowany o kliknięciu wiesza)
    private OnItemClickListener mOnItemClickListener;

    //w odróżnieniu od lab1c, adapter nie otrzymuje listy elementów jako parametru konstruktowa
    //w momencie tworzenia obiektu adaptera lista nie może być dostępna
    public RideListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mRideList = null;

        //rzutowanie kontekstu na OnItemClickListener i zapisanie w polu mOnItemClickListener
        //należy zapewnić obsługę wyjątku ClassCastException
        try
        {
            mOnItemClickListener = (OnItemClickListener) context;
        }
        catch (ClassCastException c)
        {}

    }

    //tworzy główny element Layout i tworzy pojemnik (holder) dla danego wiersza
    @NonNull
    @Override
    public RideListAdapter.RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //utworzenie layoutu wiersza na podstawie XMLa
        View ride_row = mLayoutInflater.inflate(R.layout.ride_row, null);
        //zwrócenie nowego obiektu holdera
        return new RideViewHolder(ride_row);
    }

    //wypełnia wiersz przechowywany w pojemniku danymi dla określonego wiersza
    @Override
    public void onBindViewHolder(@NonNull RideListAdapter.RideViewHolder holder, int position) {
        Ride current = mRideList.get(position);
        holder.textViewRideName.setText(current.getMName());
    }

    //zwraca liczbę elementów
    @Override
    public int getItemCount() {
        //w momencie tworzenia obiektu adaptera lista nie może być dostępna
        if(mRideList != null)
            return mRideList.size();
        return 0;
    }

    //ponieważ dane wyświetlane na liście będą się zmieniały, ta metoda umożliwia aktualizację
    //danych w adapterze (i w konserwacji) wyświetlanych w RecyclerView
    public void setRideList(List<Ride> rideList) {
        this.mRideList = rideList;
        notifyDataSetChanged();
    }

    //pojemnik przechowujący referencje do potrzebnych elementów wiersza
    //nadaje się też jako obiekt implementujący listenery - każdy wiersz ma własny holder
    class RideViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        private final TextView textViewRideName;

        public RideViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRideName = itemView.findViewById(R.id.textViewRideName);

            //itemView to główny element wiersza listy (dziedziczony z klasy ViewHolder)
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //odczytanie numeru klikniętego wiersza
            int adapterPosition = getLayoutPosition();
            //powiadomienie aktywności (mOnItemClickListener) jaki element został wybrany
            Ride ride = mRideList.get(adapterPosition);
            mOnItemClickListener.onItemClick(ride);
        }

    }

    public Ride getElementAtPosition(int adapterPosition) {
        return mRideList.get(adapterPosition);
    }
}
