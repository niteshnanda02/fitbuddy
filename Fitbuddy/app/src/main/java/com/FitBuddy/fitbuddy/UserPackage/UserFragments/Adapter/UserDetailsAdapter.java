package com.FitBuddy.fitbuddy.UserPackage.UserFragments.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Designing.MyImage;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.UserJoinSession;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.UserSessionModel;
import com.FitBuddy.fitbuddy.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.UserDetailsViewHolder> {
    boolean flag = false;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestoredb;
    private String TAG = this.getClass().getSimpleName();
    private Context context;
    private List<UserSessionModel> availableExerciseDetailsList;
    List<UserSessionModel> sessionCompletedList;
    boolean completedSession;

    public UserDetailsAdapter(Context context, List<UserSessionModel> availableExerciseDetailsList, List<UserSessionModel> sessionCompletedList, boolean completedSession) {
        this.context = context;
        this.availableExerciseDetailsList = availableExerciseDetailsList;
        this.sessionCompletedList = sessionCompletedList;
        this.completedSession = completedSession;
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestoredb = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public UserDetailsAdapter.UserDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_exercise_card, parent, false);
        return new UserDetailsAdapter.UserDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserDetailsAdapter.UserDetailsViewHolder holder, int position) {
        if (availableExerciseDetailsList.size() != 0 && !completedSession) {
            final UserSessionModel details = availableExerciseDetailsList.get(position);
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
                    dosomething(details.getDate(), details.getTime(), details.getLink());
                    if (flag) {
                        firebaseFirestoredb.collection("users")
                                .document(mAuth.getCurrentUser().getUid())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Users users = task.getResult().toObject(Users.class);
                                        UserSessionModel userSessionModel = new UserSessionModel(details.getDate(), details.getExcercise(), details.getLimit(), details.getLink(), details.getTime(), details.getTrainer(), details.getId(), details.getTimeS());
                                        Log.d(TAG, users + "");
                                        final UserJoinSession userJoinSession = new UserJoinSession(users.name, users.mobile);
                                        firebaseFirestoredb.collection("users")
                                                .document(mAuth.getCurrentUser().getUid())
                                                .collection("session_completed_collection")
                                                .document(details.getId())
                                                .set(userSessionModel)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "session is done!!");
                                                        }

                                                    }
                                                });
                                    }
                                });
                    }
                }
            });

        } else if (sessionCompletedList.size() != 0 && completedSession) {
            final UserSessionModel details = sessionCompletedList.get(position);
            holder.name.setText(details.getTrainer());
            String date = details.getDate().substring(5);
            date.replace('-', '/');
            holder.imageView.setImageResource(MyImage.updrawable(details.getExcercise()));
            holder.date.setText(date);
            holder.time.setText(details.getTime());
            holder.category.setText(details.getExcercise());
        }
    }

    @Override
    public int getItemCount() {
        int len;
        if (completedSession){
            len=sessionCompletedList.size();
        }else
            len=availableExerciseDetailsList.size();
        return len;
    }

    class UserDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView level, name, category, date, time;
        private ImageView imageView;

        public UserDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.avaialble_exercise_image);
            name = itemView.findViewById(R.id.available_exercise_name);
            category = itemView.findViewById(R.id.available_exercise_category);
            date = itemView.findViewById(R.id.available_exercise_date);
            time = itemView.findViewById(R.id.available_exercise_time);
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
            Date after_time = new Date(start_time.getTime() + 3600 * 1000);
            if (before_time.before(time) && after_time.after(time)) {
                flag = true;
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
