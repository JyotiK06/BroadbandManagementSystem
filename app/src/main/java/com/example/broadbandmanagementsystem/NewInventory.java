package com.example.broadbandmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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

public class NewInventory extends AppCompatActivity {
    private EditText nameField, quantityField, priceField;
    private TextView totalAmtText, nameText, priceText, quantutyText;
    private Button saveBtn;
    private Double totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_inventory);

        nameField = findViewById(R.id.invetoryName);
        quantityField = findViewById(R.id.invetoryQuantity);
        priceField = findViewById(R.id.invetoryPrice);
        totalAmtText = findViewById(R.id.inventoryTotalAmt);
        saveBtn = findViewById(R.id.saveInventory);
        nameText = findViewById(R.id.nameText);
        priceText = findViewById(R.id.priceText);
        quantutyText = findViewById(R.id.quntityText);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!TextUtils.isEmpty(nameField.getText().toString()) && !TextUtils.isEmpty(priceField.getText().toString()) && !TextUtils.isEmpty(quantityField.getText().toString()))
//                {


                   // Log.d("test","Name: "+name+" Quantity: "+quantity+" Price: "+price+" Total Amount: "+totalAmount);
                    if(validateName() & validatePrice() & validateQuantity()){
                        String name = nameField.getText().toString();
                        double price = Double.parseDouble(priceField.getText().toString());
                        int quantity = Integer.parseInt(quantityField.getText().toString());
                        InventoryDB inventoryDB = new InventoryDB(NewInventory.this);
                        boolean checkInsert = inventoryDB.insertData(name,quantity,price,totalAmount);

                        if(checkInsert){
                            Toast.makeText(NewInventory.this, "Record Saved", Toast.LENGTH_SHORT).show();
                            nameField.setText("");
                            priceField.setText("");
                            quantityField.setText("");
                            totalAmtText.setText("Price will be here");
                        }
                    }

//                }else{
//                    Log.d("test","Plzzz Filled Fields");
//                }
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
    }

    public Boolean validateName(){

        if(TextUtils.isEmpty(nameField.getText().toString())) {
            nameText.setText("Filed cannot be empty");
            nameText.setTextColor(Color.RED);
            nameText.setVisibility(View.VISIBLE);
            return false;
        }else{
            nameText.setText(null);
            nameText.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validatePrice(){

        if(TextUtils.isEmpty(priceField.getText().toString())) {
            priceText.setText("Filed cannot be empty");
            priceText.setTextColor(Color.RED);
            priceText.setVisibility(View.VISIBLE);
            return false;
        }else{
            priceText.setText(null);
            priceText.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validateQuantity(){

        if(TextUtils.isEmpty(quantityField.getText().toString())) {
            quantutyText.setText("Filed cannot be empty");
            quantutyText.setTextColor(Color.RED);
            quantutyText.setVisibility(View.VISIBLE);
            return false;
        }else{
            quantutyText.setText(null);
            quantutyText.setVisibility(View.GONE);
            return true;
        }
    }
}