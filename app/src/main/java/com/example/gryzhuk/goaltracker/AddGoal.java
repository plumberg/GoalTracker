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
    // create arraylist os string
    // json storing date, message
    private ListView list;
    DatabaseHelper mDatabaseHelper;
    private EditText message;
    private DatePicker picker;
    private HashMap<String, String> associatedItem;
    private String strMssg;
    ArrayList<String> listData;
    private ArrayList<HashMap<String, String>> listItems;
    private long id = -1;

    private ArrayList<Goal> notesList = new ArrayList<Goal>();

    protected void onCreate(Bundle savedInstanceState) {//LayoutInflater inflater
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_widget);
        strMssg = "";
        associatedItem = new HashMap<String, String>();
        mDatabaseHelper = new DatabaseHelper(this);
        list = (ListView) findViewById(R.id.list);
        setupMsg();
    }

    public void setupMsg() {
        message = (EditText) findViewById(R.id.enteredWidgetText);
    }


    public void doneButtonHandler(View view) {
        //Item is a date, subItem is a message

        /*Toast.makeText(this,strMssg+" "+picker.getDayOfMonth()+"/"+
                (picker.getMonth() + 1)+"/"+picker.getYear(),Toast.LENGTH_LONG).show();*/
        strMssg = message.getText().toString();
        if (!strMssg.equals("")) {
            picker = (DatePicker) findViewById(R.id.datePickerWidget);
        } else
            Toast.makeText(getApplicationContext(), "Must include message", Toast.LENGTH_SHORT).show();

        setContentView(content_main);
        int day = picker.getDayOfMonth();
        int month = picker.getMonth();
        int year = picker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", US);
        String formatedDate = sdf.format(calendar.getTime());

        id = mDatabaseHelper.insertGoal(formatedDate, strMssg);
        Log.d("Database updt","Finish addgoal");
        // associatedItem.put(formatedDate, strMssg);
        // associatedItem works, contains info

        // String out = associatedItem.toString();
    /*    // System.out.println(out);
        listItems = new ArrayList<HashMap<String, String>>();

        //This iterator should go through exsisting HashMap and put everything to a listView
        Iterator it = associatedItem.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", formatedDate);
            resultsMap.put("Second Line", strMssg);//pair.getValue().toString()
            listItems.add(resultsMap);
        }

*/


        Cursor data = mDatabaseHelper.getData();
        listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
            listData.add(data.getString(2));
            Log.d("Database updt","listdata added ");
        }

           /* ListAdapter adapter = new ArrayAdapter<>(this,R.layout.list_item,listData);
            list.setAdapter(adapter);*/


        finish();


    }

    @Override
    public void finish() {
        //  create an Intent, which has a Bundle
        //  To this bundle, we can add whatever data we want to send back to the calling Activity
        //Intent intentResults = new Intent();
        // Add some sample                    data
        //  intentResults.putExtra("LIST_DATA", listData);
        //intentResults.putExtra("LIST_DATA", listItems);
        //  Set the result to OK and to pass back this Intent;
        // if this is not set then it assumes it was NOT Ok.
        // if the second argument is blank then nothing will be sent back

       /* if (listData.isEmpty()) {
            setResult(RESULT_CANCELED, intentResults);
        } else {
            setResult(RESULT_OK, intentResults);
        }
*/  Log.d("Database updt","Finish addgoal");
        if (id != -1) {
            setResult(RESULT_OK);
        }
        // Do whatever else the parent class would normally do in its finish() method
        super.finish();
    }


}
