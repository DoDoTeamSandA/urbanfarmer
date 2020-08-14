package com.dodo.urbanfarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Otplogin extends AppCompatActivity {

    private TextView otpview;
    private Button otpbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otplogin);

        otpview=(TextView)findViewById(R.id.mobilenumber);
        otpbtn=(Button)findViewById(R.id.otpbtn);

        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobilenNumber=otpview.getText().toString().trim();

                if(mobilenNumber.isEmpty() || mobilenNumber.length()<10){
                    otpbtn.setError("Please Enter Valid Number");
                }else{

                    registerUserWithOTP(mobilenNumber);
                }

            }

            private void registerUserWithOTP(String mobilenNumber) {

                Intent intent=new Intent(Otplogin.this,OTPVerification.class);
                intent.putExtra("incomingNunber",mobilenNumber);
                startActivity(intent);


            }
        });
    }
}
