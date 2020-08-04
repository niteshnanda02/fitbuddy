package com.FitBuddy.fitbuddy.UserPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.DateAndTimeDialogue.DatePickFragment;
import com.FitBuddy.fitbuddy.UserPackage.DateAndTimeDialogue.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class SessionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private ImageView date_time_picker;
    private TextView date_time_text,date_text,time_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        initialize();
    }

    private void initialize() {
        date_time_picker=findViewById(R.id.dateandtimepicker);
        date_time_text=findViewById(R.id.datetimepickertext);
        date_text=findViewById(R.id.date_picker_text);
        time_text=findViewById(R.id.time_picker_text);
        date_time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePicker();

            }
        });
    }

    private void DatePicker(){
        DialogFragment datePicker=new DatePickFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");

    }
    private void TimePicker(){
        DialogFragment timePicker=new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"time picker");
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String selected_date= DateFormat.getDateInstance().format(c.getTime());
        Log.i("selected_date",selected_date);
        date_text.setText(selected_date);
        TimePicker();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time="Hour: "+hourOfDay+" Minute: "+minute;
        Log.i("selected_time",time);
        time_text.setText(time);
        date_time_text.setVisibility(View.VISIBLE);
        date_text.setVisibility(View.VISIBLE);
        time_text.setVisibility(View.VISIBLE);
    }
}
