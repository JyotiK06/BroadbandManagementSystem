package com.example.broadbandmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.broadbandmanagementsystem.databases.CustomerDB;

import org.w3c.dom.Text;

import java.util.Date;

public class CustemerDetailsManipulation extends AppCompatActivity {
    private Intent intent, incomingIntent;
    private TextView dateView,nameText, userNameText, addressText, emailText, phoneText, planText;
    private Button saveCustomer;
    private EditText nameEdit, usernameEdit, addressEdit, emailEdit, phoneEdit;
    private Spinner planSelector;
    private String edate, plan;
    private int amt;
    private String plans[] = {"Select Plan","1 Month-599/-", "3 Month-799/-", "4 Month-899/-", "6 Month-1099/-", "12 Month-1499/-",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custemer_details_manipulation);

        saveCustomer= findViewById(R.id.saveCustomer);
        planSelector = findViewById(R.id.planSelector);
        dateView = findViewById(R.id.selectDate);
        nameText = findViewById(R.id.nameText);
        userNameText = findViewById(R.id.usernameText);
        addressText = findViewById(R.id.addressText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);
        planText = findViewById(R.id.planText);
        nameEdit = findViewById(R.id.customerName);
        usernameEdit = findViewById(R.id.customerUserName);
        addressEdit = findViewById(R.id.customerAddress);
        emailEdit = findViewById(R.id.customerEmail);
        phoneEdit = findViewById(R.id.customerPhone);

        dateView.setText("Select Date");

        incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        Log.d("Test","Date: "+date);
        if(date != null){
//            dateView.setText(date);
            String nm = incomingIntent.getStringExtra("name");
            String usernm = incomingIntent.getStringExtra("usernm");
            String addr = incomingIntent.getStringExtra("addr");
            String email = incomingIntent.getStringExtra("email");
            String phno = incomingIntent.getStringExtra("phno");
            plan = incomingIntent.getStringExtra("plandt");
            edate = incomingIntent.getStringExtra("edate");

            nameEdit.setText(nm);
            usernameEdit.setText(usernm);
            addressEdit.setText(addr);
            emailEdit.setText(email);
            phoneEdit.setText(phno);
            int index = 0;
            for(int i=0;i<plans.length;i++) {
                if (plans[i].equals(plan)) {
                    index = i;
                    break;
                }
            }
            planSelector.setSelection(index);
            if(edate == null){
                dateView.setText("Please select plan before selecting date");
                dateView.setTextColor(Color.RED);
            }else{
                amt = incomingIntent.getIntExtra("amt", 0);
                dateView.setText("selected plan: "+plan+"\nStart Date: "+date+"\nAmount"+amt);
                dateView.setTextColor(Color.GRAY);
            }
            //            Log.d("Item:","daw"+index);
        }

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


        saveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String username = usernameEdit.getText().toString();
                String address = addressEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String phno = phoneEdit.getText().toString();
//                String planVar = planSelector.getSelectedItem().toString();
                String dateVar = dateView.getText().toString();
                if(validateName(name) & validateUsername(username) & validateAddress(address) & validateEmail(email) & validatePhone(phno) & validatePlandate()){
                    CustomerDB customerDB = new CustomerDB(CustemerDetailsManipulation.this);
                    boolean checkInsert = customerDB.inserCustomer(name, username,address, email, phno, plan, date, edate, amt);
                    if(checkInsert){
                        Toast.makeText(CustemerDetailsManipulation.this, "Customer Created", Toast.LENGTH_SHORT).show();
                        nameEdit.setText("");
                        usernameEdit.setText("");
                        addressEdit.setText("");
                        emailEdit.setText("");
                        phoneEdit.setText("");
                        dateView.setTextColor(Color.GRAY);
                        dateView.setText("Select Date");
                    }
                }else
                {
                    Toast.makeText(CustemerDetailsManipulation.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Boolean validateName(String nm){

        if(TextUtils.isEmpty(nm)) {
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

    public Boolean validateUsername(String usernm){
        String userPattern = "^[a-zA-Z]{1}+(\\w+){4,}";
        userNameText.setTextColor(Color.RED);
        userNameText.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(usernameEdit.getText().toString())) {
            userNameText.setText("Filed cannot be empty");
            return false;
        }else if(!usernm.matches(userPattern)) {
            userNameText.setText("username must contain more than 5 letters (Alphabets and digits allowed & No spaces and special character)");
            return false;
        }else {
            userNameText.setText(null);
            userNameText.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validateAddress(String addr){

        if(TextUtils.isEmpty(addr)) {
            addressText.setText("Filed cannot be empty");
            addressText.setTextColor(Color.RED);
            addressText.setVisibility(View.VISIBLE);
            return false;
        }else{
            addressText.setText(null);
            addressText.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validateEmail(String em){
        String emailPattern = "^\\w+[a-zA-Z]+([\\.!#$%'*+-\\/=?^_`{|}~]?\\w+)*@\\w*[a-zA-z]+([\\-]?\\w+)*(\\.[a-zA-Z]{2,3})+$";
        emailText.setTextColor(Color.RED);
        emailText.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(emailEdit.getText().toString())) {
            emailText.setText("Filed cannot be empty");

            return false;
        }else if(!em.matches(emailPattern)) {
            emailText.setText("Email should be valid");
            return false;
        }else
        {
            emailText.setText(null);
            emailText.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validatePhone(String plan){
        if(TextUtils.isEmpty(plan)) {
            phoneText.setText("Filed cannot be empty");
            phoneText.setTextColor(Color.RED);
            phoneText.setVisibility(View.VISIBLE);
            return false;
        }else{
            phoneText.setText(null);
            phoneText.setVisibility(View.GONE);
            return true;
        }
    }

//    public Boolean validatePlan(String phno){
//        if(phno.equals("Select Plan")) {
//            planText.setText("Please select plan");
//            planText.setTextColor(Color.RED);
//            planText.setVisibility(View.VISIBLE);
//            return false;
//        }else{
//            planText.setText(null);
//            planText.setVisibility(View.GONE);
//            return true;
//        }
//    }

    public Boolean validatePlandate(){
        String dateviewText = dateView.getText().toString();
        if(dateviewText.equals("Select Date")) {
            dateView.setText("Click here to select plan date");
            dateView.setTextColor(Color.RED);
            dateView.setVisibility(View.VISIBLE);
            return false;
        }else if(dateviewText.equals("Please select plan before selecting date") && plan != null) {
            dateView.setText("Select Date");
            dateView.setTextColor(Color.RED);
            dateView.setVisibility(View.VISIBLE);
            return false;
        }else if(dateviewText.equals("Click here to select plan date")){
            return false;
        } else{
            return true;
        }
    }

    public void getDate(View view){
        intent = new Intent(CustemerDetailsManipulation.this, CalenderActivity.class);
        intent.putExtra("name",nameEdit.getText().toString());
        intent.putExtra("usernm",usernameEdit.getText().toString());
        intent.putExtra("addr",addressEdit.getText().toString());
        intent.putExtra("email",emailEdit.getText().toString());
        intent.putExtra("phno",phoneEdit.getText().toString());
        intent.putExtra("plandt",planSelector.getSelectedItem().toString());
        startActivity(intent);
    }
}