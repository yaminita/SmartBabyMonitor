package com.example.smartbabymonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class help3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help3);

        // Buttons which contains the slides for the Menu Help
        Button back = (Button) findViewById(R.id.button11);
        Button next = (Button) findViewById(R.id.button12);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), help2.class);
                startActivity(intent);


            }

        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), help4.class);
                startActivity(intent);


            }

        });
    }
}
