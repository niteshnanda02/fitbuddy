package com.FitBuddy.fitbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.FitBuddy.fitbuddy.model.Users;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendNotificationActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editTextTitle, editTextBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        final Users users = (Users) getIntent().getSerializableExtra("users");
        textView = findViewById(R.id.TextView);
        editTextBody = findViewById(R.id.editTextBody);
        editTextTitle = findViewById(R.id.editTextTitle);
        textView.setText("Sending to : " + users.email);
        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(users);
            }
        });
    }

    private void sendNotification(Users users) {
        String title = editTextTitle.getText().toString().trim();
        String body = editTextBody.getText().toString().trim();

        if (title.isEmpty()) {
            editTextTitle.setError("Title required");
            editTextTitle.requestFocus();
            return;
        }

        if (body.isEmpty()) {
            editTextBody.setError("Body required");
            editTextBody.requestFocus();
            return;
        }

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://localhost:8085/api/").addConverterFactory(GsonConverterFactory.create()).build();

        Api api=retrofit.create(Api.class);

        Call<ResponseBody> call=api.sendNotification(users.token,title,body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(SendNotificationActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SendNotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
