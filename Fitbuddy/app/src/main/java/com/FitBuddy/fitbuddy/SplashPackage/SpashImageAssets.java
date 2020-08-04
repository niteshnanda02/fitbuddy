package com.FitBuddy.fitbuddy.SplashPackage;

import com.FitBuddy.fitbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class SpashImageAssets {
    private static final List<Integer> list=new ArrayList<Integer>(){
        {
            list.add(R.drawable.splash1);
            list.add(R.drawable.splash2);
            list.add(R.drawable.splash3);
        }};

    public static List<Integer> getList(){
        return list;
    }
}
