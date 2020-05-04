package com.example.smartbabymonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Yamina Santillan
 * Registration Activity which contains the sign up/created an account, login and sign out of the application..
 * User validation implemented in the application.
 */

public class Registration extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText etEmail;
    EditText etPassword;
    Button bLogin;
    Button bSignUp;
    Button bSignOut;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etEmail = findViewById(R.id.editText);
        etPassword = findViewById(R.id.editText2);
        bLogin = findViewById(R.id.button);
        bSignOut = findViewById(R.id.button2);
        bSignUp = findViewById(R.id.button3);

        database = FirebaseDatabase.getInstance();
        //myRef = database.getReference("message");
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        Toast.makeText(Registration.this, "Firebase connection Success", Toast.LENGTH_LONG).show();

       // myRef.setValue("Hi IOT RPI updated");

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registration.this,  new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Signed In user: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to sign in user: ", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(), "Created user: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to create user: ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        bSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(Registration.this, "You signed out", Toast.LENGTH_LONG)
                        .show();
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            Toast.makeText(getApplicationContext(), currentUser.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
