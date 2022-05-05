package com.example.broadbandmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ForgetPassword extends AppCompatActivity {
    private LinearLayout fragmentContainer, forgetEmailContainer;
    private Button nextBtn;
    private EditText otpField;
    private Intent forgetIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        fragmentContainer = findViewById(R.id.forgetFieldContainer);
        forgetEmailContainer = findViewById(R.id.forgetFieldContainer);
        nextBtn = findViewById(R.id.sendForgetEmail);
        otpField = findViewById(R.id.otpField);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgetIntent = new Intent(ForgetPassword.this, ChangePassword.class);

                startActivity(forgetIntent);
            }
        });
    }
}