package com.FitBuddy.fitbuddy.loginsignup;

import android.content.Context;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String Token_ID = "12345";
    private static final String TAG = "LoginActivity";
    public static final String MYPREFERENCES = "mypref";
    EditText token_id, mobile_number;
    Button login_btn;
    TextView signup_btn, login_user, login_admin;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    private static final int RequestPermissionCode = 1;
    private FirebaseFirestore firebaseFirestoredb;
    boolean isExisting;
    private String trainer_email;

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            sharedPreferences = this.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
            boolean check = sharedPreferences.getBoolean("val", false);
            if (check) {
                passToFitBuddyAdminActivity();
            } else
                passtoFitBuddyUserActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        firebaseFirestoredb=FirebaseFirestore.getInstance();
        initialize();
    }

    private void initialize() {
        mobile_number = findViewById(R.id.login_mobile_number);
//        token_id = findViewById(R.id.login_Token_Id);
//        login_user = findViewById(R.id.login_user);
//        login_user.setOnClickListener(this);
//        login_admin = findViewById(R.id.login_admin);
//        login_admin.setOnClickListener(this);
        progressBar = findViewById(R.id.login_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        login_btn = findViewById(R.id.login_button);
//        signup_btn = findViewById(R.id.login_signup_button);
        login_btn.setOnClickListener(this);
//        signup_btn.setOnClickListener(this);
    }

    private void signup() {
        Intent intent=new Intent(LoginActivity.this, signupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void loginwithemail(String email, String pass, final boolean val) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, user.getEmail() + " success", Toast.LENGTH_SHORT).show();
                            //Admin user
                            Intent intent;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("val", val);
                            editor.commit();
                            if (val) {
                                passToFitBuddyAdminActivity();
                            } else {
                                passtoFitBuddyUserActivity();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);

                        // ...
                    }
                });
    }

    public void passtoFitBuddyUserActivity() {
        Intent intent = new Intent(LoginActivity.this, FitBuddyUserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void passToFitBuddyAdminActivity() {
        Intent intent = new Intent(LoginActivity.this, FitBuddyAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.login_user:
//                token_id.setVisibility(View.INVISIBLE);
//                login_admin.setVisibility(View.VISIBLE);
//                login_user.setVisibility(View.INVISIBLE);
//                mobile_number.requestFocus();
//                break;
//            case R.id.login_admin:
//                token_id.setVisibility(View.VISIBLE);
//                login_user.setVisibility(View.VISIBLE);
//                login_admin.setVisibility(View.INVISIBLE);
//                token_id.requestFocus();
//                break;
            case R.id.login_button:
                String Email, Password, Id_Token, Mobile_number;
                Mobile_number = mobile_number.getText().toString().trim();

                if (Mobile_number.length() < 10) {
                    mobile_number.setError("Mobile number is less than 10");
                    mobile_number.requestFocus();
                    break;
                }
                progressBar.setVisibility(View.VISIBLE);
                boolean val = false;
                loginwithMobile(Mobile_number, val);
                break;

        }
    }

    private void loginwithMobile(final String number, final boolean val) {
        final String mobile_number = "+91" + number;
        String Node = "";
        if (val) {
            Node = "admin";
        } else
            Node = "users";
        alreadyBooked(mobile_number, new AlreadyBookedCallback() {
            @Override
            public void onCallback(boolean isAlreadyBooked) {
                if (isAlreadyBooked){
                    Intent intent=new Intent(LoginActivity.this, TrainerActivity.class);
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
                                Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("value", val);
                                intent.putExtra("login", "login");
                                Users users = new Users(null, mobile_number, null, null, null,null,null);
                                intent.putExtra("users", users);
                                startActivity(intent);

                            }else {
                                Toast.makeText(LoginActivity.this, "User is not registered, click on sign up", Toast.LENGTH_SHORT).show();
                                signup();

                            }
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    },"users");

                }
            }
        },"trainers");
       //        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Node);
//        reference.orderByChild("mobile").equalTo(mobile_number).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue() != null) {
//                    //user is already registerd
//                    Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.putExtra("value", val);
//                    intent.putExtra("login", "login");
//                    Users users = new Users(null, mobile_number, null, null, null);
//                    intent.putExtra("users", users);
//                    startActivity(intent);
//
//                } else {
//                    Toast.makeText(LoginActivity.this, "User is not registered, click on sign up", Toast.LENGTH_SHORT).show();
//                    signup();
//                }
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    public interface AlreadyBookedCallback{
        void onCallback(boolean isAlreadyBooked);
    }
    public void alreadyBooked(final String mobilenumber, final AlreadyBookedCallback callback,final String collections){
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
