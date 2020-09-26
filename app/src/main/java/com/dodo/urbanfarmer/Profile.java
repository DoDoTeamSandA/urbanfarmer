package com.dodo.urbanfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

public class Profile extends AppCompatActivity {
    private EditText username, DOB, FullName, email, ComapnyName, PhoneNunber;
    private EditText AadharNumber, Pincode, CityName, StateName, HomeAddress, OfficeAddress;
    private Button Savebtn, Editbtn;
    private String usernameStr, DOBStr, FullNameStr, emailStr, ComapnyNameStr, PhoneNunberStr;
    private String AadharNumberStr, PincodeStr, CityNameStr, StateNameStr, HomeAddressStr, OfficeAddressStr;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private ArrayList<String> Fileds;
    private ArrayList<EditText> EditTextValues;
    private String UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();

        String yesString = null, noString = null;

        yesString = extras.getString("Yesbtn");
        noString = extras.getString("Nobtn");

        //Widget declaration
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


        View cardLayout = findViewById(R.id.ProfileCard_layout);

        if (yesString.equals("Yes")) {
            cardLayout.setVisibility(View.VISIBLE);

        } else {
            cardLayout.setVisibility(View.INVISIBLE);
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




}