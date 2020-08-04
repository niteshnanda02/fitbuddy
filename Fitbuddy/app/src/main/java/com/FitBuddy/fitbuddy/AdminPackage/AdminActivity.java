package com.FitBuddy.fitbuddy.AdminPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.FitBuddy.fitbuddy.UserPackage.DisplayVideoActivity;
import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.model.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CAPTURE_VIDEO = 1;
    Button record, upload, download;
    private File videoFile;
    private String videoFilePath;
    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;
    private StorageTask muploadTask;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mStorageRef = FirebaseStorage.getInstance().getReference("videos/");
        databaseReference = FirebaseDatabase.getInstance().getReference("videos/");
        initialize_btn();
    }

    private void initialize_btn() {
        record = findViewById(R.id.button2);
        upload = findViewById(R.id.button3);
//        download = findViewById(R.id.button4);
        record.setOnClickListener(this);
        upload.setOnClickListener(this);
//        download.setOnClickListener(this);
        videoView=findViewById(R.id.admin_videoView);
        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                if (muploadTask != null && muploadTask.isInProgress()) {
                    Toast.makeText(this, "uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    videoView.setVisibility(View.INVISIBLE);
                    openVideoIntent();
                }
                break;
            case R.id.button3:
                uploadVideo();
                break;
//            case R.id.button4:
//                downloadVideo();
//                break;
        }

    }

    private void downloadVideo() {
        Intent intent = new Intent(this, DisplayVideoActivity.class);
        startActivity(intent);
    }

    private void uploadVideo() {
        if (videoFile != null) {
            Uri file = Uri.fromFile(videoFile);
            final StorageReference mstorageRef = mStorageRef.child("videos" + videoFile.getName());
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading.....");
            progressDialog.show();
            muploadTask = mstorageRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            mstorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.i("uri",uri.toString());
                                    Upload upload = new Upload(videoFile.getName(), uri.toString());
                                    String uploadId = databaseReference.push().getKey();
                                    databaseReference.child(uploadId).setValue(upload);
                                    progressDialog.dismiss();
                                    Toast.makeText(AdminActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    Log.i("Uploaded", "uploaded");
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(AdminActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Uploaded...");
                        }
                    });
        } else {
            Toast.makeText(this, "No video is created", Toast.LENGTH_SHORT).show();
        }

    }

    private void openVideoIntent() {
        Intent videoIntent = new Intent(
                MediaStore.ACTION_VIDEO_CAPTURE);
        if (videoIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the video
            File videoFile = null;
            try {
                videoFile = createVideoFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (videoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.video.provider", videoFile);
                videoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(videoIntent,
                        REQUEST_CAPTURE_VIDEO);
            }
        }
    }

    private File createVideoFile() throws IOException {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String videoFileName = mAuth.getCurrentUser().getPhoneNumber() + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        videoFile = File.createTempFile(
                videoFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        videoFilePath = "file://" + videoFile.getAbsolutePath();
        Log.i("videoFilePath", videoFilePath);
        return videoFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAPTURE_VIDEO:
                    //don't compare the data to null, it will always come as  null because we are providing a file URI, so load with the imageFilePath we obtained before opening the cameraIntent
                    Log.i("video", videoFilePath);
                    videoView=findViewById(R.id.admin_videoView);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoPath(videoFilePath);
                    MediaController mediaController=new MediaController(this);
                    mediaController.setAnchorView(videoView);
                    mediaController.setMediaPlayer(videoView) ;

                    videoView.start();
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // User Cancelled the action
        }
    }

}
