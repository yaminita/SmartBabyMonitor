package com.example.smartbabymonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Yamina Santillan x16110561
 * Help Activity which contains the instructions of how to manage the smart baby app..
 * Contains 4 activities help, help2, help3 and help4 connected through out buttons.
 */

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Buttons which contains the slides for the Menu Help
        Button back = (Button) findViewById(R.id.button7);
        Button next = (Button) findViewById(R.id.button8);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }

        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), help2.class);
                startActivity(intent);


            }

        });

    }
}
