package com.FitBuddy.fitbuddy.UserPackage.UserFragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.AvailableExerciseDetails;

import java.util.ArrayList;
import java.util.List;

public class AvailableExercise extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_exercise);
        initialize();
    }
        private void initialize() {
        List<AvailableExerciseDetails> list=new ArrayList<>();
            list.add(new AvailableExerciseDetails("","Ajay Kumar","","",""));
            list.add(new AvailableExerciseDetails("","Ajay Kumar","","",""));
            list.add(new AvailableExerciseDetails("","Ajay Kumar","","",""));
            list.add(new AvailableExerciseDetails("","Ajay Kumar","","",""));
            list.add(new AvailableExerciseDetails("","Ajay Kumar","","",""));
            list.add(new AvailableExerciseDetails("","Ajay Kumar","","",""));
//        recyclerView=(RecyclerView)findViewById(R.id.available_exercise_recycler_view);
//        AvailableExerciseAdapter adapter=new AvailableExerciseAdapter(this,list);
//        RecyclerView.LayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(adapter);
    }
}
