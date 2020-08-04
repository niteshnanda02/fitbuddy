package com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.SessionsDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AerbocisHomeListRepository {

    static AerbocisHomeListRepository instance;
    List<SessionsDetails> sessionsDetails = new ArrayList<>();
    MutableLiveData<List<SessionsDetails>> detailslist = new MutableLiveData<>();
    private String TAG=this.getClass().getSimpleName();

    public static AerbocisHomeListRepository getInstance() {


        if (instance == null)
            instance = new AerbocisHomeListRepository();

        return instance;
    }

    public MutableLiveData<List<SessionsDetails>> getSessionDetails(String exercise) {
//        if (sessionsDetails.size() == 0)
            loadDetails(exercise);

        detailslist.setValue(sessionsDetails);

        return detailslist;
    }

    private void loadDetails(String exercise) {
        Date date = Calendar.getInstance().getTime();
        Date after_date=new Date(date.getTime()-1800*1000);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection(exercise)
                .whereGreaterThanOrEqualTo("timeS",after_date)
                .orderBy("timeS", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            sessionsDetails = task.getResult().toObjects(SessionsDetails.class);
                        }
                        detailslist.postValue(sessionsDetails);
                    }
                });
    }

}
