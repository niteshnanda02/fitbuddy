package com.FitBuddy.fitbuddy.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.FitBuddy.fitbuddy.AdminPackage.FitBuddyAdminActivity;
import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.FitBuddyUserActivity;
import com.FitBuddy.fitbuddy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class OTPActivity extends AppCompatActivity {
    private OtpTextView otpTextView;
    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView mobile_number;
    boolean val;
    String token = "";
    Users users;
    private FirebaseFirestore firebaseFirestoredb;
    private String TAG=this.getClass().getSimpleName();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpTextView = findViewById(R.id.otp_view);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestoredb = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.otp_progressbar);
        mobile_number = findViewById(R.id.display_mobile_no);
        val = getIntent().getBooleanExtra("val", false);
        users = (Users) getIntent().getSerializableExtra("users");
        String phonenumber = users.mobile;
        mobile_number.setText(getString(R.string.sendotp) + " " + phonenumber);
        sendVerificationCode(phonenumber);

        final String[] code = new String[1];
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                code[0] = otp;
            }
        });
        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                verifyCode(code[0]);

            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        final String login = getIntent().getStringExtra("login");
        final boolean value = getIntent().getBooleanExtra("value", false);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(OTPActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            Intent intent;
                            if (val || value) {
                                intent = new Intent(OTPActivity.this, FitBuddyAdminActivity.class);
                                intent.putExtra("User", "admin");
                                intent.putExtra("users", users);
                                intent.putExtra("login", login);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                if (login == null) {
                                    intent = new Intent(OTPActivity.this, RegistrationDetailsActivity.class);
                                    intent.putExtra("users", users);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    updateToken();

                                }
                            }

                        } else {
                            Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void updateToken() {
        Log.d(TAG,mAuth.getCurrentUser().getUid());
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                firebaseFirestoredb.collection("users")
                        .document(mAuth.getCurrentUser().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Users users = task.getResult().toObject(Users.class);
                                    users.token = token;
                                    firebaseFirestoredb.collection("users")
                                            .document(mAuth.getCurrentUser().getUid())
                                            .set(users)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Log.i("User_Token_Updated", "updated token");
                                                    Intent intent;
                                                    intent = new Intent(OTPActivity.this, FitBuddyUserActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);

                                                }
                                            });
                                }
                            }
                        });
//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                reference = reference.child(mAuth.getCurrentUser().getUid());
//                final DatabaseReference finalReference = reference;
//                reference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Users users = dataSnapshot.getValue(Users.class);
//                        Log.i("users_name", users.      name);
//                        Log.i("users_mobile", users.mobile);
//                        users.token = token;
//                        Log.i("user.token",users.token);
//                        finalReference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Log.i("User_Token_Updated", "updated token");
//                                    Intent intent;
//                                    intent = new Intent(OTPActivity.this, FitBuddyUserActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//                                }
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
            }
        });


    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otpTextView.setOTP(code);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("otperror", e.getMessage());
            progressBar.setVisibility(View.INVISIBLE);

        }
    };
}