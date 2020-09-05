package com.dodo.urbanfarmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText email,password;
    private Button registerbtn;
    FirebaseAuth mAuth;


    private SignInButton GSignIn;

    private GoogleSignInClient mGoogleSignInClient;


    private static final int signCode = 0007;

    private CallbackManager callbackManager;
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
        GSignIn = (SignInButton) findViewById(R.id.sign_in_button);



        // Facebook login

        LoginButton loginButton=findViewById(R.id.login_button);
        callbackManager=CallbackManager.Factory.create();
        loginButton.setReadPermissions("public_profile","email", "user_birthday", "user_friends");




        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(Register.this, "log in", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Register.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


  //Google Login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.Web_ClientId)).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(Register.this, "log in", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });





//username password login
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fecthing text from editText

                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                //checking for null pointer exception
                if (TextUtils.isEmpty(emailText) || !Patterns.EMAIL_ADDRESS.matcher(emailText).find()) {
                    email.setError("Please fill");
                } else if (TextUtils.isEmpty(passwordText) || passwordText.length() < 6) {
                    password.setError("Please fill and length should be more than 6");
                } else {

                    newUserRegistration(emailText, passwordText);
                }
            }


        });

    }
    //Method to create new user


    private void newUserRegistration(String emailText, String passwordText) {
        mAuth.createUserWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Register.this,Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                }else {

                    Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register.this, "Please try again", Toast.LENGTH_SHORT).show();




                }

            }
        });

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, signCode);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);


        if (requestCode == signCode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void firebaseAuthWithGoogle(final String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}
