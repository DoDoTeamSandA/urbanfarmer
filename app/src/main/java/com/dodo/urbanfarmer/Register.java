package com.dodo.urbanfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private EditText email,password;
    private Button registerbtn;

    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Auth instance

        mAuth=FirebaseAuth.getInstance();

        // Binding UI with java instances
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        registerbtn=(Button)findViewById(R.id.registerbtn);



        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fecthing text from editText

                String emailText=email.getText().toString();
                String passwordText=password.getText().toString();

                //checking for null pointer exception
                if(TextUtils.isEmpty(emailText) || !Patterns.EMAIL_ADDRESS.matcher(emailText).find()){
                    email.setError("Please fill");
                }else if(TextUtils.isEmpty(passwordText) || passwordText.length()<6){
                    password.setError("Please fill and length should be more than 6");
                }else{

                    newUserRegistration(emailText,passwordText);


                }




                //Method to create new user





            }

            private void newUserRegistration(String emailText, String passwordText) {
                mAuth.createUserWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Register.this,Login.class));
                        }else {

                            Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(Register.this, "Please try again", Toast.LENGTH_SHORT).show();




                        }

                    }
                });

            }
        });

    }
}
