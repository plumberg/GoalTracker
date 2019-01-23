package com.example.gryzhuk.goaltracker;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;

import com.example.gryzhuk.goaltracker.database.DatabaseHelper;
import com.example.gryzhuk.goaltracker.lib.Goal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    DatabaseHelper mDatabaseHelper;
  //  private ArrayAdapter adapter;
    private List<Map.Entry<String, Object>> mListItems;
    private Map<String, Object> mMapItems;
    private Cursor mCursor;
    private CustomAdapter mCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseHelper = new DatabaseHelper(this);
        setupToolbar();
        setupListAndAdapter ();
        setupFAB();
        copyDBToList ();



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


       private void setupListAndAdapter() {
           // both empty for now
           mMapItems = new LinkedHashMap<>();
           mListItems = new ArrayList<>();

           mCustomAdapter = new CustomAdapter(this, mListItems);

           mListView = findViewById(R.id.list);
           mListView.setAdapter(mCustomAdapter);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            copyDBToList ();
            mCustomAdapter.notifyDataSetChanged();
        }
    }

    private void copyDBToList() {
        mCursor = mDatabaseHelper.getData();
        mMapItems.clear ();
        if (mCursor.getCount() > 0) {
            // add items individually to map
            while (mCursor.moveToNext()) {
                mMapItems.put (mCursor.getString (2),
                        mCursor.getString (1));
            }

            // add all items to mListView
            mListItems.clear ();
            mListItems.addAll (mMapItems.entrySet ());
        }
    }
/*
    public void cursorToList(Cursor c) {
       listItems.clear();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                listItems.add(c.getString(1));
                listItems.add(c.getString(2));
                Log.d("Database updt","Added to list");
                Log.d("Database updt",c.getString(1));
                Log.d("Database updt",c.getString(2));
            }

        }

    }*/

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
                startActivityForResult(new Intent(MainActivity.this, AddGoal.class), 100);
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
        if (id == R.id.about_menu_item) {
            showInfoDialog(MainActivity.this,"About","GoalTracker, in process of developing.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void showInfoDialog (Context context, String strTitle, String strMsg)
    {
        // create the listener for the dialog
        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener ()
        {
            @Override
            public void onClick (DialogInterface dialog, int which)
            {
                //nothing needed to do here
            }
        };

        // Create the AlertDialog.Builder object
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder (context);

        // Use the AlertDialog's Builder Class methods to set the title, icon, message, et al.
        // These could all be chained as one long statement, if desired
        alertDialogBuilder.setTitle (strTitle);
        alertDialogBuilder.setIcon (R.mipmap.ic_launcher_round);
        alertDialogBuilder.setMessage (strMsg);
        alertDialogBuilder.setCancelable (true);
        alertDialogBuilder.setNegativeButton (context.getText (android.R.string.ok), listener);

        // Create and Show the Dialog
        alertDialogBuilder.show ();
    }


}
