package com.FitBuddy.fitbuddy.UserPackage.UserFragments.Designing;

import com.FitBuddy.fitbuddy.R;

import java.util.HashMap;

public class MyImage {
    public static int drawable(String source){
        HashMap<String, Integer> map;
        map=new HashMap<>();
        map.put("Yoga", R.drawable.yog);
        map.put("Aerobics", R.drawable.aerob);
        map.put("Fitness", R.drawable.fit);
        return map.get(source);
    }
    public static int updrawable(String source){
        HashMap<String, Integer> map;
        map=new HashMap<>();
        map.put("yoga", R.drawable.yog);
        map.put("aerobics-zumba", R.drawable.aerob);
        map.put("strength_workout", R.drawable.fit);
        return map.get(source);
    }
    public static void main(String[] args) {

    }
}
