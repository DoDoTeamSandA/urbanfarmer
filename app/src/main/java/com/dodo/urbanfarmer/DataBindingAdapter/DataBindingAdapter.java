package com.dodo.urbanfarmer.DataBindingAdapter;

import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.BindingAdapter;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dodo.urbanfarmer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DataBindingAdapter {
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

    @BindingAdapter("onNavigationItemSelected")
    public static void setOnNavigationItemSelected(
            BottomNavigationView view, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }

    @BindingAdapter("selectedItemPosition")
    public static void setSelectedItemPosition(
            BottomNavigationView view, int position) {
        view.setSelectedItemId(position);
    }
/*


    @BindingAdapter({"onNavigationDrawer","toolbar","drawerLayout"})
    public static void onNavigate(NavigationView nview, Toolbar toolbar, DrawerLayout drawerLayout){
        nview.bringToFront();
        nview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.logout:
                        Toast.makeText(nview.getContext(), "Hello", Toast.LENGTH_SHORT).show();
                        break;
                }


                return true;
            }

        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else drawerLayout.openDrawer(GravityCompat.START);
            }
        });




    }
*/


}
