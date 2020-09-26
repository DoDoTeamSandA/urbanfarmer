package com.dodo.urbanfarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class IntroPage extends AppCompatActivity {
    PreferenceManager preferenceManager=new PreferenceManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}