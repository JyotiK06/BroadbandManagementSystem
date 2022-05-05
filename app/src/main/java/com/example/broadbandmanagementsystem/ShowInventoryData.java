package com.example.broadbandmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.broadbandmanagementsystem.Adapter.inventoryItemAdapter;
import com.example.broadbandmanagementsystem.databases.InventoryDB;

public class ShowInventoryData extends AppCompatActivity {
    private ListView inventoryView;
    private Button exportDataBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_inventory_data);

        inventoryView = findViewById(R.id.inventoryView);
        exportDataBtn = findViewById(R.id.exportDataBtn);


        InventoryDB inventoryDB = new InventoryDB(this);
        SQLiteDatabase db = inventoryDB.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM inventoryDB",null);

        if(cursor.getCount() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert !");
            builder.setMessage("No Data Found.");

            builder.setCancelable(false);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        }else{
            inventoryItemAdapter inventoryItemAdapter = new inventoryItemAdapter(this,cursor);
            inventoryView.setAdapter(inventoryItemAdapter);
        }

    }


}