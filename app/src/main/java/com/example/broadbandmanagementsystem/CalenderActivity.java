package com.example.broadbandmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

public class CalenderActivity extends AppCompatActivity {
    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        calendarView = findViewById(R.id.calendarView);


        Intent incomingIntent = getIntent();
        String nm = incomingIntent.getStringExtra("name");
        String usernm = incomingIntent.getStringExtra("usernm");
        String addr = incomingIntent.getStringExtra("addr");
        String email = incomingIntent.getStringExtra("email");
        String phno = incomingIntent.getStringExtra("phno");
        String plandt = incomingIntent.getStringExtra("plandt");


        Log.d("CalenderView","Name "+nm+" "+usernm+" "+addr+" "+email+" "+phno+" "+plandt);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "/" + dayOfMonth + "/" + (month + 1);
                Log.d("Date","Date: mm/dd/yyyy "+date);
                String edate = null;
                int amt = 0;
                if(plandt.equals("1 Month-599/-"))
                {
                    edate = year + "/" + dayOfMonth + "/" + (month + 2);
                    amt = 599;
                }else if(plandt.equals("3 Month-799/-"))
                {
                    edate = year + "/" + dayOfMonth + "/" + (month + 4);
                    amt = 799;
                }else if(plandt.equals("4 Month-899/-"))
                {
                    edate = year + "/" + dayOfMonth + "/" + (month + 5);
                    amt = 899;
                }else if(plandt.equals("6 Month-1099/-"))
                {
                    edate = year + "/" + dayOfMonth + "/" + (month + 7);
                    amt = 1099;
                }else if(plandt.equals("12 Month-1499/-"))
                {
                    edate = (year + 1) + "/" + dayOfMonth + "/" + (month + 1);
                    amt = 1499;
                }

                Log.d("CalenderView","End Date "+edate+" "+amt);
                Intent intent = new Intent(CalenderActivity.this, CustemerDetailsManipulation.class);
                intent.putExtra("date", date);
                intent.putExtra("name",nm);
                intent.putExtra("usernm",usernm);
                intent.putExtra("addr",addr);
                intent.putExtra("email",email);
                intent.putExtra("phno",phno);
                intent.putExtra("plandt",plandt);
                intent.putExtra("edate",edate);
                intent.putExtra("amt",amt);
                startActivity(intent);
            }
        });
    }
}