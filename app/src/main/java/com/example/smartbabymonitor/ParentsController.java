package com.example.smartbabymonitor;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.jjoe64.graphview.GraphView;

import java.io.IOException;

public class ParentsController extends AppCompatActivity {
    private static final int REQUEST_CODE_GET_CONTENT = 10;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 20;
    private static final int REQUEST_CODE_PERMISSIONS_CAMERA = 30;
    FirebaseDatabase database;
    DatabaseReference temp;
    DatabaseReference hum;
    String piAddr = "http://192.168.43.87:8081/";
    WebView mwebView;
    ImageButton bcamera;
    TextView tempera;
    TextView humid;
    Button mRecordBtn;

    private MediaRecorder recorder =null;
    String fileName = null;
    private static final String LOG_TAG = "Record_Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_controller);

        mRecordBtn = (Button) findViewById(R.id.button4);
        //fileName = Environment.getExternalStorageDirectory().getPath();
        //fileName += "/recorded_audio.3gp";



        database = FirebaseDatabase.getInstance();
        temp = database.getReference("temperature");
        hum = database.getReference("humidity");
        FirebaseApp.initializeApp(this);

        tempera = (TextView) findViewById(R.id.textView5);
        humid = (TextView) findViewById(R.id.textView4);
        //tempView = (GraphView) findViewById(R.id.graph);


        ValueEventListener changeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //GraphView tempView = (GraphView) findViewById(R.id.graph);

                if(dataSnapshot.hasChildren()){
                    String temperature = dataSnapshot.getValue().toString();
                    tempera.setText(temperature);

                }

                //showChart(dataVals);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        temp.addValueEventListener(changeListener);

        hum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String humidity = dataSnapshot.getValue().toString();
                humid.setText(humidity);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ImageButton bCamera = findViewById(R.id.imageButton3);

        bCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //takePictureFromCamera();
                WebView mwebView  = findViewById(R.id.webView);
                mwebView.loadUrl(piAddr);
            }
        });
//        mRecordBtn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction()==MotionEvent.ACTION_DOWN){
//                    startRecording();
//
//                }else if(event.getAction()==MotionEvent.ACTION_UP){
//                    stopRecording();
//                }
//
//                return false;
//            }
//        });
//        mRecordBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//                fileName += "/recorded_audio.3gp";
//
//                try {
//                    recorder.prepare();
//                    recorder.start();
//                }catch(IOException e){
//                    e.printStackTrace();
//                }
//                Toast.makeText(ParentsController.this, "Recording", Toast.LENGTH_SHORT ).show();
//            }
//        });
    }
    private void startRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording(){
        recorder.stop();
        recorder.release();
        recorder = null;
    }

}
