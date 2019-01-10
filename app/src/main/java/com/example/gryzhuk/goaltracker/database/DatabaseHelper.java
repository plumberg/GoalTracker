package com.example.gryzhuk.goaltracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gryzhuk.goaltracker.lib.Goal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "goaltracker_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table
        db.execSQL(Goal.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Goal.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertGoal(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Goal.COLUMN_NOTE, note);

        // insert row
        long id = db.insert(Goal.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    /*takes already existed note `id` and fetches the note object.*/
    public Goal getGoal(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Goal.TABLE_NAME,
                new String[]{Goal.COLUMN_ID, Goal.COLUMN_NOTE, Goal.COLUMN_TIMESTAMP},
                Goal.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Goal note = new Goal(
                cursor.getInt(cursor.getColumnIndex(Goal.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Goal.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Goal.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return note;
    }


    /* all the notes in descending order by timestamp.*/
    public List<Goal> getAllGoals() {
        List<Goal> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Goal.TABLE_NAME + " ORDER BY " +
                Goal.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Goal note = new Goal();
                note.setId(cursor.getInt(cursor.getColumnIndex(Goal.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Goal.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Goal.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getGoalsCount() {
        String countQuery = "SELECT  * FROM " + Goal.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /*Updating data again requires writable access. Below the note is updated by its `id`.*/
    public int updateGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Goal.COLUMN_NOTE, goal.getNote());

        // updating row
        return db.update(Goal.TABLE_NAME, values, Goal.COLUMN_ID + " = ?",
                new String[]{String.valueOf(goal.getId())});
    }

    public void deleteGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Goal.TABLE_NAME, Goal.COLUMN_ID + " = ?",
                new String[]{String.valueOf(goal.getId())});
        db.close();
    }
}
