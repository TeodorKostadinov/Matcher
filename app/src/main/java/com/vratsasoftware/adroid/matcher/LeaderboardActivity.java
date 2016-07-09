package com.vratsasoftware.adroid.matcher;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vratsasoftware.adroid.matcher.Database.DatabaseHelper;
import com.vratsasoftware.adroid.matcher.cmn.CustomAdapter;
import com.vratsasoftware.adroid.matcher.cmn.User;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    RecyclerView recView;
    ArrayList<User> users;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        dbHelper = new DatabaseHelper(this);
        recView = (RecyclerView) findViewById(R.id.rec_view);
//        users = dbHelper.readUsersFromDatabase();
        users = (ArrayList<User>) getIntent().getExtras().get("users");
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(new CustomAdapter(users));
    }
}
