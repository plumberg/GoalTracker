package com.example.gryzhuk.goaltracker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.gryzhuk.goaltracker.database.DatabaseHelper;
import com.example.gryzhuk.goaltracker.lib.Goal;

import java.util.ArrayList;

//import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    //   private ArrayList<HashMap<String, String>> listItems;
    private ListAdapter adapter;
    private final String STATE_LIST = "STATE LIST";
    DatabaseHelper mDatabaseHelper;
    private ArrayList<Goal> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseHelper = new DatabaseHelper(this);
        setupToolbar();
       // setupListAndAdapter();
        setupListview();
        setupFAB();


    }

    public void addGoal(String data, String goal){
        mDatabaseHelper.insertGoal(data,goal);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // call the super-class's method to save fields, etc.
        super.onSaveInstanceState(outState);

        // outState.putString(STATE_LIST, listItems);


    }

    private void setupListview() {
        listItems = new ArrayList<>();
        list = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this,R.layout.list_item,listItems);
        list.setAdapter(adapter);

    }

    private void setupListAndAdapter() {
        listItems = new ArrayList<>();
        list = findViewById(R.id.list);

        String[] from = {"First Line", "Second Line"};
        int[] to = {R.id.textUp, R.id.text2};
        // create the adapter for the ListView and bind it to the ListView...
  /*      adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                from, to);


        list.setAdapter(adapter);
        */
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void setupFAB() {
        final com.github.clans.fab.FloatingActionButton fabAdd;
        final com.github.clans.fab.FloatingActionButton fabRemove;
        final com.github.clans.fab.FloatingActionButton fabWidget;

        fabAdd = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.add_to_list);  //changed from  R.id.fab button
        fabRemove = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.remove_from_list);
        fabWidget = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.create_widget);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddGoal.class);
                startActivityForResult(new Intent(MainActivity.this, AddGoal.class), 100);
               //  startActivity(i);
            }
        });

        fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Remove clicked", Toast.LENGTH_SHORT).show();
                //here add option to click on list_item which removes it
            }
        });

        fabWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here open new screen for creating widget

                Intent i = new Intent(MainActivity.this, AppWidgetGoal.class);
                // startActivity(i);
                onNewIntent(i);
            }
        });


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            //listItems.addAll((ArrayList<HashMap<String, String>>) bundle.get("LIST_DATA"));
            listItems.addAll((ArrayList<Goal>) bundle.get("LIST_DATA"));
          //  adapter.notifyDataSetChanged();
            adapter.notifyAll();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
