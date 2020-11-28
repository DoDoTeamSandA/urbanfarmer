package com.dodo.urbanfarmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Intropage extends AppCompatActivity {
    private final static int NUM_PAGES=6;
    private int selectedPageIndex = 0;
    private Timer timer;
    private final long DELAY_MS = 800;
    private final long PERIOD_MS = 1000;
    private boolean exitWhenScrollNextPage =false;
    private ViewPager viewpager;
    private MaterialButton register,login,guestbtn;
    private List<ImageView>dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        login = findViewById(R.id.login);
        register=findViewById(R.id.Register);
        guestbtn=findViewById(R.id.guestlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intropage.this,Login.class);
                startActivity(intent);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intropage.this,Register.class);
                startActivity(intent);

            }
        });
        guestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intropage.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        /* @Override
            public void onClick(View v) {
                if (selectedPageIndex == 5){
                    Intent intent=new Intent(Intropage.this,SplashScreen2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else
                viewpager.setCurrentItem(getItem(+1),true);
            }
        });*/
        List<Introitem> mList = new ArrayList<>();
        mList.add(new Introitem(R.drawable.introimage6));
        mList.add(new Introitem(R.drawable.introimage1));
        mList.add(new Introitem(R.drawable.introimage2));
        mList.add(new Introitem(R.drawable.introimage3));
        mList.add(new Introitem(R.drawable.introimage4));
        mList.add(new Introitem(R.drawable.introimage5));

        viewpager = (ViewPager) findViewById(R.id.introviewpager);
        IntroAdapter introadapter = new IntroAdapter(this, mList);
        viewpager.setAdapter(introadapter);
        setupintroindicators();
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (selectedPageIndex == NUM_PAGES){
                    selectedPageIndex = 0;
                }viewpager.setCurrentItem(selectedPageIndex++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_MS,PERIOD_MS);
    }

    private int getItem(int i) {
        return viewpager.getCurrentItem()+i;

    }

    private void setupintroindicators() {
        dots = new ArrayList<>();
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.introindicators);
        for (int i = 0; i < NUM_PAGES; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(getResources().getDrawable(R.drawable.intro_indicator_inactive));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8,0,8,0);
            dotsLayout.addView(dot, params);
            dots.add(dot);
        }

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(exitWhenScrollNextPage && position == NUM_PAGES-1){
                    exitWhenScrollNextPage = false;

                }

            }

            @Override
            public void onPageSelected(int position) {
                selectedPageIndex = position;
                  selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*if (state == ViewPager.SCROLL_STATE_DRAGGING){
                    if(selectedPageIndex == NUM_PAGES)
                    launchNextScreen();
                }*/

            }
        });

    }

    private void launchNextScreen() {
        Intent intent=new Intent(Intropage.this,SplashScreen2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }


    private void selectDot(int index) {
        Resources res = getResources();
        for (int i = 0; i< NUM_PAGES;i++){
            int drawableid = (i == index)?(R.drawable.intro_indicator_active):(R.drawable.intro_indicator_inactive);
            Drawable drawable = res.getDrawable(drawableid);
            dots.get(i).setImageDrawable(drawable);
        }

    }
    /*private void setCurrentIntroIndicator(int index){
        int childcount = linearLayout.getChildCount();
        for (int i=0; i<childcount;i++){
            ImageView imageView = (ImageView)linearLayout.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.intro_indicator_active));
            }else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.intro_indicator_inactive)
                );

            }
        }
        if (index == introadapter.getCount() - 1){
            buttonOnBoardingAction.setText("Start");
        }else{
            buttonOnBoardingAction.setText("Next");
        }
    }*/
}

