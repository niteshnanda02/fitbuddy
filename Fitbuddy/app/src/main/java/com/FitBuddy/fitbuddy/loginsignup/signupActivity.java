package com.FitBuddy.fitbuddy.loginsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.FitBuddy.fitbuddy.AdminPackage.FitBuddyAdminActivity;
import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.TrainerPackage.TrainerActivity;
import com.FitBuddy.fitbuddy.UserPackage.FitBuddyUserActivity;
import com.FitBuddy.fitbuddy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class signupActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MYPREFERENCES = "mypref";
    SharedPreferences sharedPreferences;
    private static final String TAG = "SignupActivity";
    private EditText name, email, signup_token_id, mobile;
    private TextView signup_user, signup_admin;
    private static final String mysharepref="Mysharepref";
    private Button signup_btn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestoredb;
    private String trainer_email;
    boolean isExisting;
    ProgressBar progressBar;

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        if (mAuth.getCurrentUser()!=null) {
//            passtoFitBuddyActivity();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestoredb=FirebaseFirestore.getInstance();
        initialize();
    }

    private void initialize() {
        mobile = findViewById(R.id.signup_number);
//        signup_token_id = findViewById(R.id.signup_Token_Id);
//        signup_user = findViewById(R.id.signup_user);
//        signup_user.setOnClickListener(this);
//        signup_admin = findViewById(R.id.signup_admin);
//        signup_admin.setOnClickListener(this);
//        progressBar = findViewById(R.id.signup_progress_bar);
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        signup_btn = findViewById(R.id.signupwithemail);
        signup_btn.setOnClickListener(this);
    }

    private void signupwithemail(final String mobile, final String name, String email, String password, final boolean val) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(signupActivity.this, user.getEmail() + " success", Toast.LENGTH_SHORT).show();
                            //Admin user
                            Intent intent;
                            if (val) {
                                intent = new Intent(signupActivity.this, FitBuddyAdminActivity.class);
                                intent.putExtra("User", "admin");
                                intent.putExtra("name", name);
                                intent.putExtra("mobile", mobile);
                                startActivity(intent);
                                finish();
                            } else {
                                intent = new Intent(signupActivity.this, RegistrationDetailsActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("mobile", mobile);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(signupActivity.this, "Authentication failed. " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void passtoFitBuddyActivity() {
        Intent intent = new Intent(this, FitBuddyUserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.signup_user:
//                signup_admin.setVisibility(View.VISIBLE);
//                signup_token_id.setVisibility(View.INVISIBLE);
//                signup_user.setVisibility(View.INVISIBLE);
//                name.requestFocus();
//                break;
//            case R.id.signup_admin:
//                signup_user.setVisibility(View.VISIBLE);
//                signup_token_id.setVisibility(View.VISIBLE);
//                signup_admin.setVisibility(View.INVISIBLE);
//                signup_token_id.requestFocus();
//                break;
            case R.id.signupwithemail:
                String Name, Email, Password, Id_Token, Mobile;
                Mobile = mobile.getText().toString().trim();
                Name = name.getText().toString().trim();
                Email = email.getText().toString().trim();
                if (Name.isEmpty()) {
                    name.setError("Name required");
                    name.requestFocus();
                    break;
                }
                if (Mobile.isEmpty()) {
                    name.setError("Mobile required");
                    name.requestFocus();
                    break;
                }
                if (Email.isEmpty()) {
                    email.setError("Email Required");
                    email.requestFocus();
                    break;
                }
                boolean val = false;
                    signupwithmobile(Mobile, Name, Email, val);


                break;
        }
    }

    private void signupwithmobile(String mobile, final String name, final String email, final boolean val) {
        String code = "91";
        final String mobile_number="+"+code+mobile;
        alreadyBooked(mobile_number, new AlreadyBookedCallback() {
            @Override
            public void onCallback(boolean isAlreadyBooked) {
                if (isAlreadyBooked){
                    Intent intent=new Intent(signupActivity.this, TrainerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    sharedPreferences=getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("trainer_email",trainer_email);
                    editor.commit();
                    startActivity(intent);
                }else {
                    alreadyBooked(mobile_number, new AlreadyBookedCallback() {
                        @Override
                        public void onCallback(boolean isAlreadyBooked) {
                            Log.d(TAG, String.valueOf(isAlreadyBooked));
                            if (isAlreadyBooked){
                                //user is already registerd
                                Intent intent = new Intent(signupActivity.this, OTPActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("value", val);
                                intent.putExtra("login", "login");
                                Users users = new Users(null, mobile_number, null, null, null,null,null);
                                intent.putExtra("users", users);
                                startActivity(intent);

                            }else {

                                Intent intent=new Intent(signupActivity.this,OTPActivity.class);
                                Users users=new Users(email,mobile_number,null,name,null,null,null);
                                intent.putExtra("users",users);
                                intent.putExtra("val",val);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        }
                    },"users");

                }
            }
        },"trainers");

    }

    public void taketoLogin(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public interface AlreadyBookedCallback{
        void onCallback(boolean isAlreadyBooked);
    }
    public void alreadyBooked(final String mobilenumber, final AlreadyBookedCallback callback, final String collections){
        CollectionReference cref=firebaseFirestoredb.collection(collections);
        Query q1=cref.whereEqualTo("mobile",mobilenumber);
        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds:queryDocumentSnapshots
                ) {
                    trainer_email=ds.getString("email");
                    String mobile=ds.getString("mobile");
                    if (mobile.equals(mobilenumber))
                        isExisting=true;

                }
                Log.d(TAG,queryDocumentSnapshots.getDocuments().toString());
                callback.onCallback(isExisting);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,e.getMessage());
            }
        });
    }
}
