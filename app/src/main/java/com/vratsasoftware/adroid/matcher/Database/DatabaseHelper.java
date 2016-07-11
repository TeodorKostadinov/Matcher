package com.vratsasoftware.adroid.matcher.Database;

import android.content.Context;

import com.vratsasoftware.adroid.matcher.cmn.User;

import java.util.ArrayList;

public class DatabaseHelper {

    private final String LEADERBOARD = "Leaderboard";
    private final String DATABASE_URL = "https://matcher-c12d9.firebaseio.com/";
    private final String USERNAME = "userName";
    private final String SCORE = "score";
    private final String TIME = "time";

    public DatabaseHelper(Context context){

    }


    public ArrayList<User> readUsersFromDatabase(){
        final ArrayList<User> users = new ArrayList<User>();
        return users;
    }

}
