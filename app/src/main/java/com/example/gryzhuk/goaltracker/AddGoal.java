package com.example.gryzhuk.goaltracker;


import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ListView;

import android.widget.Toast;

import com.example.gryzhuk.goaltracker.database.DatabaseHelper;
import com.example.gryzhuk.goaltracker.lib.Goal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;


import static com.example.gryzhuk.goaltracker.R.layout.content_main;
import static java.util.Locale.US;
import static java.util.stream.IntStream.concat;

public class AddGoal extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;
    private EditText message;
    private DatePicker picker;
    private boolean mRecordSuccessfullyAdded = false;

    private ArrayList<Goal> notesList = new ArrayList<Goal>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_widget);
        mDatabaseHelper = new DatabaseHelper (this);
        setupViews ();
    }

    private void setupViews() {
        message = findViewById (R.id.enteredWidgetText);
        picker = findViewById(R.id.datePickerWidget);
    }

    public void setupMsg() {
        message = (EditText) findViewById(R.id.enteredWidgetText);
    }


    public void doneButtonHandler(View view) {
        //Item is a date, subItem is a message

        /*Toast.makeText(this,strMssg+" "+picker.getDayOfMonth()+"/"+
                (picker.getMonth() + 1)+"/"+picker.getYear(),Toast.LENGTH_LONG).show();*/
        String strMsg = message.getText ().toString ();
        if (strMsg.length () == 0) {
            Toast.makeText (getApplicationContext (), "Must include message",
                    Toast.LENGTH_SHORT).show ();
        }
        else {
            String formattedDate = getFormattedDateFromPicker ();
            attemptToAddNewRecord (strMsg, formattedDate);
            finish ();
        }

        finish();


    }

    private void attemptToAddNewRecord(String strMsg, String formattedDate) {
        long id = mDatabaseHelper.insertGoal (formattedDate, strMsg);
        mRecordSuccessfullyAdded = id !=-1;
    }

    private String getFormattedDateFromPicker() {
        int day = picker.getDayOfMonth ();
        int month = picker.getMonth ();
        int year = picker.getYear ();
        Calendar calendar = Calendar.getInstance ();
        calendar.set (year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat ("MM/dd/yyyy", US);
        return sdf.format (calendar.getTime ());
    }

    @Override
    public void finish() {
        setResult (mRecordSuccessfullyAdded ? RESULT_OK : RESULT_CANCELED);
        super.finish ();
    }


    @Override protected void onSaveInstanceState (Bundle outState)
    {
        super.onSaveInstanceState (outState);
        outState.putInt ("YEAR", picker.getYear ());
        outState.putInt ("MONTH", picker.getMonth ());
        outState.putInt ("DAY", picker.getDayOfMonth ());
    }

    @Override protected void onRestoreInstanceState (Bundle savedInstanceState)
    {
        super.onRestoreInstanceState (savedInstanceState);
        picker.updateDate (savedInstanceState.getInt ("YEAR"),
                savedInstanceState.getInt ("MONTH"),
                savedInstanceState.getInt ("DAY"));
    }


}
