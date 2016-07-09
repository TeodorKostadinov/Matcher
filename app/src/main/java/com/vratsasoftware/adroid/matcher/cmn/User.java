package com.vratsasoftware.adroid.matcher.cmn;

public class User {

    private String name;
    private int score;
    private double time;

    public User(){
    }

    public User(String name, int score, double time){
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public double getTime(){
        return this.time;
    }

}
