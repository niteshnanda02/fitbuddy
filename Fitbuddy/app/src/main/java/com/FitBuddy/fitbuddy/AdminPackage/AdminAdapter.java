package com.FitBuddy.fitbuddy.AdminPackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.UserPackage.DisplayUserActivity;
import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.model.Users;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    Context context;
    List<Users> usersList;
    public AdminAdapter(Context context, List<Users> userlist) {
        this.context=context;
        this.usersList=userlist;
        }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.recyclerview_user, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        Users users=usersList.get(position);
        holder.textViewEmail.setText(users.name);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder{
        TextView textViewEmail;
        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmail=itemView.findViewById(R.id.textViewEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Users users=usersList.get(getAdapterPosition());
                    Intent intent=new Intent(context, DisplayUserActivity.class);
                    intent.putExtra("users",users);
                    context.startActivity(intent);
                }
            });
        }
    }
}
