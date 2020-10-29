package com.dodo.urbanfarmer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dodo.urbanfarmer.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URI;
import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    ActivityProfileBinding mBiniding;
    private static final int PICK_IMAGE = 100;
    public   ProfileViewModel profileViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_profile);

        mBiniding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        profileViewModel=new ProfileViewModel(this);


        mBiniding.setLifecycleOwner(this);
        mBiniding.setProfileView(profileViewModel);





        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String yesString = null;
        String NoString = null;

        mBiniding.sellerprofile.setVisibility(View.VISIBLE);
/*

        yesString = extras.getString("Yesbtn");
        NoString = extras.getString("Yesbtn");

        if (yesString.equals("Yes")) {
            mBiniding.viewerprofile.setVisibility(View.VISIBLE);

        } if(NoString.equals("No")) {

        }
*/


        //Firebase Declarations
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && resultCode==RESULT_OK) {
            profileViewModel.ProfilePicuri.setValue(data.getDataString());
            Glide.with(this).load(profileViewModel.ProfilePicuri.getValue()).into(mBiniding.imgeView);
        }else if(requestCode==101 && resultCode==RESULT_OK){
            profileViewModel.aadharPicUri.setValue(data.getDataString());
            mBiniding.sendAadharphoto.setText("Uploaded");
            mBiniding.sendAadharphoto.setBackgroundColor(getResources().getColor(R.color.green_light));
        }


    }
}