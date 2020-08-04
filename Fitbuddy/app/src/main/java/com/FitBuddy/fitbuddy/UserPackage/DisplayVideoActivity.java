package com.FitBuddy.fitbuddy.UserPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.model.Upload;
import com.FitBuddy.fitbuddy.model.VideoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayVideoActivity extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private VideoAdapter videoAdapter;
    private DatabaseReference databaseReference;
    private List<Upload> videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video);
        mrecyclerView=findViewById(R.id.display_video_recycler_view);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("videos/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot:dataSnapshot.getChildren()){
                    Upload mupload=postsnapshot.getValue(Upload.class);
                    videoList.add(mupload);
                }
                videoAdapter=new VideoAdapter(DisplayVideoActivity.this,videoList);
                mrecyclerView.setAdapter(videoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DisplayVideoActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
