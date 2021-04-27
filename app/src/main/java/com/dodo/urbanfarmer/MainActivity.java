package com.dodo.urbanfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dodo.urbanfarmer.NavigationFragments.HomeFragment;
import com.dodo.urbanfarmer.NavigationFragments.NetWorkFragment;
import com.dodo.urbanfarmer.NavigationFragments.ProfileFragment;
import com.dodo.urbanfarmer.NavigationFragments.ShoppingFragment;
import com.dodo.urbanfarmer.SideNavFragments.AboutUsFragment;
import com.dodo.urbanfarmer.SideNavFragments.BucketListFragment;
import com.dodo.urbanfarmer.SideNavFragments.CategoryFragment;
import com.dodo.urbanfarmer.SideNavFragments.DealsFragment;
import com.dodo.urbanfarmer.SideNavFragments.LearningBlogFragment;
import com.dodo.urbanfarmer.SideNavFragments.NurseryFragment;
import com.dodo.urbanfarmer.SideNavFragments.OrdersFragment;
import com.dodo.urbanfarmer.SideNavFragments.SellersPageFragment;
import com.dodo.urbanfarmer.SideNavFragments.SettingsFragment;
import com.dodo.urbanfarmer.databinding.ActivityMainBinding;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
        setContentView(R.layout.activity_main);
*/

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel mainViewModel = new MainViewModel(this);
        mainBinding.setLifecycleOwner(this);
        mainBinding.setMainViewModel(mainViewModel);



        mAuth = FirebaseAuth.getInstance();

        //Actionbar Support
        setSupportActionBar(mainBinding.toolbar);
        navigationDrawer();


        //BottomNavigationView declartion

        //Floating
        //No idea
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.Web_ClientId))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void navigationDrawer() {
        mainBinding.navigationView.bringToFront();
        mainBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                Fragment fragment=null;

                switch (item.getItemId()) {
                    case R.id.logout:
                        logout();
                        break;
                    case R.id.nav_home:
                        fragment=new HomeFragment();
                        break;
                    case R.id.profile:
                        fragment=new ProfileFragment();
                        break;
                    case R.id.mynetwork:
                        fragment=new NetWorkFragment();
                        break;

                    case R.id.shop:
                        fragment=new ShoppingFragment();
                        break;
                    case R.id.shopbycategory:
                        fragment=new CategoryFragment();
                        break;
                    case R.id.dealsoftheday:
                        fragment=new DealsFragment();
                        break;
                    case R.id.bucketlist:
                        fragment=new BucketListFragment();
                        break;
                    case R.id.yourorders:
                        fragment=new OrdersFragment();
                        break;
                    case R.id.sellonurbanfarmer:
                        fragment=new SellersPageFragment();
                        break;
                    case R.id.learnfromus:
                        fragment=new LearningBlogFragment();
                        break;
                    case R.id.OnlinePlantNursery:
                        fragment=new NurseryFragment();
                        break;
                    case R.id.settings:
                        fragment=new SettingsFragment();
                        break;
                    case R.id.AboutUs:
                        fragment=new AboutUsFragment();
                        break;
                }

                mainBinding.drawerlayout.closeDrawer(GravityCompat.START);


                return LoadFragment(fragment);
            }
        });
        mainBinding.navigationView.setCheckedItem(R.id.nav_home);
        mainBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainBinding.drawerlayout.isDrawerVisible(GravityCompat.START)) {
                    mainBinding.drawerlayout.closeDrawer(GravityCompat.START);
                } else mainBinding.drawerlayout.openDrawer(GravityCompat.START);
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
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, ":Logged Out", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    @Override
    public void onBackPressed() {
        if (mainBinding.drawerlayout.isDrawerVisible(GravityCompat.START)) {
            mainBinding.drawerlayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    private boolean LoadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Fragment_Container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

}
