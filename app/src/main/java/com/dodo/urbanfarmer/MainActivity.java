package com.dodo.urbanfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dodo.urbanfarmer.NavigationFragments.HomeFragment;
import com.dodo.urbanfarmer.NavigationFragments.NetWorkFragment;
import com.dodo.urbanfarmer.NavigationFragments.ProfileFragment;
import com.dodo.urbanfarmer.NavigationFragments.ShoppingFragment;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("UrbanFarmer");
         setSupportActionBar(toolbar);

         //BottomNavigationView declartion

        bottomNavigationView=findViewById(R.id.MainBNB);


        LoadFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(this);


         //Floating
        findViewById(R.id.Nav_addbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Ok u clicked", Toast.LENGTH_SHORT).show();
            }
        });


        //No idea
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.side_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.signout:
                logout();
                return true;
        }
        return true;
    }
    private void logout() {
        mAuth.signOut();
        signOut();
        sendToLogin();
        LoginManager.getInstance().logOut();
    }
    private void sendToLogin() {
        if (mAuth.getCurrentUser() == null) {
            Intent intent=new Intent(MainActivity.this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
    }

    private boolean LoadFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Container,fragment).commit();
            return true;
        }
        return false;
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, ":Logged Out", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment=null;

        switch (item.getItemId()){
            case R.id.nav_hone:
                fragment=new HomeFragment();
                break;
            case R.id.nav_Network:
                fragment=new NetWorkFragment();
                break;
            case R.id.nav_Profile:
                fragment=new ProfileFragment();
                break;
            case R.id.nav_Shopping:
                fragment=new ShoppingFragment();
                break;
        }

        return LoadFragment(fragment);
    }
}
