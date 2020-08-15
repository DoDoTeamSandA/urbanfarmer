package com.dodo.urbanfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Resetpassword extends AppCompatActivity {

    private EditText emailTxt;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        mAuth=FirebaseAuth.getInstance();

        emailTxt=(EditText)findViewById(R.id.email);

        findViewById(R.id.reverifybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailTxt.getText().toString();

                if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).find()){

                    emailTxt.setError("Enter a Valid Email");

                }else{

                   mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                           if(task.isSuccessful()){
                               Intent intent=new Intent(Resetpassword.this,Login.class);
                               intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(intent);
                           }

                       }
                   });



                }
            }
        });



    }
}
