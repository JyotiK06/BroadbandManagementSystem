package com.example.broadbandmanagementsystem.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class LoginDB extends SQLiteOpenHelper {
    public LoginDB(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE loginDB(_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(String name, String pass, String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("password", pass);
        values.put("email", email);

        long result = db.insert("loginDB", null, values);
        Log.d("login","Data inserted");
        db.close();

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean validateAdmin(String email, String pass){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from loginDB where email= ? and password = ?", new String[] { email, pass });
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }
}
