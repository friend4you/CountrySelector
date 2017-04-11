package com.example.vlada.countryselector.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vlada.countryselector.CityActivity;
import com.example.vlada.countryselector.R;

import java.util.ArrayList;
import java.util.List;


public class CitiesAdapter  extends Adapter<ViewHolder> {

    private List<String> cities;

    public CitiesAdapter(){
        this.cities = new ArrayList<>();
    }

    public void add(List<String> itemList){
        cities.clear();
        cities.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((CityViewHolder) holder).bind(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class CityViewHolder extends ViewHolder{
    private final TextView title;

    public CityViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.city);

        itemView.setOnClickListener(v -> {
            Intent intent = CityActivity.getIntent(title.getText().toString());
            itemView.getContext().startActivity(intent);
        });
    }
    void bind(String city){
        title.setText(city);
    }
}
}
