package com.example.broadbandmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Dashborad extends AppCompatActivity {
    private Intent dashboardIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashborad);
    }

    public void goCustomeDetails(View view) {
        dashboardIntent = new Intent(this,CustemerDetailsManipulation.class);
        startActivity(dashboardIntent);
    }

    public void goUpdateDeleteCustomerDetails(View view) {
        dashboardIntent = new Intent(this,UpdateDeleteCustomer.class);
        startActivity(dashboardIntent);
    }

    public void goInventoryDetails(View view) {
        dashboardIntent = new Intent(this,NewInventory.class);
        startActivity(dashboardIntent);
    }

    public void goUpdateDeleteInventoryDetails(View view) {
        dashboardIntent = new Intent(this,InventoryUpdateDelete.class);
        startActivity(dashboardIntent);
    }

    public void closeApp(View view){
        this.finishAffinity();
    }
}