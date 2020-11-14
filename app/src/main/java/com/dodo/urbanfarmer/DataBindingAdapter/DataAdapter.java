package com.dodo.urbanfarmer.DataBindingAdapter;

import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.dodo.urbanfarmer.R;

public class DataAdapter {
    @BindingAdapter("TextChange")
    public static void OTPText(Button btn, Boolean value){

        if(value){
            btn.setText("Verified");
            btn.setBackgroundColor(btn.getResources().getColor(R.color.green_light,null));
        }else {
            btn.setText("send otp");
            btn.setBackgroundColor(btn.getResources().getColor(R.color.black,null));
        }

    }
}
