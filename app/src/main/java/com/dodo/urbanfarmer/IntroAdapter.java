package com.dodo.urbanfarmer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;


public class IntroAdapter extends PagerAdapter {
    Context mcontext;
    List<Introitem> introitems;

    public IntroAdapter(Context mcontext, List<Introitem> introitems) {
        this.mcontext = mcontext;
        this.introitems = introitems;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater =(LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.item_container_intro,null);
        ImageView introimage = layoutScreen.findViewById(R.id.introimage);


        introimage.setImageResource(introitems.get(position).getImage());
        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return introitems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}



