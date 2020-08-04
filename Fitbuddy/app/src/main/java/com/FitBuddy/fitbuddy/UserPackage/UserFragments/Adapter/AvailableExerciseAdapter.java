package com.FitBuddy.fitbuddy.UserPackage.UserFragments.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Designing.MyImage;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.SelectedSessionByUsers;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.SessionsDetails;

import java.util.List;

public class AvailableExerciseAdapter extends RecyclerView.Adapter<AvailableExerciseAdapter.AvailableViewHolder> {

    private Context context;
    private List<SessionsDetails> availableExerciseDetailsList;
    public AvailableExerciseAdapter(Context context, List<SessionsDetails> availableExerciseDetailsList) {
        this.context = context;
        this.availableExerciseDetailsList = availableExerciseDetailsList;
    }

    @NonNull
    @Override
    public AvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.available_exercise_card, parent, false);
        return new AvailableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AvailableViewHolder holder, int position) {
        if (availableExerciseDetailsList.size()!=0) {
            final SessionsDetails details = availableExerciseDetailsList.get(position);
            holder.name.setText(details.getTrainer());
            String date = details.getDate().substring(5);
            date.replace('-', '/');
            holder.imageView.setImageResource(MyImage.updrawable(details.getExcercise()));
            holder.date.setText(date);
            holder.time.setText(details.getTime());
            holder.category.setText(details.getExcercise());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SelectedSessionByUsers.class);
                        intent.putExtra("sessiondetails", details);
                        context.startActivity(intent);
                    }
                });
            }
        }


    @Override
    public int getItemCount() {
        return availableExerciseDetailsList.size();
    }

    class AvailableViewHolder extends RecyclerView.ViewHolder{
        private TextView level,name,category,date,time;
        private ImageView imageView;
        public AvailableViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.avaialble_exercise_image);
            name=itemView.findViewById(R.id.available_exercise_name);
            category=itemView.findViewById(R.id.available_exercise_category);
            date=itemView.findViewById(R.id.available_exercise_date);
            time=itemView.findViewById(R.id.available_exercise_time);
        }
    }
}
