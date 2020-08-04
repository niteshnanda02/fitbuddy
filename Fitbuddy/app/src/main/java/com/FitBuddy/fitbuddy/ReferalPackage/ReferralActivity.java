package com.FitBuddy.fitbuddy.ReferalPackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.FitBuddy.fitbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class ReferralActivity extends AppCompatActivity {
    Button refer_button;
    private String TAG=this.getClass().getSimpleName();
    FirebaseAuth mAuth;
    ProgressBar referral_progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);
        referral_progBar=findViewById(R.id.referral_progressBar);
        refer_button=findViewById(R.id.refer_button);
        refer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLink();
            }
        });
        mAuth=FirebaseAuth.getInstance();
    }

    private void createLink() {
        Log.e(TAG,"create link");
        referral_progBar.setVisibility(View.VISIBLE);
//        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
//                .setLink(Uri.parse("https://fitbuddy.co.in/"))
//                .setDomainUriPrefix("https://fitbuddy.page.link")
//                // Open links with this app on Android
//                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
//                .buildDynamicLink();
//
//        Uri dynamicLinkUri = dynamicLink.getUri();
//        Log.e(TAG,dynamicLinkUri+"");
//        //url :- https://fitbuddy.page.link?apn=com.FitBuddy.fitbuddy&link=https%3A%2F%2Ffitbuddy.co.in%2F
        //manual link

        String share_link="https://fitbuddy.page.link?"+
                "link=https://fitbuddy.co.in/?"+mAuth.getCurrentUser().getUid()+
                "&apn="+getPackageName()+
                "&st="+"FitBuddy"+
                "&sd="+"You both will get 30% off in your subscription plan"+
                "&si="+"https://lh3.googleusercontent.com/GzwMCv019RklvC0MN_ENH6xt7tyZBPjQQhDwNaxUisD0Pd7pL0YstnNrGDSoys6pFP8=s180-rw";

        //shorted the url

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
//                .setLongLink(dynamicLinkUri)
                .setLongLink(Uri.parse(share_link))
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Log.e(TAG,shortLink+"");
                            Intent intent=new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT,shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);
                            referral_progBar.setVisibility(View.GONE);

                        } else {
                            // Error
                            // ...
                        }
                    }
                });
    }

}