package com.example.alarmster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "Alarms.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create Table AlarmDetails (time TEXT, days TEXT primary key, week TEXT, message TEXT, song TEXT, image TEXT, state TEXT, color TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop Table if exists AlarmDetails");

    }

    public boolean insertData(String time, StringBuffer days, String week, String message, String song, String image, String state, String color){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("time", time);
        contentValues.put("days", String.valueOf(days));
        contentValues.put("week", week);
        contentValues.put("message", message);
        contentValues.put("song", song);
        contentValues.put("image", image);
        contentValues.put("state", state);
        contentValues.put("color", color);

        long result = db.insert("AlarmDetails", null, contentValues);

        return result != -1;

    }

    public boolean  updateState(StringBuffer days, String state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("state", state);

        Cursor cursor = db.rawQuery("Select * from AlarmDetails where days = ?", new String[]{days.toString()});
        if (cursor.getCount() > 0) {

            long result = db.update("AlarmDetails", contentValues, "days = ?", new String[]{days.toString()});

            cursor.close();
            return result != -1;

        }
        else {
            cursor.close();
            return false;
        }


    }

    public boolean  updateTime(StringBuffer days, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("time", time);

        Cursor cursor = db.rawQuery("Select * from AlarmDetails where days = ?", new String[]{days.toString()});
        if (cursor.getCount() > 0) {

            long result = db.update("AlarmDetails", contentValues, "days = ?", new String[]{days.toString()});

            cursor.close();
            return result != -1;

        }
        else {
            cursor.close();
            return false;
        }


    }

    public boolean  updateWeek(StringBuffer days, String week){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("week", week);

        Cursor cursor = db.rawQuery("Select * from AlarmDetails where days = ?", new String[]{days.toString()});
        if (cursor.getCount() > 0) {

            long result = db.update("AlarmDetails", contentValues, "days = ?", new String[]{days.toString()});

            cursor.close();
            return result != -1;

        }
        else {
            cursor.close();
            return false;
        }


    }


    public boolean deleteData(StringBuffer days) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from AlarmDetails where days = ?", new String[]{days.toString()});
        if (cursor.getCount() > 0) {

            long result = db.delete("AlarmDetails", "days = ?", new String[]{days.toString()});

            cursor.close();
            return result != -1;

        }
        else {
            cursor.close();
            return false;
        }
    }

    public ArrayList<ArrayList<String>> getSortedData(){

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

        Cursor cursor = db.query("AlarmDetails", new String[] { "*" },
                null,
                null, null, null, "week, days", null);

        if(cursor != null) {

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                ArrayList<String> record = new ArrayList<>();

                record.add(cursor.getString(0)); // time
                record.add(cursor.getString(1)); // days
                record.add(cursor.getString(2)); // week
                record.add(cursor.getString(3)); // message
                record.add(cursor.getString(4)); // song
                record.add(cursor.getString(5)); // image
                record.add(cursor.getString(6)); // state
                record.add(cursor.getString(7)); // color

                rows.add(record);

                cursor.moveToNext();
            }
            cursor.close();
        }

        db.close();
        return rows;
    }

    public ArrayList<ArrayList<String>> getData(){

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

        Cursor cursor = db.query("AlarmDetails", new String[] { "*" },
                null,
                null, null, null, "ROWID DESC", null);

        if(cursor != null) {

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                ArrayList<String> record = new ArrayList<>();

                record.add(cursor.getString(0)); // time
                record.add(cursor.getString(1)); // days
                record.add(cursor.getString(2)); // week
                record.add(cursor.getString(3)); // message
                record.add(cursor.getString(4)); // song
                record.add(cursor.getString(5)); // image
                record.add(cursor.getString(6)); // state
                record.add(cursor.getString(7)); // color

                rows.add(record);

                cursor.moveToNext();
            }
            cursor.close();
        }

        db.close();
        return rows;
    }

}
