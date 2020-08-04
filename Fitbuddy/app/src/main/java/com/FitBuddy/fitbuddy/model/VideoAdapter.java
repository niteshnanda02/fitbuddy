package com.FitBuddy.fitbuddy.model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private Context mcontext;
    private List<Upload> videolist;

    public VideoAdapter(Context mcontext, List<Upload> videolist) {
        this.mcontext = mcontext;
        this.videolist = videolist;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Upload uploadCurrent=videolist.get(position);
        holder.videoname.setText(uploadCurrent.getName());
        Uri uri=Uri.parse(uploadCurrent.getVideoulr());
        holder.display_videoView.setVideoURI(uri);
        MediaController mediaController=new MediaController(mcontext);
        mediaController.setAnchorView(holder.display_videoView);
        holder.display_videoView.setMediaController(mediaController);
        holder.display_videoView.start();
    }

    @Override
    public int getItemCount() {
        return videolist.size();
    }
}
