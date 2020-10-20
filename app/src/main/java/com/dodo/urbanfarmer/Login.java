package com.dodo.urbanfarmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {

        private TextView RegisterText;
        private TextView phoneloginTxt;
        private  TextView Resetpassword;
        FirebaseAuth mAuth;
        private EditText UsernameET, PasswaordET;
        private Button login;

        private SignInButton GSignIn;

       private GoogleSignInClient mGoogleSignInClient;

       private ProgressBar progressBar;

       private Dialog  dialog;


       private static final int signCode = 0007;
       //Comments is add
    //Sunny is a good boy

      private CallbackManager callbackManager;

      //please add my code

    //iam here


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            dialog=new Dialog(this);
            dialog.setContentView(R.layout.dialogbox);
            dialog.setTitle("Loading");



            mAuth = FirebaseAuth.getInstance();
            Resetpassword =(TextView) findViewById(R.id.forgotpassword);
            RegisterText = (TextView) findViewById(R.id.RegisterText);

           LoginButton loginButton=findViewById(R.id.login_button);
           callbackManager=CallbackManager.Factory.create();
           loginButton.setReadPermissions("public_profile","email", "user_birthday", "user_friends");


/*
            phoneloginTxt = (TextView) findViewById(R.id.mobilenumber);
*/
            UsernameET = (EditText) findViewById(R.id.email);
            PasswaordET = (EditText) findViewById(R.id.password);
            login = (Button) findViewById(R.id.login);

          GSignIn = (SignInButton) findViewById(R.id.sign_in_button);

           // Log in with password and username

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    String username = UsernameET.getText().toString();
                    String password = PasswaordET.getText().toString();


                    if (username.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(username).find()) {
                        UsernameET.setError("Please Enter Valid Eamil Address");

                    } else if (password.isEmpty()) {

                        PasswaordET.setError("Please Enter PassWord");
                    } else {
                        dialog.show();


                        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Logedin Sucessfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    MainScreen();


                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(Login.this, "Username and Password Doesn't Matches", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }

            });


            //Facebook Login


          loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Toast.makeText(Login.this, "log in", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login.this, MainActivity.class);
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

          //Google login

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
                    Toast.makeText(Login.this, "log in", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });


            RegisterText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this, Register.class));
                }
            });

           /* phoneloginTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this, Otplogin.class));

                }
            });*/
            Resetpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this,Resetpassword.class));
                }
            });



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
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });
        }


       private void signIn() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, signCode);
        }



        @Override
        protected void onStart() {
            super.onStart();

            FirebaseUser user = mAuth.getCurrentUser();

            if (user != null) {
                MainScreen();
            }

            AccessToken accessToken=AccessToken.getCurrentAccessToken();
            if(accessToken!=null){

               MainScreen();
            }


        }

        public void MainScreen(){

            startActivity(new Intent(Login.this,MainActivity.class));
            finish();



        }
    }

