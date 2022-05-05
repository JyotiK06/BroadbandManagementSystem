package com.example.broadbandmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.broadbandmanagementsystem.databases.CustomerDB;
import com.example.broadbandmanagementsystem.databases.InventoryDB;

public class UpdateDeleteCustomer extends AppCompatActivity {
    private Spinner planSelector;
    private TextView curretPlanText;
    private CalendarView calendarView;
    private EditText idEdit, nameEdit, usernameEdit, addressEdit, emailEdit, phoneEdit;
    private Button serachBtn, updateBtn, deleteBtn,showCustomerDataBtn ;
    private int amt;
    private String selectDate, selectedPlan, end_date;
    private String plans[] = {"Select Plan","1 Month-599/-", "3 Month-799/-", "4 Month-899/-", "6 Month-1099/-", "12 Month-1499/-",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_customer);

        planSelector = findViewById(R.id.planSelector);
        idEdit = findViewById(R.id.searchField);
        serachBtn = findViewById(R.id.searchBtn);
        updateBtn =findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        showCustomerDataBtn = findViewById(R.id.showCustomerDataBtn);

        nameEdit = findViewById(R.id.customerName);
        usernameEdit = findViewById(R.id.customerUserName);
        addressEdit = findViewById(R.id.customerAddress);
        emailEdit = findViewById(R.id.customerEmail);
        phoneEdit = findViewById(R.id.customerPhone);
        curretPlanText = findViewById(R.id.currentPlan);
        calendarView = findViewById(R.id.calendarView2);


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, plans){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0){
                    return false;
                }else{
                    return  true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        planSelector.setAdapter(arrayAdapter);

        planSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(selectedPlan != "Current Plan" & selectDate != null)
                {
                    selectedPlan = planSelector.getSelectedItem().toString();
                    selectDate = null;
                    curretPlanText.setText("Current Plan: "+selectedPlan+"\nNow select date");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectDate = year + "/" + dayOfMonth + "/" + (month + 1);
                if(selectedPlan.equals("1 Month-599/-"))
                {
                    end_date = year + "/" + dayOfMonth + "/" + (month + 2);
                    amt = 599;
                }else if(selectedPlan.equals("3 Month-799/-"))
                {
                    end_date = year + "/" + dayOfMonth + "/" + (month + 4);
                    amt = 799;
                }else if(selectedPlan.equals("4 Month-899/-"))
                {
                    end_date = year + "/" + dayOfMonth + "/" + (month + 5);
                    amt = 899;
                }else if(selectedPlan.equals("6 Month-1099/-"))
                {
                    end_date = year + "/" + dayOfMonth + "/" + (month + 7);
                    amt = 1099;
                }else if(selectedPlan.equals("12 Month-1499/-"))
                {
                    end_date = (year + 1) + "/" + dayOfMonth + "/" + (month + 1);
                    amt = 1499;
                }
                curretPlanText.setText("Current Plan: "+selectedPlan+"\nStart Date: "+selectDate);
                Toast.makeText(UpdateDeleteCustomer.this, "Date "+selectDate, Toast.LENGTH_SHORT).show();
            }
        });


        serachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(idEdit.getText().toString())){
                    CustomerDB customerDB = new CustomerDB(UpdateDeleteCustomer.this);
                    Cursor cursor = customerDB.getDataById(Integer.parseInt(idEdit.getText().toString()));

                    if(cursor.getCount() == 0){
                        Toast.makeText(UpdateDeleteCustomer.this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                        idEdit.setText("");
                        nameEdit.setText("");
                        usernameEdit.setText("");
                        addressEdit.setText("");
                        emailEdit.setText("");
                        phoneEdit.setText("");
                        curretPlanText.setText("Current Plan");
                        return;
                    }

                    nameEdit.setText(cursor.getString(1));
                    usernameEdit.setText(cursor.getString(2));
                    addressEdit.setText(cursor.getString(3));
                    emailEdit.setText(cursor.getString(4));
                    phoneEdit.setText(cursor.getString(5));
                    curretPlanText.setText("Current Plan: "+cursor.getString(6)+"\nStart Date: "+cursor.getString(7));
                    selectDate = cursor.getString(7);
                    selectedPlan = cursor.getString(6);
                    end_date = cursor.getString(8);
                    amt = cursor.getInt(9);
                }else{
                    Toast.makeText(UpdateDeleteCustomer.this, "Please Enter Id for searching", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(idEdit.getText().toString())){
                    if(!TextUtils.isEmpty(nameEdit.getText().toString()) & !TextUtils.isEmpty(usernameEdit.getText().toString()) & !TextUtils.isEmpty(addressEdit.getText().toString()) & !TextUtils.isEmpty(emailEdit.getText().toString()) & !TextUtils.isEmpty(phoneEdit.getText().toString())){
                        int id= Integer.parseInt(idEdit.getText().toString());
                        String name = nameEdit.getText().toString();
                        String username = usernameEdit.getText().toString();
                        String addr = addressEdit.getText().toString();
                        String email = emailEdit.getText().toString();
                        String phno = phoneEdit.getText().toString();
                        if(planSelector.getSelectedItem().toString() != "Select Plan"){
                            selectedPlan = planSelector.getSelectedItem().toString();
                        }

                        if(selectDate != null){
                            Log.d("Update","CustomerUpdate:"+name+" "+username+" "+addr+" "+email+" "+phno+" "+selectedPlan+" "+selectDate+" "+end_date+" "+amt);
                        CustomerDB customerDB = new CustomerDB(UpdateDeleteCustomer.this);
                        boolean checkUpdate = customerDB.updateData(id, name, username, addr, email, phno, selectedPlan, selectDate, end_date, amt);
                            if(checkUpdate){
                                Toast.makeText(UpdateDeleteCustomer.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                idEdit.setText("");
                                nameEdit.setText("");
                                usernameEdit.setText("");
                                addressEdit.setText("");
                                emailEdit.setText("");
                                phoneEdit.setText("");
                                curretPlanText.setText("Current Plan");
                            }else{
                                Toast.makeText(UpdateDeleteCustomer.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(UpdateDeleteCustomer.this, "Select Date After Plan Updation", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(UpdateDeleteCustomer.this, "Plzz Fill Data To Update", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(UpdateDeleteCustomer.this, "First Search Record", Toast.LENGTH_SHORT).show();
                }
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(idEdit.getText().toString())){
                    if(!TextUtils.isEmpty(nameEdit.getText().toString()) & !TextUtils.isEmpty(usernameEdit.getText().toString()) & !TextUtils.isEmpty(addressEdit.getText().toString()) & !TextUtils.isEmpty(emailEdit.getText().toString()) & !TextUtils.isEmpty(phoneEdit.getText().toString())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteCustomer.this);
                        builder.setTitle("Alert !");
                        builder.setMessage("Do you want to delete the record?");
                        builder.setCancelable(false);

                        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CustomerDB customerDB = new CustomerDB(UpdateDeleteCustomer.this);
                                Boolean checkDelete = customerDB.deleteRecord(Integer.parseInt(idEdit.getText().toString()));

                                if(checkDelete){
                                    Toast.makeText(UpdateDeleteCustomer.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                                    idEdit.setText("");
                                    nameEdit.setText("");
                                    usernameEdit.setText("");
                                    addressEdit.setText("");
                                    emailEdit.setText("");
                                    phoneEdit.setText("");
                                    curretPlanText.setText("Current Plan");
                                }else{
                                    Toast.makeText(UpdateDeleteCustomer.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UpdateDeleteCustomer.this, "Plzzz Select Record To Delete", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(UpdateDeleteCustomer.this, "Plzzz Select Record To Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showCustomerDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateDeleteCustomer.this, ShowCustomerData.class);
                startActivity(intent);
            }
        });
    }
}