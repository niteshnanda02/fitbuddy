package com.FitBuddy.fitbuddy.SplashPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;

import java.util.List;

public class SplashAdapter extends RecyclerView.Adapter<SplashAdapter.SplashViewHolder> {
    private Context context;
    private List<Integer> splashList;

    public SplashAdapter(Context context, List<Integer> splashList) {
        this.context = context;
        this.splashList = splashList;
    }

    @NonNull
    @Override
    public SplashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.splashcard, parent, false);
        return new SplashViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SplashViewHolder holder, int position) {
        holder.imageView1.setImageResource(splashList.get(position));
    }

    @Override
    public int getItemCount() {
        return splashList.size();
    }

    class SplashViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView1;
        public SplashViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1=itemView.findViewById(R.id.splash_card_imageView1);
        }
    }
}
