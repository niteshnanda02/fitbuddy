package com.FitBuddy.fitbuddy.UserPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.ReferalPackage.ReferralActivity;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.UserAddFragment;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.UserDetails.UsersDetailsFragment;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.UserHomeFragment;
import com.FitBuddy.fitbuddy.loginsignup.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;
import java.util.List;

public class FitBuddyUserActivity extends AppCompatActivity implements RatingDialogListener {
    private final String TAG = this.getClass().getSimpleName();
    private FirebaseAuth mAuth;
    private DrawerLayout user_drawerLayout;
    private ActionBarDrawerToggle user_actionBarDrawerToggle;
    private NavigationView user_navigationView;
    private String token;
    private BottomNavigationView bottomNavigationView;
    private Fragment selected_fragment;
    private FloatingActionButton floatingActionButton;
    private List<String> list = Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!");

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        FirebaseDynamicLinks.getInstance()
//                .getDynamicLink(getIntent())
//                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
//                    @Override
//                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
//                        // Get deep link from result (may be null if no link is found)
//                        Uri deepLink = null;
//                        if (pendingDynamicLinkData != null) {
//                            deepLink = pendingDynamicLinkData.getLink();
//                            Log.e(TAG,"My refer link "+deepLink);
//                            String user_uid;
//                            try{
//                                user_uid=deepLink.toString();
//                                user_uid=user_uid.substring(user_uid.lastIndexOf("?")+1);
//                                Log.e(TAG,user_uid);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//
//
//                        // Handle the deep link. For example, open the linked
//                        // content, or apply promotional credit to the user's
//                        // account.
//                        // ...
//
//                        // ...
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "getDynamicLink:onFailure", e);
//                    }
//                });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_buddy_user);
        mAuth = FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("updates");


        initialize();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new UserHomeFragment()).commit();
    }

    private void initialize() {
        bottomNavigationView = findViewById(R.id.bottom_bar_navigation);
        bottomNavigate();
        drawer();
        floatingActionButton = findViewById(R.id.user_home_floating_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_fragment = new UserAddFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selected_fragment).commit();
            }
        });
    }

    private void bottomNavigate() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
//                        floatingActionButton.setVisibility(View.GONE);
                        selected_fragment = new UserHomeFragment();
                        break;
                    case R.id.user:
//                        floatingActionButton.setVisibility(View.VISIBLE);
                        selected_fragment = new UsersDetailsFragment();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selected_fragment).commit();
                return true;

            }
        });
    }

    private void drawer() {
        user_drawerLayout = findViewById(R.id.user_drawer_layout);
        user_actionBarDrawerToggle = new ActionBarDrawerToggle(this, user_drawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        user_drawerLayout.addDrawerListener(user_actionBarDrawerToggle);
        user_actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user_navigationView = findViewById(R.id.user_navigationView);
        user_navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id) {
//                    case R.id.user_menu:
//                        Toast.makeText(FitBuddyUserActivity.this, "User menu", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.user_video:
//                        Toast.makeText(FitBuddyUserActivity.this, "Upload video", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(FitBuddyUserActivity.this, AdminActivity.class));
//                        break;
//                    case R.id.user_subscription:
//                        Toast.makeText(FitBuddyUserActivity.this, "Subscription", Toast.LENGTH_SHORT).show();
//                        break;
                    case R.id.user_logout:
                        Toast.makeText(FitBuddyUserActivity.this, "LogOut", Toast.LENGTH_SHORT).show();
                        signout();
                        break;
                    case R.id.user_privacy_policy:
                        intent = new Intent(FitBuddyUserActivity.this, PrivacyPolicyActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.share_invite:
                        intent = new Intent(FitBuddyUserActivity.this, ReferralActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.feedback:
                        openfeedbackform();
                        break;

                    default:
                        return true;

                }
                user_drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void openfeedbackform() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(list)
                .setDefaultRating(2)
                .setTitle("Rate this session")
                .setDescription("Please select some stars and give your feedback")
                .setCommentInputEnabled(true)
                .setStarColor(R.color.colorPrimary)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.titleTextColor)
                .setDescriptionTextColor(R.color.contentTextColor)
                .setHint("Please write your feedback here ...")
                .setHintTextColor(R.color.hintTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.backgroundCommentColor)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(FitBuddyUserActivity.this)
                .show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //for drawer
        if (user_actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;


        return super.onOptionsItemSelected(item);

    }

    private void signout() {
        Log.d(TAG, "sign out Account");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Log.d(TAG, "OK! Works fine!");
        Intent intent = new Intent(FitBuddyUserActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {
        Log.e(TAG, list.get(--i) + " " + s);
    }
}