package com.example.gryzhuk.goaltracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.zip.Inflater;

import static com.example.gryzhuk.goaltracker.R.layout.content_main;
import static java.util.Locale.US;
import static java.util.stream.IntStream.concat;

public class AddGoal extends AppCompatActivity {
        // create arraylist os string
        // json storing date, message

    private EditText message;
    private DatePicker picker;
    private ListView list;
    private HashMap<String, String> associatedItem;
    private String strMssg;
    //private Intent intent;
    View dialogLayout;


    protected void onCreate( Bundle savedInstanceState) {//LayoutInflater inflater
        super.onCreate(savedInstanceState);

       // intent = new Intent();
       // intent.setClass(this,MainActivity.class);

        strMssg = "";


       // list = findViewById(R.id.list);
        associatedItem = new HashMap<String, String>();
        setContentView(R.layout.activity_add_goal_text);
        setupMsg();
    }



    public void setupDate() throws ParseException {
      //  picker.setMinDate(new Date().getDate());
        setContentView(R.layout.activity_add_goal);
        picker=(DatePicker)findViewById(R.id.datePicker1);
    }

    public void setupMsg(){
        message = (EditText)findViewById(R.id.enteredGoalTxt);
    }


    public void nextButtonHandler(View view) throws ParseException {
       // Toast.makeText(getApplicationContext(),"Next Button",Toast.LENGTH_LONG).show();
        strMssg= message.getText().toString();
        if (!strMssg.equals("")) {
            setupDate();
        } else
        Toast.makeText(getApplicationContext(),"Must include message",Toast.LENGTH_SHORT).show();


    }

    public void doneButtonHandler(View view,LayoutInflater inflater) {
        //add to the screen list, save as json?
        //Item is a date, subItem is a message

        /*Toast.makeText(this,strMssg+" "+picker.getDayOfMonth()+"/"+
                (picker.getMonth() + 1)+"/"+picker.getYear(),Toast.LENGTH_LONG).show();*/
        setContentView(content_main);
        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth();
        int   year = picker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);


        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", US);
        String formatedDate = sdf.format(calendar.getTime());

      //  Toast.makeText(this,formatedDate,Toast.LENGTH_SHORT).show();
        //put date and message to the hashmap

        associatedItem.put(formatedDate, strMssg);

       // Create an ArrayList that will hold the data.
        // Each HashMap entry will have a Key (First line) and a Value (Second line) of each one of the ListView Items.

        ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
       // String [] hashList = (String[]) associatedItem.entrySet().toArray();
      /*  HashMap<String, String> resultsMap = new HashMap<String, String>(2);
        resultsMap.put("First Line", formatedDate);
        resultsMap.put("Second Line",strMssg);
        listItems.add(resultsMap);
        */


      // associatedItem works, contains info
      // String out = associatedItem.toString();
      // System.out.println(out);



       //This iterator should go through exsisting HashMap and put everything to a listView
        Iterator it = associatedItem.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line",formatedDate);
            resultsMap.put("Second Line",strMssg);//pair.getValue().toString()
            listItems.add(resultsMap);
        }

        dialogLayout = inflater.inflate(R.layout.content_main, null);
        list = (ListView) dialogLayout.findViewById(R.id.list);

        //set up the adapter, upper text and bottom text
        SimpleAdapter adapter = new SimpleAdapter(this, listItems ,R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.textUp, R.id.text2});



        list.setAdapter(adapter);


    }
}
