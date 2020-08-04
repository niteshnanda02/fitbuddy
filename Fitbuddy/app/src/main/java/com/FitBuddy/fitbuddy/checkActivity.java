package com.FitBuddy.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class checkActivity extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        videoView=findViewById(R.id.videoview);
        String url="https://firebasestorage.googleapis.com/v0/b/learnfirebase-3a35a.appspot.com/o/videos%2FvideosMP420200521_154905_1847290955.mp4?alt=media&token=096ff473-cac2-414d-bb41-22ce5a2a371b";
        Uri ui = Uri.parse(url);
        videoView.setVideoURI(ui);
        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}
