package com.FitBuddy.fitbuddy.TrainerPackage.TrainerRepository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.FitBuddy.fitbuddy.TrainerPackage.Model.TrainerSession;
import com.FitBuddy.fitbuddy.loginsignup.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TrainerListRepisitory {
    SharedPreferences sharedPreferences;
    static TrainerListRepisitory instance;
    MutableLiveData<List<TrainerSession>> mutableLiveTrainer = new MutableLiveData<>();
    List<TrainerSession> trainerSessionsList = new ArrayList<>();
    String trainer_email;
    private String TAG = this.getClass().getSimpleName();

    public static TrainerListRepisitory getInstance() {
        if (instance == null) {
            instance = new TrainerListRepisitory();
        }
        return instance;
    }

    public MutableLiveData<List<TrainerSession>> getTrainerDetails(Context context) {
        loadData(context);
        mutableLiveTrainer.setValue(trainerSessionsList);
        return mutableLiveTrainer;
    }

    private void loadData(Context context) {
        Date date = Calendar.getInstance().getTime();
        final Date after_date = new Date(date.getTime() - 1800 * 1000);

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        sharedPreferences = context.getSharedPreferences(LoginActivity.MYPREFERENCES, MODE_PRIVATE);
        trainer_email = sharedPreferences.getString("trainer_email", null);
        if (trainer_email != null) {
            firebaseFirestore.collection("trainers")
                    .document(trainer_email)
                    .collection("sessions")
                    .whereGreaterThanOrEqualTo("timeS",after_date)
                    .orderBy("timeS", Query.Direction.ASCENDING)
                    .get()

                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                trainerSessionsList = task.getResult().toObjects(TrainerSession.class);
                            }
                            mutableLiveTrainer.postValue(trainerSessionsList);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, e.getMessage() + " error");
                        }
                    });
        }
    }
}