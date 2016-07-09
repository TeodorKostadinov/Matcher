package com.vratsasoftware.adroid.matcher.game_logic;

import android.graphics.Color;

import com.vratsasoftware.adroid.matcher.R;

import java.util.Random;

public class Block {

    private static int color;
    private static Random r = new Random();

    public static int getRandomColor(){
        color = r.nextInt(4) + 1;
        switch(color){
            case 1:
                return R.color.red;
            case 2:
                return R.color.blue;
            case 3:
                return R.color.green;
            case 4:
                return R.color.yellow;
            default:
                return R.color.red;
        }
    }

}
