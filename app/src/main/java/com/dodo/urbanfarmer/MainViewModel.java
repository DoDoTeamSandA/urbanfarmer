package com.dodo.urbanfarmer;


import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.dodo.urbanfarmer.NavigationFragments.AddFragment;
import com.dodo.urbanfarmer.NavigationFragments.HomeFragment;
import com.dodo.urbanfarmer.NavigationFragments.NetWorkFragment;
import com.dodo.urbanfarmer.NavigationFragments.ProfileFragment;
import com.dodo.urbanfarmer.NavigationFragments.ShoppingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

public class MainViewModel extends ViewModel implements BottomNavigationView.OnNavigationItemSelectedListener
{
    public Context context;

     public MainViewModel(Context context){
         this.context=context;
         LoadFragment(new HomeFragment());

    }



    private boolean LoadFragment(Fragment fragment){
        if(fragment!=null){
           ( (FragmentActivity)context).getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.Fragment_Container,fragment)
                   .commit();

            return true;
        }
        return false;
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
                    Intent i = new Intent(context,Add_activity.class);
                    context.startActivity(i);
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
