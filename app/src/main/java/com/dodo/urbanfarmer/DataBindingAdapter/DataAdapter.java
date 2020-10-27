package com.dodo.urbanfarmer.DataBindingAdapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.dodo.urbanfarmer.R;

public class DataAdapter {
    @BindingAdapter("TextChange")
    public static void OTPText(TextView textView,Boolean value){

        if(value){
            textView.setText("Verified");
            textView.setBackgroundColor(textView.getResources().getColor(R.color.green_light));
        }else {
            textView.setText("Not Verified");
            textView.setBackgroundColor(textView.getResources().getColor(R.color.black));
        }

    }
}
