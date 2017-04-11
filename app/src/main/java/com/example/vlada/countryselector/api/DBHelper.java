package com.example.vlada.countryselector.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table countries(" +
            "id integer primary key autoincrement," +
            "country text);");
        db.execSQL("create table cities(" +
                "id integer primary key autoincrement," +
                "city text," +
                "countryId integer," +
                "FOREIGN KEY(countryId) REFERENCES countries(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
