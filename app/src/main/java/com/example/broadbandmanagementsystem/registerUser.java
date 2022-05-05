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
import android.widget.Toast;

import com.example.broadbandmanagementsystem.databases.LoginDB;

import java.util.regex.Pattern;

public class registerUser extends AppCompatActivity {
    private Intent registerIntent;
    private EditText usernameField, emailFiled, conEmailFiled, passField, conPassFiled;
    private TextView userTextView, emailTextView, conEmailTextView, passTextView, conPassTextView;
    private Button registerBtn;
//    private static Pattern emailPattern = Pattern.compile("^\\w+[a-zA-Z]+([\\.!#$%'*+-\\/=?^_`{|}~]?\\w+)*@\\w*[a-zA-z]+([\\-]?\\w+)*(\\.[a-zA-Z]{2,3})+$"), passPattern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        usernameField = findViewById(R.id.inputUsername);
        emailFiled = findViewById(R.id.inputEmail);
        conEmailFiled = findViewById(R.id.inputEmailConfirm);
        passField = findViewById(R.id.inputNewPassword);
        conPassFiled = findViewById(R.id.inputPasswordConfirm);
        registerBtn = findViewById(R.id.registerBtn);

        userTextView = findViewById(R.id.usernameValidText);
        emailTextView = findViewById(R.id.emailValidText);
        conEmailTextView = findViewById(R.id.emailConfirmValidText);
        passTextView = findViewById(R.id.passValidText);
        conPassTextView = findViewById(R.id.passConValidText);



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!TextUtils.isEmpty(usernameField.getText().toString()) && !TextUtils.isEmpty(emailFiled.getText().toString()) && !TextUtils.isEmpty(conEmailFiled.getText().toString()) && !TextUtils.isEmpty(passField.getText().toString()) && !TextUtils.isEmpty(conPassFiled.getText().toString())){
                      String username = usernameField.getText().toString();
                      String email = emailFiled.getText().toString();
                      String confirmEmail = conEmailFiled.getText().toString();
                      String password = passField.getText().toString();
                      String confirmPassword = conPassFiled.getText().toString();
                      if(validateUsername(username) & validateEmail(email) & validateConEmail(email) & validatePass(password) & validateConPass(password)){

                          Log.d("login","Name: "+username+"\nEmail:"+email+"\nConfirm Email: "+confirmEmail+"\nPassword: "+password+"\nConfirm Password: "+confirmPassword);

                          LoginDB loginDB = new LoginDB(registerUser.this);
                          boolean checkInsert = loginDB.insertData(username, password, email);
                          if(checkInsert){
                              Toast.makeText(registerUser.this, "Admin Created", Toast.LENGTH_SHORT).show();
                              usernameField.setText("");
                              emailFiled.setText("");
                              conEmailFiled.setText("");
                              passField.setText("");
                              conPassFiled.setText("");
                          }

                      }
//                }else{
//                    Toast.makeText(registerUser.this, "Plzzz Fill Details", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    public Boolean validateUsername(String usernm){
        String userPattern = "^[a-zA-Z]{1}+(\\w+){4,}";
        userTextView.setTextColor(Color.RED);
        userTextView.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(usernameField.getText().toString())) {
            userTextView.setText("Filed cannot be empty");
            return false;
        }else if(!usernm.matches(userPattern)) {
            userTextView.setText("username must contain more than 5 letters (Alphabets and digits allowed & No spaces and special character)");
            return false;
        }else {
            userTextView.setText(null);
            userTextView.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validateEmail(String em){
        String emailPattern = "^\\w+[a-zA-Z]+([\\.!#$%'*+-\\/=?^_`{|}~]?\\w+)*@\\w*[a-zA-z]+([\\-]?\\w+)*(\\.[a-zA-Z]{2,3})+$";
        emailTextView.setTextColor(Color.RED);
        emailTextView.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(emailFiled.getText().toString())) {
            emailTextView.setText("Filed cannot be empty");

            return false;
        }else if(!em.matches(emailPattern)) {
            emailTextView.setText("Email should be valid");
            return false;
        }else
        {
            emailTextView.setText(null);
            emailTextView.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validateConEmail(String em){
        conEmailTextView.setTextColor(Color.RED);
        conEmailTextView.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(conEmailFiled.getText().toString())) {
            conEmailTextView.setText("Filed cannot be empty");
            return false;
        }else if(emailTextView.getText().toString().equals("Email should be valid")) {
            conEmailTextView.setText("Email should be valid");
            return false;
        }else if(!conEmailFiled.getText().toString().equals(em)){
            conEmailTextView.setText("Email Not Matching");
            return false;
        }else{
            conEmailTextView.setText(null);
            conEmailTextView.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validatePass(String pass){
//        String password = passField.getText().toString();
        passTextView.setTextColor(Color.RED);
        passTextView.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(pass)){
            passTextView.setText("Filed cannot be empty");
            return false;
        }else if(pass.length() < 8) {
            passTextView.setText("password must contain more than 8 letters");
            return false;
        } else{
            passTextView.setText(null);
            passTextView.setVisibility(View.GONE);
            return true;
        }
    }

    public Boolean validateConPass(String pass){
        String password = conPassFiled.getText().toString();
        conPassTextView.setTextColor(Color.RED);
        conPassTextView.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(password)) {
            conPassTextView.setText("Filed cannot be empty");
            return false;
        }else if(!password.equals(pass)) {
            conPassTextView.setText("Password not matching");
            return false;
        } else{
            conPassTextView.setText(null);
            conPassTextView.setVisibility(View.GONE);
            return true;
        }
    }

    public void goLoginForm(View view){
        registerIntent = new Intent(this,MainActivity.class);
        startActivity(registerIntent);
    }
}