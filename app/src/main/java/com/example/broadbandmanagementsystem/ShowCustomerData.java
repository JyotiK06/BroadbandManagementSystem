package com.example.broadbandmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;


import com.example.broadbandmanagementsystem.Adapter.CustomerAdapter;
import com.example.broadbandmanagementsystem.databases.CustomerDB;


public class ShowCustomerData extends AppCompatActivity {
    private ListView customerView;
    private Button exportCustomerDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer_data);

        customerView = findViewById(R.id.customerView);
        exportCustomerDataBtn = findViewById(R.id.exportCustomerDataBtn);

        CustomerDB customerDB = new CustomerDB(this);
        SQLiteDatabase db = customerDB.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM customerDB",null);

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
            CustomerAdapter customerAdapter = new CustomerAdapter(this,cursor);
            customerView.setAdapter(customerAdapter);
        }
    }
}