package com.vratsasoftware.adroid.matcher.Database;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUtils {

    private static FirebaseDatabase instance;
    private static String userID = "0";
    private static int userIDNumber = 0;

    private DatabaseUtils(){
    }

    public static FirebaseDatabase getInstance(){
        if(instance == null){
            instance = FirebaseDatabase.getInstance();
        }
        return instance;
    }

    public static String getUserID(){
        return userID;
    }

    public static void incrementUserID(){
        userIDNumber++;
        userID = String.valueOf(userIDNumber);
    }

//    private DatabaseUtils(Context context){
//        initDB(context);
//    }
//
//
//    private DatabaseHelper initDB(Context context){
//        if(db == null){
//            db = new DatabaseHelper(context);
//        }
//        return db;
//    }

}
