package com.dodo.urbanfarmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class Intropage extends AppCompatActivity {
    private final static int NUM_PAGES=3;
    private ViewPager viewpager;
    private MaterialButton buttonOnBoardingAction,Skipbtn;
    private List<ImageView>dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        buttonOnBoardingAction = findViewById(R.id.buttonOnBoardingAction);
        Skipbtn=findViewById(R.id.buttonskip);

        Skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intropage.this,SplashScreen2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });
        List<Introitem> mList = new ArrayList<>();
        mList.add(new Introitem(R.drawable.urbanfarmerbg3,"Learn and grow","By using UrbanFarmer Learn how to Grow Your own Food in your Own Home."));
        mList.add(new Introitem(R.drawable.urbanfarmerbg3,"Learn and grow","By using UrbanFarmer Learn how to Grow Your own Food in your Own Home."));
        mList.add(new Introitem(R.drawable.urbanfarmerbg3,"Learn and grow","By using UrbanFarmer Learn how to Grow Your own Food in your Own Home."));
        viewpager = (ViewPager) findViewById(R.id.introviewpager);
        IntroAdapter introadapter = new IntroAdapter(this, mList);
        viewpager.setAdapter(introadapter);
        setupintroindicators();
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

            }

            @Override
            public void onPageSelected(int position) {
                  selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

