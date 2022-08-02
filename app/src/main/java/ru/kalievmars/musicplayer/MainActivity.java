package ru.kalievmars.musicplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button previousBtn, playBtn, nextBtn;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;

    boolean isPlay;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        isPlay = false;

        previousBtn.setOnClickListener(musicButtonsListener);
        playBtn.setOnClickListener(musicButtonsListener);
        nextBtn.setOnClickListener(musicButtonsListener);

        mediaPlayer = MediaPlayer.create(this, R.raw.everyday);

        settingMusicBar();
    }

    private void initViews() {
        previousBtn = findViewById(R.id.previousBtn);
        playBtn = findViewById(R.id.playBtn);
        nextBtn = findViewById(R.id.nextBtn);
        seekBar = findViewById(R.id.seekBar);
    }

    private void settingMusicBar() {
        seekBar.setMax(mediaPlayer.getDuration());

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0, 1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    View.OnClickListener musicButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.previousBtn:
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                    break;
                case R.id.playBtn:
                        if(isPlay) {
                            mediaPlayer.pause();
                            playBtn.setForeground(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24, null));
                            isPlay = false;
                        } else {
                            mediaPlayer.start();
                            playBtn.setForeground(getResources().getDrawable(R.drawable.ic_baseline_pause_24, null));
                            isPlay = true;
                        }
                    break;
                case R.id.nextBtn:
                    mediaPlayer.seekTo(mediaPlayer.getDuration());
                    mediaPlayer.start();
                    break;
                default:
                    break;
            }
        }
    };



}