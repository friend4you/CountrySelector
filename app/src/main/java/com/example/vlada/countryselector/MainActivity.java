package com.example.vlada.countryselector;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vlada.countryselector.adapters.CitiesAdapter;
import com.example.vlada.countryselector.api.DBHelper;
import com.example.vlada.countryselector.api.Database;
import com.example.vlada.countryselector.api.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Spinner spinner;
    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;
    private LinearLayout content;
    private CitiesAdapter adapter;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private List<String> countries;
    private List<String> cities;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countries = new ArrayList<String>();
        cities = new ArrayList<String>();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        content = (LinearLayout) findViewById(R.id.content);
        dbHelper = new DBHelper(this);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            fetchData();
        });

        spinner = (Spinner) findViewById(R.id.countries);
        countries = Database.getCountries(countries);
        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cities = Database.getCities(countries, cities, position);
                adapter.add(cities);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.cities);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (adapter == null) {
            adapter = new CitiesAdapter();
        }
        recyclerView.setAdapter(adapter);
    }

    public void fetchData() {
        ServiceGenerator.getCountiesService().getCountriesToCities()
                .subscribeOn(Schedulers.io())
                .doOnNext(Database::incertData)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                    showProgress(true);
                })
                .doOnUnsubscribe(() -> {
                    showProgress(false);
                })
                .subscribe(
                        data -> {
                            countries.clear();
                            countries.addAll(data.keySet());
                            spinnerArrayAdapter.notifyDataSetChanged();
                            Log.d("fetch", "success");
                        }, error -> {
                            Log.e("Fetch", "failure", error);
                            Toast.makeText(this, "Failed to get Subscribed", Toast.LENGTH_SHORT).show();
                        });
    }

    public void showProgress(final boolean show){
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        content.setVisibility(show ? View.GONE : View.VISIBLE);
        floatingActionButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
