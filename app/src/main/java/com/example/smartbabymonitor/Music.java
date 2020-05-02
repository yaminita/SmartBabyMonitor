package com.example.smartbabymonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class Music extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        playButton = findViewById(R.id.button15);
        seekBar = findViewById(R.id.seekBar);

        //Instanciate the media Player and create the media to play the music
        mediaPlayer = new MediaPlayer();
        //mediaPlayer = MediaPlayer.create(Music.this, R.raw.lullabygoodnight);
        try {
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/smart-baby-monitor-d19e1.appspot.com/o/rockabyebaby.mp3?alt=media&token=5c1977f5-8493-43b1-a0a6-2671357bcb5b");
        }catch (IOException e){
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int duration = mp.getDuration();
                Toast.makeText(Music.this, String.valueOf((duration/1000)/60),Toast.LENGTH_LONG).show();
            }
        });
        MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {

                seekBar.setMax(mediaPlayer.getDuration());

                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mp.isPlaying()){
                            //stops and give users the option to start again

                            mp.pause();
                            playButton.setText(R.string.play_text);
                        }else{

                            mp.start();
                            playButton.setText(R.string.pause_text);
                        }
                    }
                });
            }
        };
        mediaPlayer.setOnPreparedListener(preparedListener);
        mediaPlayer.prepareAsync();

        //method that allows to see which the maximum duration of the song

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    //method will help to release any music from memory
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }

}
