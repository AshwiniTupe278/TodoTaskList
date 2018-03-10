package com.ashwini.todotask.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ashwini.todotask.model.TaskModel;

import java.io.Console;

/**
 * Created by softdotcom-7 on 9/3/18.
 */

public class SqliteDBHelper extends SQLiteOpenHelper {
    // Constants
    public static final String DATABASE_NAME = "TASK.db";
    public static final String TABLE_NAME = "TaskTable";
    public static final String TASK_ID = "ID";
    public static final String TASK_NAME = "TaskName";
    public static final String TASK_STATUS = "TaskStatus";
    public static final String TASK_DESC = "TaskDesc";

    // Create DB to store To Do Tasks
    public SqliteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    // Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "create table if not exists " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, TaskName TEXT,  TaskStatus TEXT, TaskDesc TEXT)";
        db.execSQL(createTableQuery);
    }

    // Drop Table for new table creation
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert into Table
    public boolean insertInto(ContentValues cv) {

        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.insert(TABLE_NAME, null, cv);

        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Select * from Table i.e get all data
    public Cursor selectAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + TABLE_NAME;
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public Cursor getTask(String status)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * from " + TABLE_NAME + " WHERE " + TASK_STATUS +" = '" + status +"'";
        Log.d("query",query);
        Cursor results = db.rawQuery(query, null);

        return results;
    }

    // Update specific Task
    public Cursor updateTask(TaskModel td) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + TABLE_NAME
                + " SET "
                + TASK_NAME + "='" + td.getTaskName()
                + "', "
                + TASK_STATUS + "='" + td.getTaskStatus()
                + "', "
                + TASK_DESC + "='" + td.getTaskDescription()
                + "' WHERE " + TASK_ID + "='" + td.get_ID() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }

    // Delete specific Task
    public Cursor deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME
                + " WHERE "
                + TASK_ID + "='"
                + id + "'";
        Cursor result = db.rawQuery(query, null);
        return result;
    }
}
