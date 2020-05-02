package com.example.smartbabymonitor;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.IOException;

public class ParentsController extends AppCompatActivity {
    private static final int REQUEST_CODE_GET_CONTENT = 10;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 20;
    private static final int REQUEST_CODE_PERMISSIONS_CAMERA = 30;

    // Variable for the Databases/temperature & humidity
    FirebaseDatabase database;
    DatabaseReference temp;
    DatabaseReference hum;

    // string which contains the raspberry address / port
    String piAddr = "http://192.168.43.87:8081/";
    WebView mwebView;
    ImageButton bcamera;
    TextView tempera;
    TextView humid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_controller);

        ImageButton music = (ImageButton) findViewById(R.id.imageButton7);
        // Start a new class music
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Music.class);
                startActivity(intent);
            }

        });
        // Create an instance to the Databases which connects to the Raspberry pi
        database = FirebaseDatabase.getInstance();
        temp = database.getReference("temperature");
        hum = database.getReference("humidity");
        FirebaseApp.initializeApp(this);

        tempera = (TextView) findViewById(R.id.textView5);
        humid = (TextView) findViewById(R.id.textView4);

         //Get the temperature readings from the Database to the app
        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temperature = dataSnapshot.getValue().toString();
                tempera.setText(  temperature);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Get the humidity readings from thef Database to the app
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

        //Button bcamera which calls the string variable which contains the raspberry pi camera address/port
        bCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView mwebView  = findViewById(R.id.webView);
                mwebView.loadUrl(piAddr);
            }
        });
    }

}
