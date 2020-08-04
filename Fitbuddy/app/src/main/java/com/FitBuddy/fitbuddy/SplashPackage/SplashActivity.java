package com.FitBuddy.fitbuddy.SplashPackage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.TrainerPackage.TrainerActivity;
import com.FitBuddy.fitbuddy.UserPackage.FitBuddyUserActivity;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Designing.CirclePagerIndicatorDecoration;
import com.FitBuddy.fitbuddy.loginsignup.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private String TAG=this.getClass().getSimpleName();

    @Override
    protected void onStart() {
        super.onStart();

    }
    private RecyclerView recyclerView;
    final int speedControl=1100;
    final Handler handler=new Handler();
    List<Integer> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            passtoFitBuddyUserActivity();
        }
        sharedPreferences=getSharedPreferences(LoginActivity.MYPREFERENCES,MODE_PRIVATE);
        String email=sharedPreferences.getString("trainer_email",null);
        if (email!=null){
            Intent intent=new Intent(SplashActivity.this, TrainerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialize();

        final Runnable runnable=new Runnable() {
            int count=0;
            boolean flag=true;
            @Override
            public void run() {
                if (count<list.size()){
                    if (count==list.size()-1){
                        flag=false;
                    }else if (count==0){
                        flag=true;
                    }
                    if (flag)
                        count++;
                    else
                        count--;

                    recyclerView.smoothScrollToPosition(count);
                    handler.postDelayed(this,speedControl);
                }
            }
        };
        handler.postDelayed(runnable,speedControl);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            String referlink=deepLink.toString();
                            Log.e(TAG,referlink);
                            String referredUserID;
                            try{

                                referredUserID=referlink.substring(referlink.indexOf("?")+1);
                                Log.e(TAG,referredUserID);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }

    private void initialize() {

        list.add(R.drawable.splash1);
        list.add(R.drawable.splash2);
        list.add(R.drawable.splash3);        recyclerView=findViewById(R.id.splash_recycler_view);

        SplashAdapter adapter=new SplashAdapter(this,list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration());
    }

    public void taketoWelcome(View view) {
        Intent intent=new Intent(this,WelcomeActivity.class);
        startActivity(intent);
    }
    public void passtoFitBuddyUserActivity() {
        Intent intent = new Intent(SplashActivity.this, FitBuddyUserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
