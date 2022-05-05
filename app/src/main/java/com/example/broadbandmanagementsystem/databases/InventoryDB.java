package com.example.broadbandmanagementsystem.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class InventoryDB extends SQLiteOpenHelper {
    public InventoryDB(Context context){
        super(context, "Project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE inventoryDB(_id INTEGER PRIMARY KEY AUTOINCREMENT , iName TEXT, price DOUBLE,quantity int, totalAmount DOUBLE)");
//        db.execSQL("DROP TABLE inventoryDB");
//        Log.d("test","Table Deleted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(String name, int quantity,double price, double totalAmount){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("iName",name);
        values.put("price",price);
        values.put("quantity",quantity);
        values.put("totalAmount",totalAmount);

        long result = db.insert("inventoryDB",null, values);
        Log.d("test","Data inserted");
        db.close();

        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateData(int _id, String name, int quantity,double price, double totalAmount){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("iName",name);
        values.put("price",price);
        values.put("quantity",quantity);
        values.put("totalAmount",totalAmount);

        Cursor cursor = db.rawQuery("Select * from inventoryDB where _id = ?", new String[] {String.valueOf(_id)});

        if(cursor.getCount() > 0){
            long result = db.update("inventoryDB", values, "_id=?", new String[] {String.valueOf(_id)});
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean deleteData(int _id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from inventoryDB where _id = ?", new String[] {String.valueOf(_id)});

        if(cursor.getCount() > 0){
            long result = db.delete("inventoryDB",  "_id=?", new String[] {String.valueOf(_id)});
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Cursor getDataById(int  id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from inventoryDB where _id ="+id,null);
        cursor.moveToFirst();
        return cursor;
    }

//    public Cursor getAllData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery("select * from inventoryDB",null);
//        cursor.moveToFirst();
//        return cursor;
//    }

}
