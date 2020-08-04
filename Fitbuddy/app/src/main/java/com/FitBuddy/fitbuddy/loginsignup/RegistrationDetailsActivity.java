package com.FitBuddy.fitbuddy.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "RegistrationDetails";
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private EditText editText_age,editText_height,editText_weight;
    private Spinner spinner_profession,spinner_proficiency_level;
    Button reg_details_btn;

    String profession="",proficiency_level="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_details);

        initialize();
    }

    private void initialize() {
        radioGroup=findViewById(R.id.registration_radiogroup);
        editText_age=findViewById(R.id.registration_age);
        editText_height=findViewById(R.id.registration_height);
        editText_weight=findViewById(R.id.registration_weight);
        spinner_profession=findViewById(R.id.registration_profession_spinner);
        spinner_proficiency_level=findViewById(R.id.registration_proficiency_spinner);
        reg_details_btn=findViewById(R.id.registration_details_btn);
        reg_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataToDatabase();
            }
        });
        initialize_spinner();
    }
    private void initialize_spinner(){
        //Profession
        spinner_profession.setOnItemSelectedListener(this);
        ArrayAdapter<String> profession_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.profession_array));
        profession_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_profession.setAdapter(profession_adapter);
        profession=String.valueOf(spinner_profession.getSelectedItem());

        //Proficiency level
        spinner_proficiency_level.setOnItemSelectedListener(this);
        ArrayAdapter<String> proficieny_level_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.proficiency_level));
        proficieny_level_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_proficiency_level.setAdapter(proficieny_level_adapter);
        proficiency_level=String.valueOf(spinner_proficiency_level.getSelectedItem());


    }

    private void addDataToDatabase() {
        String gender="";
        int selectedId=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(this, "Select the gender please", Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (radioButton.getId()==R.id.male_radiobutton)
                gender="Male";
            else if (radioButton.getId()==R.id.female_radiobutton)
                gender="Female";
        }
        String age=editText_age.getText().toString().trim();
        String height=editText_height.getText().toString().trim();
        String weight=editText_weight.getText().toString().trim();
        if (age.isEmpty()){
            editText_age.setError("Enter your age");
            editText_age.requestFocus();
            return;
        }
        if (height.isEmpty()){
            editText_height.setError("Enter your height");
            editText_height.requestFocus();
            return;
        }
        if (weight.isEmpty()){
            editText_weight.setError("Enter your weight");
            editText_weight.requestFocus();
            return;
        }

        RegistrationDetails details=new RegistrationDetails(gender,age,height,weight,profession,proficiency_level,null);
        Intent intent=new Intent(this,RegistrationCategoryActivity.class);
        Users users= (Users) getIntent().getSerializableExtra("users");
        users.registrationDetails=details;
        intent.putExtra("users",users);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    Log.d(TAG,"OK! Works fine!");
                    Intent intent=new Intent(RegistrationDetailsActivity.this, signupActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Log.w(TAG,"Something is wrong!");
                }
            }
        });
    }
}
