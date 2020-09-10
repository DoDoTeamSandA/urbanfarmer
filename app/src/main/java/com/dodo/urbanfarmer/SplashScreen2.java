package com.dodo.urbanfarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class SplashScreen2 extends AppCompatActivity {
    private VideoView videoBG;
    MediaPlayer mediaPlayer;
    int mCurrentVideoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        //Hookup for VideoView
        videoBG = (VideoView) findViewById(R.id.VideoView);
        //build your video uri
        Uri uri = Uri.parse("android.resource://"
        +getPackageName()
        +"/"
        +R.raw.video);
        videoBG.setVideoURI(uri);
        videoBG.start();
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mediaPlayer.setLooping(true);
                if(mCurrentVideoPosition != 0){
                    mediaPlayer.seekTo(mCurrentVideoPosition);
                    mediaPlayer.start();
                }

            }
        });
    }
    /*================================== important Section! =============================
     we must override onPause(),onResume(), and onDestroy() to properly handle our
     VideoView
     */

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentVideoPosition = mediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}