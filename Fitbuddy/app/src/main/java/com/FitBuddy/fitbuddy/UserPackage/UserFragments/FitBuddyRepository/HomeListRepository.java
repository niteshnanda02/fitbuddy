package com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.SessionsDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeListRepository {
    static HomeListRepository instance;
    List<SessionsDetails> sessionsDetails = new ArrayList<>();
    MutableLiveData<List<SessionsDetails>> detailslist = new MutableLiveData<>();

    public static HomeListRepository getInstance() {


        if (instance == null)
            instance = new HomeListRepository();

        return instance;
    }

    public MutableLiveData<List<SessionsDetails>> getSessionDetails() {
//        if (sessionsDetails.size() == 0)
            loadDetails();

        detailslist.setValue(sessionsDetails);

        return detailslist;
    }

    private void loadDetails() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("datas")
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
