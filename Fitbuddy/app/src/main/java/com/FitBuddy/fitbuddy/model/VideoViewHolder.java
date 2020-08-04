package com.FitBuddy.fitbuddy.model;

import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;

class VideoViewHolder extends RecyclerView.ViewHolder{

    public TextView videoname;
    public VideoView display_videoView;
    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoname=itemView.findViewById(R.id.video_textView_name);
        display_videoView=itemView.findViewById(R.id.display_videoView);
    }
}
