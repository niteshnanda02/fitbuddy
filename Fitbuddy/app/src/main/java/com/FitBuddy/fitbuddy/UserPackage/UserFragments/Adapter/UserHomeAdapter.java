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
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.UserHomeCardClicked;

import java.util.List;

public class UserHomeAdapter extends RecyclerView.Adapter<UserHomeAdapter.MyViewHolder> {
    private Context context;
    private List<String> exerciseList;

    public UserHomeAdapter(Context context, List<String> sessionsDetailsList) {
        this.context = context;
        this.exerciseList = sessionsDetailsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.user_home_fragment_card, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String exercise= exerciseList.get(position);
        holder.imageView.setImageResource(MyImage.drawable(exercise));
        holder.exercise_textView.setText(exercise);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UserHomeCardClicked.class);
                intent.putExtra("exercise",exercise);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView exercise_textView,numberOfPeople_textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.user_home_card_image);
            exercise_textView =itemView.findViewById(R.id.user_home_card_exercise);

        }
    }
}
