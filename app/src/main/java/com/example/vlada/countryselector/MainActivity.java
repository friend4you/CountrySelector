package com.example.vlada.countryselector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Spinner spinner;
    private FloatingActionButton floatingActionButton;
    private List<String> countries;
    private List<String> cities;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private CitiesAdapter adapter;
    private ProgressBar progressBar;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countries = new ArrayList<String>();
        cities = new ArrayList<String>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        dbHelper = new DBHelper(this);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            fetchData();
        });

        spinner = (Spinner) findViewById(R.id.countries);
        getCountries();
        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCities(position);
                adapter.add(cities);
                Log.d("position", position + "");
                Log.d("id", id + "");
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

    public void getCountries() {
        countries.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("countries", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int countryIndex = c.getColumnIndex("country");
            do {
                countries.add(c.getString(countryIndex));
            } while (c.moveToNext());

        } else {
            countries.add("default");
        }
        c.close();
    }

    public void getCities(int id) {
        String country = countries.get(id);
        int countryId = 0;
        cities.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor v = db.query("countries", null, null, null, null, null, null);
        Cursor countryCursor = db.rawQuery("select * from countries where country = (?)", new String[]{country});
        if (countryCursor.moveToFirst()) {
            int countryIdIndex = v.getColumnIndex("id");
            countryId = countryCursor.getInt(countryIdIndex);
        } else {

        }
        Cursor cityCursor = db.rawQuery("select * from cities where countryId = (?)", new String[]{Integer.toString(countryId)});
        if (cityCursor.moveToFirst()) {
            int cityIndex = cityCursor.getColumnIndex("city");
            do {
                cities.add(cityCursor.getString(cityIndex));
            } while (cityCursor.moveToNext());
        } else {
            cities.add("empty");
        }
        v.close();
        countryCursor.close();
        cityCursor.close();
    }

    public void fetchData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ServiceGenerator.getCountiesService().getCountriesToCities()
                .subscribeOn(Schedulers.io())
                .doOnNext(data -> {
                    db.delete("cities", null, null);
                    db.delete("countries", null, null);
                    try {
                        db.beginTransaction();
                        for (Map.Entry<String, List<String>> element : data.entrySet()) {
                            db.execSQL("insert into countries (country) values (?);", new String[]{element.getKey()});
                        }
                        db.setTransactionSuccessful();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        db.endTransaction();
                    }
                    Cursor c = db.query("countries", null, null, null, null, null, null);
                    if (c.moveToFirst()) {
                        int idIndex = c.getColumnIndex("id");
                        int countryIndex = c.getColumnIndex("country");
                        try {
                            db.beginTransaction();
                            do {
                                final String country = c.getString(countryIndex);
                                for (String city : data.get(country)) {
                                    db.execSQL("insert into cities (city, countryId) values (?,?);", new Object[]{city, c.getInt(idIndex)});
                                }
                            } while (c.moveToNext());
                            db.setTransactionSuccessful();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            db.endTransaction();
                        }
                    }
                    c.close();
                })
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
                            Log.d("failure", "failure");
                            Toast.makeText(this, "Failed to get Subscribed", Toast.LENGTH_SHORT).show();
                        });
    }

    public void showProgress(final boolean show){
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
