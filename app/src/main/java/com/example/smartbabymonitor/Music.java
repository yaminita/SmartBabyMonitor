package com.example.smartbabymonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;

public class Music extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Button play = (Button) findViewById(R.id.button15);
        Button stop = (Button) findViewById(R.id.button17);
        Button pause = (Button) findViewById(R.id.button16);



    }

    // Object which allows to play music in the baby application
    MediaPlayer mediaPlayer = new MediaPlayer();

    public void play_song(View v){
        try{
            //Get the file music from the Firebase Storage
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/smart-baby-monitor-d19e1.appspot.com/o/rockabyebaby.mp3?alt=media&token=5c1977f5-8493-43b1-a0a6-2671357bcb5b");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.prepare();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    //Object which stops the music in the app.
    public void stop_song(View v){
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}
