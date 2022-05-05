package com.example.broadbandmanagementsystem.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.broadbandmanagementsystem.R;

public class CustomerAdapter extends CursorAdapter {

    public CustomerAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.customer_details, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView cusID = view.findViewById(R.id.cusID);
        TextView cusName = view.findViewById(R.id.cusName);
        TextView cusAddr = view.findViewById(R.id.cusAddr);
        TextView cusEmail = view.findViewById(R.id.cusEmail);
        TextView cusPhone = view.findViewById(R.id.cusPhone);
        TextView cusPlan = view.findViewById(R.id.cusPlan);

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String addr = cursor.getString(3);
        String email = cursor.getString(4);
        String phno = cursor.getString(5);
        String planDetails = cursor.getString(6);

        cusID.setText("ID: "+Integer.toString(id));
        cusName.setText("Name: "+name);
        cusAddr.setText("address: "+addr);
        cusEmail.setText("email: "+email);
        cusPhone.setText("phno: "+phno);
        cusPlan.setText("plan_details: "+planDetails);
    }
}
