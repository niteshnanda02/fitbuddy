package com.FitBuddy.fitbuddy.TrainerPackage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.TrainerPackage.Model.TrainerSession;
import com.FitBuddy.fitbuddy.TrainerPackage.TrainerViewHolder.TrainerViewHolder;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TrainerActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String trainer_email;
    FirebaseFirestore firebaseFirestore;
    TrainerSession session = new TrainerSession();
    RecyclerView recyclerView;
    List<TrainerSession> trainerSessionsList = new ArrayList<>();
    private String TAG = this.getClass().getSimpleName();
    TrainerViewHolder trainerViewHolder;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        initialize();
    }

    private void initialize() {
        trainerViewHolder= ViewModelProviders.of(this).get(TrainerViewHolder.class);
        trainerViewHolder.init(this);
        trainerViewHolder.getLiveData().observe(this, new Observer<List<TrainerSession>>() {
            @Override
            public void onChanged(List<TrainerSession> trainerSessions) {
                Log.d(TAG, trainerSessions.size()+"");
                recyclerView = findViewById(R.id.trainer_recycler_view);
                recyclerView.setHasFixedSize(true);
                TrainerAdapter trainerAdapter = new TrainerAdapter(TrainerActivity.this, trainerSessions);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TrainerActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(trainerAdapter);
            }
        });
//        sharedPreferences = getSharedPreferences(LoginActivity.MYPREFERENCES, MODE_PRIVATE);
//        trainer_email = sharedPreferences.getString("trainer_email", null);
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        if (trainer_email != null) {
//            firebaseFirestore.collection("trainers")
//                    .document(trainer_email)
//                    .collection("sessions")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            TrainerSession trainerSession = new TrainerSession();
//                            for (QueryDocumentSnapshot snapshot :
//                                    task.getResult()) {
//                                Log.d(TAG, snapshot + "");
//                                trainerSession = snapshot.toObject(TrainerSession.class);
//                                trainerSessionsList.add(trainerSession);
//
//                            }
//
//
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(TAG, e.getMessage());
//                        }
//                    });
//        }
    }
}