package com.FitBuddy.fitbuddy.UserPackage.PlanPackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    Context context;
    List<PlanModel> planModelList;

    public PlanAdapter(Context context, List<PlanModel> planModelList) {
        this.context = context;
        this.planModelList = planModelList;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view=LayoutInflater.from(parent.getContext()).inflate(R.layout.users_plan_card, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        final PlanModel planModel=planModelList.get(position);
        holder.pack_name.setText(planModel.getPackName());
        holder.pack_price.setText(planModel.getPrice());
        holder.pack_workout.setText(planModel.getWorkout());
        holder.sessions.setText(planModel.getNo_of_sessions());
        holder.plan_image.setImageResource(planModel.getImage());
        holder.packButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,CheckoutActivity.class);
                intent.putExtra("PlanModel",planModel);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return planModelList.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView plan_image;
        private TextView pack_name,pack_price,pack_workout,sessions;
        private Button packButton;
        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            pack_name=itemView.findViewById(R.id.pack_name);
            pack_price=itemView.findViewById(R.id.pack_price);
            pack_workout=itemView.findViewById(R.id.pack_workout);
            sessions=itemView.findViewById(R.id.pack_sessions);
            packButton=itemView.findViewById(R.id.pack_button);
            plan_image=itemView.findViewById(R.id.plan_image);
        }
    }
}
