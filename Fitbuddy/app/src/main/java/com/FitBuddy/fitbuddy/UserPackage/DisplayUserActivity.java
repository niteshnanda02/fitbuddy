package com.FitBuddy.fitbuddy.UserPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.model.Users;
import com.FitBuddy.fitbuddy.loginsignup.RegistrationDetails;

public class DisplayUserActivity extends AppCompatActivity {
    TextView name,mobile, email, age, height, weight, profession, level, focus, workout, time,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        initialize();

    }

    private void initialize() {
        name=findViewById(R.id.display_name);
        mobile = findViewById(R.id.display_number);
        email =findViewById(R.id.display_email);
        age=findViewById(R.id.display_age);
        height=findViewById(R.id.display_height);
        weight=findViewById(R.id.display_weight);
        profession=findViewById(R.id.display_profession);
        level=findViewById(R.id.display_level);
        focus=findViewById(R.id.display_focus);
        workout=findViewById(R.id.display_workout);
        time=findViewById(R.id.display_time);
        gender=findViewById(R.id.display_gender);
        String Name,Mobile,Email, Age, Height, Weight, Profession, Level, Focus, Workout, Time,Gender;
        Users users= (Users) getIntent().getSerializableExtra("users");
        Name=users.name;
        Mobile=users.mobile;
        Email=users.email;
        RegistrationDetails details=users.registrationDetails;
        Age=details.age;
        Gender=details.gender;
        Height=details.height;
        Weight=details.weight;
        Profession=details.profession;
        Level=details.proficiency_level;
        Category category=details.category;
        Focus=category.focus_area;
        Workout=category.workout;
        Time=category.workout_time;
        name.setText(Name);
        mobile.setText(Mobile);
        email.setText(Email);
        age.setText(Age);
        gender.setText(Gender);
        height.setText(Height);
        weight.setText(Weight);
        profession.setText(Profession);
        level.setText(Level);
        focus.setText(Focus);
        workout.setText(Workout);
        time.setText(Time);

    }
}
