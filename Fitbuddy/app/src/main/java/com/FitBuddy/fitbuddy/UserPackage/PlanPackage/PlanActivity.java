package com.FitBuddy.fitbuddy.UserPackage.PlanPackage;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Designing.CirclePagerIndicatorDecoration;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        init();
    }

    private void init() {
        List<PlanModel> planModelList=new ArrayList<>();
        planModelList.add(new PlanModel("Fitness Pack","Rs 500/-","Zumba\nFitness\nAerobics","17 Sessions",R.drawable.payment1));
        planModelList.add(new PlanModel("Yoga Pack","Rs 400/-","Yoga","15 Sessions",R.drawable.payment3));
        planModelList.add(new PlanModel("Kids Pack","Rs 900/-","Kids Exercise","15 Sessions",R.drawable.payment2));
        viewPager2=findViewById(R.id.viewpager);
        PlanAdapter planAdapter=new PlanAdapter(this,planModelList);
        viewPager2.setAdapter(planAdapter);
        viewPager2.addItemDecoration(new CirclePagerIndicatorDecoration());
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);

            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

    }
}