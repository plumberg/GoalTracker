package com.example.gryzhuk.goaltracker;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
//import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        setupToolbar();
        setupFAB();


    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void setupFAB() {
        final com.github.clans.fab.FloatingActionButton fab;
        fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.add_to_list);  //changed from  R.id.fab button

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddGoal.class);
                startActivityForResult (new Intent(MainActivity.this,AddGoal.class),100);

               // startActivity(new Intent(MainActivity.this,AddGoal.class));
            }
        });
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            ArrayList<HashMap<String, String>> listItems = (ArrayList<HashMap<String, String>>) bundle.get("LIST_DATA");


            String[] from = {"First Line", "Second Line"};
            int[] to = {R.id.textUp, R.id.text2};
            // create the adapter for the ListView and bind it to the ListView...
            SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                    from, to);

            list.setAdapter(adapter);
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
