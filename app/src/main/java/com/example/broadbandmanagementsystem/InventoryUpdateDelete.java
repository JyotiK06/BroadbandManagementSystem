package com.example.broadbandmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.broadbandmanagementsystem.databases.InventoryDB;

import java.text.DecimalFormat;

public class InventoryUpdateDelete extends AppCompatActivity {
    private Button searchRecord, updateRecord, deleteRecord, showDataBtn;
    private EditText idField, nameField, quantityField, priceField;
    private TextView totalAmtText;
    private Double totalAmount;
    private int selectedId;
    private InventoryDB inventoryDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_update_delete);

        searchRecord = findViewById(R.id.searchInventoryBtn);
        updateRecord = findViewById(R.id.updateInventoryBtn);
        deleteRecord = findViewById(R.id.deleteInventoryBtn);
        showDataBtn = findViewById(R.id.showDataBtn);

        idField = findViewById(R.id.searchInventoryField);
        nameField = findViewById(R.id.invetoryName);
        quantityField = findViewById(R.id.invetoryQuantity);
        priceField = findViewById(R.id.invetoryPrice);
        totalAmtText = findViewById(R.id.inventoryTotalAmt);

        searchRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(idField.getText().toString())){
                    inventoryDB = new InventoryDB(InventoryUpdateDelete.this);
                    Cursor res = inventoryDB.getDataById(Integer.parseInt(idField.getText().toString()));

                    if(res.getCount() == 0){
                        Toast.makeText(InventoryUpdateDelete.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                        idField.setText("");
                        nameField.setText("");
                        priceField.setText("");
                        quantityField.setText("");
                        totalAmtText.setText("Price will be here");
                        return;
                    }

//                    Log.d("update","ID"+res.getInt(0)+"\nName: "+res.getString(1)+"\nPrice: "+res.getDouble(2)+"\nQuantity: "+res.getInt(3)+"\nTotal Amount: "+res.getDouble(4));
                    nameField.setText(res.getString(1));
                    priceField.setText(Double.toString(res.getDouble(2)));
                    quantityField.setText(Integer.toString(res.getInt(3)));
                    totalAmtText.setText("Total Amount: "+Double.toString(res.getDouble(4)));
                    totalAmount = res.getDouble(4);
                    selectedId = Integer.parseInt(idField.getText().toString());
                }else{
                    Toast.makeText(InventoryUpdateDelete.this, "Plzz Enter ID", Toast.LENGTH_SHORT).show();
                }
            }
        });


        updateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(idField.getText().toString())){
                    if(!TextUtils.isEmpty(nameField.getText().toString()) && !TextUtils.isEmpty(priceField.getText().toString()) && !TextUtils.isEmpty(quantityField.getText().toString())){
                        String name = nameField.getText().toString();
                        double price = Double.parseDouble(priceField.getText().toString());
                        int quantity = Integer.parseInt(quantityField.getText().toString());
                         inventoryDB = new InventoryDB(InventoryUpdateDelete.this);
                         boolean checkUpdate = inventoryDB.updateData(selectedId, name, quantity, price, totalAmount);
                         if(checkUpdate){
                             Toast.makeText(InventoryUpdateDelete.this, "Data Updated", Toast.LENGTH_SHORT).show();
                             idField.setText("");
                             nameField.setText("");
                             priceField.setText("");
                             quantityField.setText("");
                             totalAmtText.setText("Price will be here");
                        }else{
                             Toast.makeText(InventoryUpdateDelete.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                         }
                    }else{
                        Toast.makeText(InventoryUpdateDelete.this, "Plzz Fill Data To Update", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(InventoryUpdateDelete.this, "First Search Record", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(idField.getText().toString())){
                    if(!TextUtils.isEmpty(nameField.getText().toString()) && !TextUtils.isEmpty(priceField.getText().toString()) && !TextUtils.isEmpty(quantityField.getText().toString())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(InventoryUpdateDelete.this);
                        builder.setTitle("Alert !");
                        builder.setMessage("Do you want to delete the record?");
                        builder.setCancelable(false);

                        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                inventoryDB = new InventoryDB(InventoryUpdateDelete.this);
                                Boolean checkDelete = inventoryDB.deleteData(selectedId);

                                if(checkDelete){
                                    Toast.makeText(InventoryUpdateDelete.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                                    idField.setText("");
                                    nameField.setText("");
                                    priceField.setText("");
                                    quantityField.setText("");
                                    totalAmtText.setText("Price will be here");
                                }else{
                                    Toast.makeText(InventoryUpdateDelete.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }else{
                        Toast.makeText(InventoryUpdateDelete.this, "Plzzz Select Record To Delete", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(InventoryUpdateDelete.this, "Plzzz Select Record To Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        quantityField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(quantityField.getText().toString()) && !TextUtils.isEmpty(priceField.getText().toString())){
                    totalAmount = Integer.parseInt(quantityField.getText().toString()) * Double.parseDouble(priceField.getText().toString());
                    totalAmtText.setText("Total Amount: "+new DecimalFormat("##.##").format(totalAmount));
                }else{
                    totalAmtText.setText("Price will be here");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        priceField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(quantityField.getText().toString()) && !TextUtils.isEmpty(priceField.getText().toString())){
                    totalAmount = Integer.parseInt(quantityField.getText().toString()) * Double.parseDouble(priceField.getText().toString());
                    totalAmtText.setText("Total Amount: "+new DecimalFormat("##.##").format(totalAmount));
                }else{
                    totalAmtText.setText("Price will be here");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        showDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryUpdateDelete.this, ShowInventoryData.class);
                startActivity(intent);
            }
        });
    }
}