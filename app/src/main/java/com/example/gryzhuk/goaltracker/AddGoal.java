package com.example.gryzhuk.goaltracker;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class AddGoal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        setupFAB();
       // setupDone();
    }
    public void setupFAB() {
        Button done = findViewById(R.id.done_btn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupDone();
               AddGoal.super.onBackPressed();
            }
        });
    }

    public void setupDone(){
        DatePicker picker;
        picker=(DatePicker)findViewById(R.id.datePicker1);
        Toast.makeText(this,picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear(),Toast.LENGTH_LONG).show();

    }
}
