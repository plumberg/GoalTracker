package com.example.gryzhuk.goaltracker;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AddGoal extends AppCompatActivity {
        // create arraylist os string
        // json storing date, message
        //

    private EditText message;
    private DatePicker picker;
    private ListView list;
    private HashMap<String, String> associatedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        picker.setMinDate(new Date().getTime());

        list = (ListView) findViewById(R.id.listview);
        associatedItem = new HashMap<String, String>();
        setupMssg();
    }


    public void setupDate(String s) throws ParseException {

        picker=(DatePicker)findViewById(R.id.datePicker1);
       // Toast.makeText(this,picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear(),Toast.LENGTH_LONG).show();

        //add to the screen list, save as json?
        //Item is a date, subItem is a message
        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth();
        int   year = picker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(day, month, year);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formatedDate = sdf.format(calendar.getTime());


        //put date and message to the hashmap
        associatedItem.put(formatedDate, s);

        ArrayList<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});

        Iterator it = associatedItem.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        list.setAdapter(adapter);


        Button done = findViewById(R.id.done_btn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddGoal.super.onBackPressed(); // to go back
            }
        });
    }

    public void setupMssg(){
        final String strMssg;
        message = (EditText)findViewById(R.id.enteredGoalTxt);
        strMssg= message.getText().toString();
        message.setText("");

        if (strMssg != "") {
            Button next = findViewById(R.id.done_btn);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        setupDate(strMssg);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else
            Toast.makeText(this,"Must include message",Toast.LENGTH_LONG).show();
    }


}
