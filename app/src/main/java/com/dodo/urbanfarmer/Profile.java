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
    private EditText username, DOB, FullName, email, ComapnyName, PhoneNunber;
    private EditText AadharNumber, Pincode, CityName, StateName, HomeAddress, OfficeAddress;
    private Button Savebtn, Editbtn;
    private String usernameStr, DOBStr, FullNameStr, emailStr, ComapnyNameStr, PhoneNunberStr;
    private String AadharNumberStr, PincodeStr, CityNameStr, StateNameStr, HomeAddressStr, OfficeAddressStr;
    private LinearLayout sellerprofile;
    private LinearLayout viewerprofile;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private ArrayList<String> Fileds;
    private ArrayList<EditText> EditTextValues;
    private String UserId;

    ActivityProfileBinding mBiniding;

    private static final int PICK_IMAGE = 100;



    private ProfileViewModel viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_profile);

        mBiniding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        ProfileViewModel profileViewModel=new ProfileViewModel(this);
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


        mBiniding.imgeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });













    /*    //Widget declaration
        username = findViewById(R.id.Username);
        DOB = findViewById(R.id.DOB);
        FullName = findViewById(R.id.FullName);
        email = findViewById(R.id.EmailAddress);
        ComapnyName = findViewById(R.id.CompanyName);
        PhoneNunber = findViewById(R.id.MobilerNumber);
        AadharNumber = findViewById(R.id.AadharNumber);
        Pincode = findViewById(R.id.Pincode);
        CityName = findViewById(R.id.CityName);
        StateName = findViewById(R.id.StateName);
        HomeAddress = findViewById(R.id.HomeAddress);
        OfficeAddress = findViewById(R.id.OfficeAddress);
        Savebtn = findViewById(R.id.Savebtn);
        Editbtn = findViewById(R.id.Editbtn);
        sellerprofile =  (LinearLayout) findViewById(R.id.sellerprofile);
        viewerprofile = (LinearLayout) findViewById(R.id.viewerprofile);

        //adding to arrayList
        EditTextValues = new ArrayList<>();
        EditTextValues.add(username);
        EditTextValues.add(DOB);
        EditTextValues.add(FullName);
        EditTextValues.add(email);
        EditTextValues.add(ComapnyName);
        EditTextValues.add(PhoneNunber);
        EditTextValues.add(AadharNumber);
        EditTextValues.add(Pincode);
        EditTextValues.add(CityName);
        EditTextValues.add(StateName);
        EditTextValues.add(HomeAddress);
        EditTextValues.add(OfficeAddress);

        Log.i("Check",String.valueOf(EditTextValues.size()));

        Fileds = new ArrayList<>();

        Fileds.add("username");
        Fileds.add("DOB");
        Fileds.add("FullName");
        Fileds.add("email");
        Fileds.add("ComapnyName");
        Fileds.add("PhoneNunber");
        Fileds.add("AadharNumber");
        Fileds.add("Pincode");
        Fileds.add("CityName");
        Fileds.add("StateName");
        Fileds.add("HomeAddress");
        Fileds.add("OfficeAddress");


        //Firebase Declarations
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();

        if(firebaseUser!=null) {


            UserId = mAuth.getCurrentUser().getUid();


List
            Savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<String> valueString = new ArrayList<>();


                    for (int values = 0; values < EditTextValues.size(); values++) {
                        valueString.add(EditTextValues.get(values).getText().toString());


                    }

                    int flag = 0;

                    if (CheckValues(valueString,flag,EditTextValues)!=0) {

                        Toast.makeText(Profile.this, "Fill Fill", Toast.LENGTH_SHORT).show();

                    } else {



                    HashMap<String, Object> map = new HashMap<>();

                    for (int j = 0; j < Fileds.size(); j++) {
                        map.put(Fileds.get(j), valueString.get(j));
                    }

                    firebaseFirestore.collection("Users").document(UserId).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(Profile.this, "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Profile.this, MainActivity.class));
                            } else {
                                Toast.makeText(Profile.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

                    }


            });

        }




        if (yesString.equals("Yes")) {
            sellerprofile.setVisibility(View.VISIBLE);

        } if(NoString.equals("No")) {
            viewerprofile.setVisibility(View.VISIBLE);
        }


    }

    public void UploadValues(HashMap<String, Object> map) {

    }

    public int VerifyNull(String username,String DOB, String Fullname,String ComapnyName,String EmailAddress,
                          String Phonenunber,String AadharNumber,String pincode,String CityName,String StateName,String HomeAddress,String OfficeAddress
                          ){


        if(username.isEmpty() || DOB.isEmpty()|| Fullname.isEmpty()||ComapnyName.isEmpty()|| EmailAddress.isEmpty()||Phonenunber.isEmpty()||AadharNumber.isEmpty()||
        pincode.isEmpty()||CityName.isEmpty()||StateName.isEmpty()||HomeAddress.isEmpty()||OfficeAddress.isEmpty()){

            return 0;
        }else{
            return 1;
        }









    }


    public int CheckValues(ArrayList<String> valueString,int flag,ArrayList<EditText> editTextValues){

        for (String input : valueString) {
            if (!input.isEmpty()) {
                flag++;

            }
        }

        if(Patterns.EMAIL_ADDRESS.matcher(valueString.get(3)).find()){
            editTextValues.get(3).setError("Please Enter Valid Email");
        }


        if(flag==valueString.size()){
            return 0;
        }else {
            return 1;
        }

    }
*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK) {

            viewModel.uri.setValue(data.getDataString());


            Log.i("TAG_A", "onActivityResult: "+viewModel.uri.getValue());

            Glide.with(this).load(viewModel.uri.getValue()).into(mBiniding.imgeView);





        }else{

        }










            }
}