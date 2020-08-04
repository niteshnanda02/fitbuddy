package com.FitBuddy.fitbuddy.UserPackage.UserFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.Designing.MyImage;
import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.SessionsDetails;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelectedSessionByUsers extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    TextView trainer_name, exercise, date, time, save_session_text;
    Button save_session_btn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestoredb;
    SessionsDetails sessionsDetails;
    boolean flag=false;
    private String UniqueKey = "";
    private ImageView selected_image;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_exercise);

        sessionsDetails = (SessionsDetails) getIntent().getSerializableExtra("sessiondetails");
        UniqueKey = sessionsDetails.getDate() + sessionsDetails.getTime() + sessionsDetails.getTrainer();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestoredb = FirebaseFirestore.getInstance();
        initialize();
        new SendToUser(sessionsDetails).execute();
        progressBar.setVisibility(View.VISIBLE);


    }

    private void initialize() {
        save_session_btn = findViewById(R.id.save_session_btn);
        progressBar = findViewById(R.id.save_session_progress_bar);
        save_session_text = findViewById(R.id.save_session_text);
        selected_image = findViewById(R.id.selected_exercise_image);
        trainer_name = findViewById(R.id.selected_trainer_name);
        exercise = findViewById(R.id.selected_exercise_category);
        date = findViewById(R.id.selected_exercise_date);
        time = findViewById(R.id.selected_exercise_time);
        selected_image.setImageResource(MyImage.updrawable(sessionsDetails.getExcercise()));
        trainer_name.setText(sessionsDetails.getTrainer());
        exercise.setText(sessionsDetails.getExcercise());
        String Date = sessionsDetails.getDate().substring(5);
        Date.replace('-', '/');

        date.setText(Date);
        time.setText(sessionsDetails.getTime());
    }

    class SendToUser extends AsyncTask<Void, Void, Void> {
        private SessionsDetails sessionsDetails;

        public SendToUser(SessionsDetails sessionsDetails) {
            this.sessionsDetails = sessionsDetails;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            firebaseFirestoredb.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                Users users = task.getResult().toObject(Users.class);
                                final UserSessionModel userSessionModel = new UserSessionModel(sessionsDetails.getDate(), sessionsDetails.getExcercise(), sessionsDetails.getLimit(), sessionsDetails.getLink(), sessionsDetails.getTime(), sessionsDetails.getTrainer(), sessionsDetails.getId(), sessionsDetails.getTimeS());
                                Log.d(TAG, users + "");
                                final UserJoinSession userJoinSession = new UserJoinSession(users.name, users.mobile);
                                if (ifavailable(sessionsDetails.getDate(),sessionsDetails.getTime())) {
                                    firebaseFirestoredb.collection(sessionsDetails.getExcercise())
                                            .document(UniqueKey)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        SessionsDetails sessionsDetails = task.getResult().toObject(SessionsDetails.class);
                                                        if (sessionsDetails.userJoinSessionList == null) {
                                                            sessionsDetails.userJoinSessionList = new ArrayList<>();
                                                        }
                                                        if (!sessionsDetails.userJoinSessionList.contains(userJoinSession)) {
                                                            sessionsDetails.userJoinSessionList.add(userJoinSession);

                                                            firebaseFirestoredb.collection(sessionsDetails.getExcercise())
                                                                    .document(UniqueKey)
                                                                    .set(sessionsDetails)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Log.d(TAG, "Added the user to session db");
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                }
                                            });
                                }
                                if (ifavailable(sessionsDetails.getDate(), sessionsDetails.getTime())) {
                                    firebaseFirestoredb.collection("users")
                                            .document(mAuth.getCurrentUser().getUid())
                                            .collection("session_details_collection")
                                            .document(sessionsDetails.getId())
                                            .set(userSessionModel)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    save_session_text.setVisibility(View.VISIBLE);
                                                    Log.d(TAG, "session added in user profile");
                                                    progressBar.setVisibility(View.INVISIBLE);

                                                    save_session_btn.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            dosomething(sessionsDetails.getDate(), sessionsDetails.getTime());
                                                            if (flag){
                                                                firebaseFirestoredb.collection("users")
                                                                        .document(mAuth.getCurrentUser().getUid())
                                                                        .collection("session_completed_collection")
                                                                        .document(sessionsDetails.getId())
                                                                        .set(userSessionModel)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()){
                                                                                    Log.d(TAG,"session is done!!");
                                                                                }

                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    });

                                                }
                                            });
                                }else {
                                    save_session_text.setText("Session is expired you can't joined!!");
                                    save_session_text.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }


                        }
                    });
            return null;
        }
    }

    private void dosomething(String SessionDate, String SessionTime) {
        String curr_date, curr_time;
        Date date = Calendar.getInstance().getTime();
        curr_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
        curr_time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date);
        Log.d(TAG, curr_date + "  " + curr_time);
        Log.d(TAG, curr_date + "  " + SessionDate + "  " + curr_time + "  " + SessionTime);
        if (curr_date.equals(SessionDate)) {
            checkTiming(curr_time, SessionTime);
        } else {
            Toast.makeText(this, "Session is not started till now", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkTiming(String CurrTime, String SessionTime) {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {

            Date time = simpleDateFormat.parse(CurrTime);
            Date start_time = simpleDateFormat.parse(SessionTime);
            Date before_time = new Date(start_time.getTime() - 600 * 1000);
            Date after_time = new Date(start_time.getTime() + 3600 * 1000);
            if (before_time.before(time) && after_time.after(time)) {
                flag=true;
                String link = sessionsDetails.getLink();
                Uri uri;
                uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            } else if (before_time.after(time)) {
                Toast.makeText(this, "Session is not started till now", Toast.LENGTH_SHORT).show();
            } else if (after_time.before(time)) {
                Toast.makeText(this, "You can't join session now", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean ifavailable(String SessionDate, String sessionTime) {
        String curr_date, curr_time;
        String pattern_time = "HH:mm";
        String pattern_date = "yyyy-MM-dd";
        Date date = Calendar.getInstance().getTime();
        curr_date = new SimpleDateFormat(pattern_date, Locale.getDefault()).format(date);
        curr_time = new SimpleDateFormat(pattern_time, Locale.getDefault()).format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern_time);
        try {
            Date sessiondate = dateFormat.parse(SessionDate);
            Date currdate = dateFormat.parse(curr_date);
            if (sessiondate.after(currdate))
                return true;
            Date time = simpleDateFormat.parse(curr_time);
            Date start_time = simpleDateFormat.parse(sessionTime);
            Date before_time = new Date(start_time.getTime() - 600 * 1000);
            Date after_time = new Date(start_time.getTime() + 3600 * 1000);
            if (before_time.before(time) && after_time.after(time)) {
                return true;

            } else if (before_time.after(time)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
