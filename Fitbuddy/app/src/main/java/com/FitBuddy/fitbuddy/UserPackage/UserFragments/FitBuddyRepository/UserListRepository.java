package com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyRepository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.UserSessionModel;
import com.FitBuddy.fitbuddy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserListRepository {
    static UserListRepository instance;
    Users users = new Users();
    List<UserSessionModel> userSessionModel = new ArrayList<>();
    List<UserSessionModel> userCompletedSessionModel = new ArrayList<>();
    MutableLiveData<Users> mutableLiveUser = new MutableLiveData<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String TAG = this.getClass().getSimpleName();


    public static UserListRepository getInstance() {
        if (instance == null)
            instance = new UserListRepository();

        return instance;
    }

    public MutableLiveData<Users> getUsersDetails() {
        loadData();
        mutableLiveUser.setValue(users);
        return mutableLiveUser;
    }

    private void loadData() {
        Date date = Calendar.getInstance().getTime();
        final Date after_date = new Date(date.getTime() - 1800 * 1000);

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, task.getResult().toString());
                            users = task.getResult().toObject(Users.class);
                            Log.d(TAG, users + "");
                            firebaseFirestore.collection("users")
                                    .document(mAuth.getCurrentUser().getUid())
                                    .collection("session_details_collection")
                                    .whereGreaterThanOrEqualTo("timeS", after_date)
                                    .orderBy("timeS", Query.Direction.ASCENDING)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                userSessionModel = task.getResult().toObjects(UserSessionModel.class);
                                                firebaseFirestore.collection("users")
                                                        .document(mAuth.getCurrentUser().getUid())
                                                        .collection("session_completed_collection")
                                                        .orderBy("timeS", Query.Direction.DESCENDING)
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    userCompletedSessionModel = task.getResult().toObjects(UserSessionModel.class);
                                                                }
                                                                users.setSessionCompletedList(userCompletedSessionModel);
                                                                users.setSessionsDetailsList(userSessionModel);
                                                                mutableLiveUser.postValue(users);

                                                            }
                                                        });
                                            }
                                        }
                                    });

                        }

                    }
                });


    }
}
