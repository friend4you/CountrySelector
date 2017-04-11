package com.example.vlada.countryselector.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public final static String COUNTRIES = "countries";
    public final static String COUNTRYID = "id";
    public final static String COUNTRY = "country";

    public final static String CITIES = "cities";
    public final static String CITYID = "id";
    public final static String CITY = "city";
    public final static String CITIES_COUNTRYID = "countryId";

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + COUNTRIES + "(" +
                COUNTRYID + "integer primary key autoincrement," +
                COUNTRY + " text);");
        db.execSQL("create table "+ CITIES+"(" +
                CITYID+" integer primary key autoincrement," +
                CITY+" text," +
                CITIES_COUNTRYID +" integer," +
                "FOREIGN KEY(countryId) REFERENCES countries(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
