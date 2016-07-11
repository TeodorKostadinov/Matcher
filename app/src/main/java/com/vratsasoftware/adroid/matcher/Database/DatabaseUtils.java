package com.vratsasoftware.adroid.matcher.Database;

public class DatabaseUtils {

    private static String userID = "0";
    private static int userIDNumber = 0;

    private DatabaseUtils(){
    }


    public static String getUserID(){
        return userID;
    }

    public static void incrementUserID(){
        userIDNumber++;
        userID = String.valueOf(userIDNumber);
    }

}
