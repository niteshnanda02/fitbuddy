package com.FitBuddy.fitbuddy.UserPackage.UserFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Adapter.AvailableExerciseAdapter;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.FitBuddyViewHolder.HomeViewHolder;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.SessionsDetails;

import java.util.ArrayList;
import java.util.List;

public class UserHomeCardClicked extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private java.lang.String exercise;
    TextView exercise_textView;
    List<SessionsDetails> YogasessionsDetailsList=new ArrayList<>();
    HomeViewHolder homeViewHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhomecardclicked);
        View view = findViewById(R.id.available_exercise_list);
        exercise=getIntent().getStringExtra("exercise");
        exercise_textView=findViewById(R.id.exercise_name);
        exercise_textView.setText(exercise);
        initialize(view);
    }

    private void initialize(final View view) {
        homeViewHolder= ViewModelProviders.of(this).get(HomeViewHolder.class);
        Log.d(TAG,exercise);
        homeViewHolder.init(exercise);
        homeViewHolder.getLiveDetails(exercise).observe(this, new Observer<List<SessionsDetails>>() {
            @Override
            public void onChanged(List<SessionsDetails> sessionsDetails) {
                Log.d("firestore_list",sessionsDetails.size()+"");
                recyclerView = (RecyclerView) view.findViewById(R.id.available_exercise_recycler_view);
                AvailableExerciseAdapter adapter = new AvailableExerciseAdapter(UserHomeCardClicked.this, sessionsDetails);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(UserHomeCardClicked.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);

            }
        });
//        final FirebaseFirestore yogadb=FirebaseFirestore.getInstance();
//        yogadb.collection(exercise)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            Log.d(TAG,task.getResult().toString());
//                            YogasessionsDetailsList=task.getResult().toObjects(SessionsDetails.class);
//                            Log.d("firestore_list",YogasessionsDetailsList.size()+"");
//                            recyclerView = (RecyclerView) view.findViewById(R.id.available_exercise_recycler_view);
//                            AvailableExerciseAdapter adapter = new AvailableExerciseAdapter(UserHomeCardClicked.this, YogasessionsDetailsList,null);
//                            RecyclerView.LayoutManager manager = new LinearLayoutManager(UserHomeCardClicked.this, LinearLayoutManager.VERTICAL, false);
//                            recyclerView.setLayoutManager(manager);
//                            recyclerView.setAdapter(adapter);
//                        }else {
//                            Log.d("firestore_error", java.lang.String.valueOf(task.getException()));
//                        }
//                    }
//                });
//

    }
}