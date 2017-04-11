package com.example.vlada.countryselector.api;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.vlada.countryselector.Application;

import java.util.List;
import java.util.Map;


public class Database {


    public static List<String> getCountries(List<String> countries) {
        countries.clear();
        DBHelper dbHelper = new DBHelper(Application.getSharedInstance().getApplicationContext());
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
        return countries;
    }

    public static List<String> getCities(List<String> countries, List<String> cities, int id) {
        String country = countries.get(id);
        int countryId = 0;
        cities.clear();
        DBHelper dbHelper = new DBHelper(Application.getSharedInstance().getApplicationContext());
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
        return cities;
    }

    public static void incertData(Map<String, List<String>> data){

        DBHelper dbHelper = new DBHelper(Application.getSharedInstance().getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

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
    }

}
