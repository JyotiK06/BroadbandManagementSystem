package com.example.broadbandmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.broadbandmanagementsystem.databases.LoginDB;

public class MainActivity extends AppCompatActivity {
    private Intent loginIntent;
    private EditText emailField, passwordField;
    private Button loginBtn;
    private TextView emailValidText, passValidText, loginInfoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailField = findViewById(R.id.inputEmail);
        passwordField = findViewById(R.id.inputPassword);
        loginBtn = findViewById(R.id.loginBtn);
        emailValidText = findViewById(R.id.emailValidText);
        passValidText = findViewById(R.id.passValidText);
        loginInfoText = findViewById(R.id.loginInfoText);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                Log.d("output","X: "+email+"Y: "+password);
                if(validateEmail(email) & validatePassword(password)){
                    LoginDB loginDB = new LoginDB(MainActivity.this);
                    boolean validateAdmin = loginDB.validateAdmin(email, password);
                    if(validateAdmin){
                        loginInfoText.setVisibility(View.GONE);
                        loginIntent = new Intent(MainActivity.this, Dashborad.class);
                        startActivity(loginIntent);
                    }else{
                        loginInfoText.setText("Invalid Credentials");
                        loginInfoText.setTextColor(Color.RED);
                        loginInfoText.setVisibility(View.VISIBLE);
                    }
//                    if(email.equals("x") && password.equals("y")){
//                        loginInfoText.setVisibility(View.GONE);
//                        loginIntent = new Intent(MainActivity.this, Dashborad.class);
//                        startActivity(loginIntent);
//                    }else{
//                        loginInfoText.setText("Invalid Credentials");
//                        loginInfoText.setTextColor(Color.RED);
//                        loginInfoText.setVisibility(View.VISIBLE);
//                    }
                }
            }
        });


    }

    public Boolean validateEmail(String email){

        if(TextUtils.isEmpty(email)) {
            emailValidText.setText("Filed cannot be empty");
            emailValidText.setTextColor(Color.RED);
            emailValidText.setVisibility(View.VISIBLE);
            loginInfoText.setVisibility(View.GONE);
            return false;
        }else{
            emailValidText.setText(null);
            emailValidText.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validatePassword(String password){
        if(TextUtils.isEmpty(password)){
            passValidText.setText("Filed cannot be empty");
            passValidText.setTextColor(Color.RED);
            passValidText.setVisibility(View.VISIBLE);
            loginInfoText.setVisibility(View.GONE);
            return false;
        }else{
            passValidText.setText(null);
            passValidText.setVisibility(View.GONE);
            return true;
        }
    }

    public void goRegisterForm(View view){
//         RegisterDB registerDB = new RegisterDB(MainActivity.this);
         loginIntent = new Intent(this,registerUser.class);
         startActivity(loginIntent);
    }

    public void goForgetPassword(View view){
         loginIntent = new Intent(this,ForgetPassword.class);
         startActivity(loginIntent);
    }
}