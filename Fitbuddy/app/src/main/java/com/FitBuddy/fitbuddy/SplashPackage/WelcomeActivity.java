package com.FitBuddy.fitbuddy.SplashPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.loginsignup.LoginActivity;
import com.FitBuddy.fitbuddy.loginsignup.signupActivity;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class WelcomeActivity extends AppCompatActivity {
    public static final int PERMISSION_REQUEST_CODE=100;

    @Override
    protected void onStart() {
        super.onStart();
        if(!checkPermission()){
            RequestPermission();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void taketoLogin(View view) {
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void taketosignup(View view) {

        Intent intent=new Intent(this, signupActivity.class);
        startActivity(intent);
    }

    private boolean checkPermission(){
        int internet= ContextCompat.checkSelfPermission(getApplicationContext(),INTERNET);
        int readdata=ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
        int writedata=ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);

        return internet== PackageManager.PERMISSION_GRANTED
                && readdata==PackageManager.PERMISSION_GRANTED
                && writedata==PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{INTERNET,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length>0){
                    boolean internet=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean readData=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    boolean writeData=grantResults[2]==PackageManager.PERMISSION_GRANTED;

                    if (internet&&readData&&writeData){
                        Toast.makeText(this, "Permission given!!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Permission denied!!", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                            if (shouldShowRequestPermissionRationale(INTERNET)){
                                showMessageOKCancel("You need to give permission to go further",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                                                    requestPermissions(new String[]{INTERNET,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                            }
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(WelcomeActivity.this)
                .setMessage(message)
                .setPositiveButton("OK",okListener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }
}