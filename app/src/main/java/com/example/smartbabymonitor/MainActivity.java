package com.example.smartbabymonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Yamina Santillan x16110561
 * Main Activity which contains all the parents controller and the help menu in the app.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This activity contains the main menu of the app: Parents Controller and Help menu
        Button parentsController = (Button) findViewById(R.id.button5);
        Button help = (Button) findViewById(R.id.button6);

        //Intent which starts the Parents controller activity
        parentsController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParentsController.class);
                startActivity(intent);
            }

        });

        //Intent which starts the Help Menu Activity
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Help.class);
                startActivity(intent);


            }

        });

    }
}
