package com.ashwini.todotask.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ashwini.todotask.helper.SqliteDBHelper;

/**
 * Created by softdotcom-7 on 9/3/18.
 */

public class SplashActivity extends AppCompatActivity {
    SqliteDBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create Sqlite DB
        dbHelper = new SqliteDBHelper(this);
        // Go to Main Activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}