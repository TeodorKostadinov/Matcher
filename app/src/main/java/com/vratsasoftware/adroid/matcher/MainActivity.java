package com.vratsasoftware.adroid.matcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vratsasoftware.adroid.matcher.Database.DatabaseHelper;
import com.vratsasoftware.adroid.matcher.cmn.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnPlay;
    Button btnLeaderboard;
    DatabaseHelper dbHelper;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        users = dbHelper.readUsersFromDatabase();

        btnPlay = (Button) findViewById(R.id.btn_play);
        btnLeaderboard = (Button) findViewById(R.id.btn_leaderboard);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameplayActivity.class);
                startActivity(i);
            }
        });

        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LeaderboardActivity.class);
                i.putExtra("users", users);
                startActivity(i);
            }
        });
    }
}
