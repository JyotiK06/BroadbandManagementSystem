package com.example.broadbandmanagementsystem.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CustomerDB extends SQLiteOpenHelper {
    public CustomerDB(Context context) {
        super(context, "Customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE customerDB(_id INTEGER PRIMARY KEY, name TEXT, username Text, address TEXT, email Text, phno TEXT, plan_details Text, start_date date, end_date date, amt int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean inserCustomer(String name, String username,String addr, String email, String phno, String planDetails, String sdate, String edate, int amt){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("username", username);
        values.put("address", addr);
        values.put("email", email);
        values.put("phno", phno);
        values.put("plan_details", planDetails);
        values.put("start_date", sdate);
        values.put("end_date", edate);
        values.put("amt", amt);

        long result = db.insert("customerDB", null, values);
        Log.d("login","Customer inserted");
        db.close();

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateData(int _id, String name, String username, String addr, String email, String phno, String  plan_details, String sdate, String end_data, int amt){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("username",username);
        values.put("address",addr);
        values.put("email",email);
        values.put("phno",phno);
        values.put("plan_details",plan_details);
        values.put("start_date",sdate);
        values.put("end_date",end_data);
        values.put("amt",amt);

        Cursor cursor = db.rawQuery("Select * from customerDB where _id = ?", new String[] {String.valueOf(_id)});

        if(cursor.getCount() > 0){
            long result = db.update("customerDB", values, "_id=?", new String[] {String.valueOf(_id)});
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public boolean deleteRecord(int _id ){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from customerDB where _id = ?", new String[] {String.valueOf(_id)});

        if(cursor.getCount() > 0){
            long result = db.delete("customerDB",  "_id=?", new String[] {String.valueOf(_id)});
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Cursor getDataById(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from customerDB where _id ="+id,null);
        cursor.moveToFirst();
        return cursor;
    }
}
