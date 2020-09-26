package com.dodo.urbanfarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class splashscreen extends AppCompatActivity {
    private  static  int SPLASH_SCREEN = 4000;
    ImageView logo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mAuth=FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(mAuth.getCurrentUser()!=null){
                    startActivity(new Intent(splashscreen.this,MainActivity.class));
                    finish();
                }else {
                    Intent intent = new Intent(splashscreen.this,SplashScreen2.class);
                    startActivity(intent);
                    finish();

                }





            }
        },SPLASH_SCREEN);
    }
}
