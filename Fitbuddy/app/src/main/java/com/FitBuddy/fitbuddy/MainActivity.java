package com.FitBuddy.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.FitBuddy.fitbuddy.AdminPackage.AdminActivity;
import com.FitBuddy.fitbuddy.UserPackage.DisplayVideoActivity;

public class MainActivity extends AppCompatActivity {
    Button confirm_button;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirm_button = findViewById(R.id.confirm_button);
        radioGroup = findViewById(R.id.radio_group);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotonextScreen();
            }
        });
    }

    private void gotonextScreen() {
        int selectedID = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedID);
        if (selectedID == 1) {
            Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = null;
            if (radioButton.getId() == R.id.trainer_radiobutton) {
                intent = new Intent(this, AdminActivity.class);
            } else if (radioButton.getId() == R.id.user_radiobutton) {
                intent = new Intent(this, DisplayVideoActivity.class);
            }
            startActivity(intent);
        }

    }
}
