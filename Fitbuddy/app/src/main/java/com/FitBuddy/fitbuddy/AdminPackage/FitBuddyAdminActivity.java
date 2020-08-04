package com.FitBuddy.fitbuddy.AdminPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.DisplayVideoActivity;
import com.FitBuddy.fitbuddy.loginsignup.LoginActivity;
import com.FitBuddy.fitbuddy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

public class FitBuddyAdminActivity extends AppCompatActivity {
    private static final String NODE_USERS = "admin";
    private static final String TAG = "FitBuddyAdmin";
    private FirebaseAuth mAuth;

    private List<Users> usersList;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    DatabaseReference databaseReference;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_buddy_admin);
        mAuth = FirebaseAuth.getInstance();
        String login=getIntent().getStringExtra("login");
        if (login==null) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (task.isSuccessful()) {
                                String token = task.getResult().getToken();
                                savedToken(token);
                            } else {

                            }
                        }
                    });
        }
        loadUsers();
        findViewById(R.id.uploaded_videos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitBuddyAdminActivity.this, DisplayVideoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void savedToken(String token) {
        Users value= (Users) getIntent().getSerializableExtra("users");
        Users users = new Users( value.email,value.mobile,token, value.name,  null,null,null);

        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference(NODE_USERS);

        dbUsers.child(mAuth.getCurrentUser().getUid())
                .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(FitBuddyAdminActivity.this, "Token is saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadUsers() {
        progressBar = findViewById(R.id.admin_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        usersList = new ArrayList<>();
        recyclerView = findViewById(R.id.admin_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("users/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dbsnap : dataSnapshot.getChildren()) {
                        Users users = dbsnap.getValue(Users.class);
                        usersList.add(users);
                    }
                    AdminAdapter adminAdapter = new AdminAdapter(FitBuddyAdminActivity.this, usersList);
                    recyclerView.setAdapter(adminAdapter);
                } else {
                    Toast.makeText(FitBuddyAdminActivity.this, "No users found!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("database", databaseError.getMessage());

            }
        });
        System.out.println("list " + usersList.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sign_out:
                signout();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void signout() {
        Log.d(TAG, "sign out Account");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Log.d(TAG, "OK! Works fine!");
        Intent intent=new Intent(FitBuddyAdminActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
