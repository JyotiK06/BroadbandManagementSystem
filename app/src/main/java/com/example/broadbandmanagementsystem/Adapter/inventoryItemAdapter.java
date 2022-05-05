package com.example.broadbandmanagementsystem.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.broadbandmanagementsystem.R;

public class inventoryItemAdapter extends CursorAdapter {

    public inventoryItemAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView inId = view.findViewById(R.id.inId);
        TextView inName = view.findViewById(R.id.inName);
        TextView inPrice = view.findViewById(R.id.inPrice);
        TextView inQuantity = view.findViewById(R.id.inQuantity);
        TextView inTamnt = view.findViewById(R.id.inTamt);

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        double price = cursor.getDouble(2);
        int quantity = cursor.getInt(3);
        double tamt = cursor.getDouble(4);

        inId.setText("ID: "+Integer.toString(id));
        inName.setText("Name: "+name);
        inPrice.setText("Price: "+Double.toString(price));
        inQuantity.setText("Quantity: "+Integer.toString(quantity));
        inTamnt.setText("Total: "+Double.toString(tamt));
    }
}
