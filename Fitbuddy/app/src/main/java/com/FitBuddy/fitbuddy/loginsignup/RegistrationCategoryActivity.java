package com.FitBuddy.fitbuddy.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.Category;
import com.FitBuddy.fitbuddy.UserPackage.FitBuddyUserActivity;
import com.FitBuddy.fitbuddy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class RegistrationCategoryActivity extends AppCompatActivity {
    private static final String NODE_USERS = "users";
    private static final String TAG = "RegistrationActivity";
    private EditText other_workout,other_time;
    RadioGroup focus, workout_time;

    CheckBox workout1, workout2, workout3, workout4;
    FloatingActionButton floatingActionButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestoredb;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_category);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestoredb=FirebaseFirestore.getInstance();
        initialize();
    }

    private void initialize() {
        other_workout=findViewById(R.id.other_workout);
        other_time=findViewById(R.id.other_time);
        focus = findViewById(R.id.focus_radio_group);

        workout_time = findViewById(R.id.time_radio_group);

        workout_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton time_radioButton = findViewById(checkedId);
                if (time_radioButton.getId() == R.id.time5){
                    other_time.setVisibility(View.VISIBLE);
                }else
                    other_time.setVisibility(View.INVISIBLE);
            }
        });
        workout1 = findViewById(R.id.workout1);
        workout2 = findViewById(R.id.workout2);
        workout3 = findViewById(R.id.workout3);
        workout4 = findViewById(R.id.workout4);
        workout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workout4.isChecked()){
                    other_workout.setVisibility(View.VISIBLE);
                }else {
                    other_workout.setVisibility(View.INVISIBLE);

                }
            }
        });
        floatingActionButton = findViewById(R.id.registration_floating_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FitbuddyuserScreen();
            }
        });
    }

    private void FitbuddyuserScreen() {
        String focus_selected = "", workouttime = "", workout = "";
        //focus area
        int selected_focus = focus.getCheckedRadioButtonId();
        RadioButton focus_radioButton = findViewById(selected_focus);
        if (selected_focus == -1) {
            Toast.makeText(this, "Select any focus area", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (focus_radioButton.getId() == R.id.focus1) {
                focus_selected = getResources().getString(R.string.focus1);
            } else if (focus_radioButton.getId() == R.id.focus2) {
                focus_selected = getResources().getString(R.string.focus2);
            } else if (focus_radioButton.getId() == R.id.focus3) {
                focus_selected = getResources().getString(R.string.focus3);

            }

        }


        //workout
        if (workout1.isChecked() || workout2.isChecked() || workout3.isChecked() || workout4.isChecked()) {
            if (workout1.isChecked()) {
                workout += getResources().getString(R.string.workout1) + "\n";
            }
            if (workout2.isChecked()) {
                workout += getResources().getString(R.string.workout2) + "\n";
            }
            if (workout3.isChecked()) {
                workout += getResources().getString(R.string.workout3) + "\n";
            }
            if (workout4.isChecked()) {
                String workoutother=other_workout.getText().toString().trim();
                if(workoutother.isEmpty()){
                    other_workout.setError("Enter some value");
                    other_workout.requestFocus();
                    return;
                }
                workout += workoutother + "\n";
            }
        } else {
            Toast.makeText(this, "Select any workout", Toast.LENGTH_SHORT).show();
            return;
        }

        //workout time
        int selected_time = workout_time.getCheckedRadioButtonId();
        RadioButton time_radioButton = findViewById(selected_time);
        if (selected_time == -1) {
            Toast.makeText(this, "Select any workout time", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (time_radioButton.getId() == R.id.time1) {
                workouttime = getResources().getString(R.string.time1);
            } else if (time_radioButton.getId() == R.id.time2) {
                workouttime = getResources().getString(R.string.time2);
            } else if (time_radioButton.getId() == R.id.time3) {
                workouttime = getResources().getString(R.string.time3);
            } else if (time_radioButton.getId() == R.id.time4) {
                workouttime = getResources().getString(R.string.time4);
            } else if (time_radioButton.getId() == R.id.time5) {
                String timeother=other_time.getText().toString().trim();
                if(timeother.isEmpty()){
                    other_time.setError("Enter some value");
                    other_time.requestFocus();
                    return;
                }
                workouttime = timeother;
            }
        }

        final String mobile_number = mAuth.getCurrentUser().getPhoneNumber();
        Category category = new Category(focus_selected, workout, workouttime);
        final Users values = (Users) getIntent().getSerializableExtra("users");
        values.registrationDetails.category = category;

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult
                        .getToken();
                Users users = new Users(values.email, mobile_number, token, values.name, values.registrationDetails,null,null);

                firebaseFirestoredb.collection("users")
                        .document(mAuth.getCurrentUser().getUid())
                        .set(users)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot added ");

                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference(NODE_USERS);
                dbUsers.child(mAuth.getCurrentUser().getUid())

                        .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationCategoryActivity.this, "Token is saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationCategoryActivity.this, FitBuddyUserActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "ingreso a deleteAccount");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "OK! Works fine!");
                    Intent intent = new Intent(RegistrationCategoryActivity.this, signupActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Log.w(TAG, "Something is wrong!");
                }
            }
        });
    }
}
