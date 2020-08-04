package com.FitBuddy.fitbuddy.Notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.FitBuddy.fitbuddy.R;

public class DisplayNotificationActivity extends AppCompatActivity {
    TextView title_textview,body_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);
        title_textview=findViewById(R.id.display_title);
        body_textview=findViewById(R.id.display_body);
        String title=getIntent().getStringExtra("title");
        String body=getIntent().getStringExtra("body");

        if(title==null&&body==null)
            title_textview.setText("No notification from admin");
        else {
            title_textview.setText(title);
            body_textview.setText(body);
            body_textview.setMovementMethod(LinkMovementMethod.getInstance())   ;
        }
    }
}
