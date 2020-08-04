package com.FitBuddy.fitbuddy.TrainerPackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.TrainerPackage.Model.TrainerSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrainerAdapter extends RecyclerView.Adapter<TrainerAdapter.MyViewHolder> {
    private  String TAG = this.getClass().getSimpleName();
    public Context context;
    public List<TrainerSession> trainerSessionList;

    public TrainerAdapter(Context context, List<TrainerSession> trainerSessionList) {
        this.context = context;
        this.trainerSessionList = trainerSessionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_activity_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TrainerSession trainerSession=trainerSessionList.get(position);
        holder.exercise_name.setText(trainerSession.getExcercise());
        holder.time.setText(trainerSession.getTime());
        holder.date.setText(trainerSession.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dosomething(trainerSession.getDate(),trainerSession.getTime(),trainerSession.getLink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainerSessionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView exercise_name,date,time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exercise_name=itemView.findViewById(R.id.trainer_activity_exercise_name);
            date=itemView.findViewById(R.id.trainer_activity_exercise_date);
            time=itemView.findViewById(R.id.trainer_activity_exercise_time);
        }
    }
    private void dosomething(String SessionDate, String SessionTime, String link) {
        String curr_date, curr_time;
        Date date = Calendar.getInstance().getTime();
        curr_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
        curr_time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date);
        Log.d(TAG, curr_date + "  " + curr_time);
        Log.d(TAG, curr_date + "  " + SessionDate + "  " + curr_time + "  " + SessionTime);
        if (curr_date.equals(SessionDate)) {
            checkTiming(curr_time, SessionTime, link);
        } else {
            Toast.makeText(context, "Session is not started till now", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkTiming(String CurrTime, String SessionTime, String link) {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {

            Date time = simpleDateFormat.parse(CurrTime);
            Date start_time = simpleDateFormat.parse(SessionTime);
            Date before_time = new Date(start_time.getTime() - 600 * 1000);
            Date after_time = new Date(start_time.getTime() + 1800 * 1000);
            if (before_time.before(time) && after_time.after(time)) {
                Uri uri;
                uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);

            } else if (before_time.after(time)) {
                Toast.makeText(context, "Session is not started till now", Toast.LENGTH_SHORT).show();
            } else if (after_time.before(time)) {
                Toast.makeText(context, "You can't join session now", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
