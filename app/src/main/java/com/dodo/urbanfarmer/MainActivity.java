package com.dodo.urbanfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dodo.urbanfarmer.NavigationFragments.AddFragment;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout mdrawerlayout;
    private Toolbar toolbar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        mAuth=FirebaseAuth.getInstance();
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze_24);
        toolbar.setTitle("UrbanFarmer");
        navigationDrawer();

        //BottomNavigationView declartion
        bottomNavigationView=findViewById(R.id.MainBNB);
        LoadFragment(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //Floating
        //No idea
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.Web_ClientId))
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.logout:
                        logout();
                        break;
                }
                return true;
            }
        });
        navigationView.setCheckedItem(R.id.nav_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mdrawerlayout.isDrawerVisible(GravityCompat.START)) {
                    mdrawerlayout.closeDrawer(GravityCompat.START);
                } else mdrawerlayout.openDrawer(GravityCompat.START);
            }
        });
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
            case R.id.nav_add:
                fragment=new AddFragment();
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
    @Override
    public void onBackPressed() {
        if (mdrawerlayout.isDrawerVisible(GravityCompat.START)){
            mdrawerlayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }
}
