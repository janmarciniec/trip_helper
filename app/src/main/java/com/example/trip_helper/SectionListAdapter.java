package com.example.trip_helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_helper.entities.Section;
import com.example.trip_helper.entities.relations.RideWithSections;

import java.util.List;

public class SectionListAdapter extends RecyclerView.Adapter<SectionListAdapter.SectionViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private List<Section> mSectionList;

    //interfejs do zaimplementowania w głównej aktywności - będzie informował ją o tym, jaki element wybrano
    interface OnItemClickListener {
        void onItemClick(Section section);
    }

    //w tej zmiennej będziemy przechowywać referencję do listenera (w naszym przypadku aktywności),
    //który będzie informowany o kliknięciu wiesza)
    private SectionListAdapter.OnItemClickListener mOnItemClickListener;

    //w odróżnieniu od lab1c, adapter nie otrzymuje listy elementów jako parametru konstruktowa
    //w momencie tworzenia obiektu adaptera lista nie może być dostępna
    public SectionListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mSectionList = null;

        //rzutowanie kontekstu na OnItemClickListener i zapisanie w polu mOnItemClickListener
        //należy zapewnić obsługę wyjątku ClassCastException
        try
        {
            mOnItemClickListener = (SectionListAdapter.OnItemClickListener) context;
        }
        catch (ClassCastException c)
        {}

    }

    //tworzy główny element Layout i tworzy pojemnik (holder) dla danego wiersza
    @NonNull
    @Override
    public SectionListAdapter.SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //utworzenie layoutu wiersza na podstawie XMLa
        View section_row = mLayoutInflater.inflate(R.layout.section_row, null);
        //zwrócenie nowego obiektu holdera
        return new SectionListAdapter.SectionViewHolder(section_row);
    }

    //wypełnia wiersz przechowywany w pojemniku danymi dla określonego wiersza
    @Override
    public void onBindViewHolder(@NonNull SectionListAdapter.SectionViewHolder holder, int position) {
        Section current = mSectionList.get(position);
        holder.textViewSection.setText(current.getMOrigin() + " - " + current.getMDestination());
    }

    //zwraca liczbę elementów
    @Override
    public int getItemCount() {
        //w momencie tworzenia obiektu adaptera lista nie może być dostępna
        if(mSectionList != null)
            return mSectionList.size();
        return 0;
    }

    //ponieważ dane wyświetlane na liście będą się zmieniały, ta metoda umożliwia aktualizację
    //danych w adapterze (i w konserwacji) wyświetlanych w RecyclerView
    public void setSectionList(List<Section> sectionList) {
        this.mSectionList = sectionList;
        notifyDataSetChanged();
    }

    //pojemnik przechowujący referencje do potrzebnych elementów wiersza
    //nadaje się też jako obiekt implementujący listenery - każdy wiersz ma własny holder
    class SectionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView textViewSection;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSection = itemView.findViewById(R.id.textViewSection);

            //itemView to główny element wiersza listy (dziedziczony z klasy ViewHolder)
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //odczytanie numeru klikniętego wiersza
            int adapterPosition = getLayoutPosition();
            //powiadomienie aktywności (mOnItemClickListener) jaki element został wybrany
            Section section = mSectionList.get(adapterPosition);
            mOnItemClickListener.onItemClick(section);
        }
    }

    public Section getElementAtPosition(int adapterPosition) {
        return mSectionList.get(adapterPosition);
    }
}