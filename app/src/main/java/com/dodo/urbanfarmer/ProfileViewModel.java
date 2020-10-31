package com.dodo.urbanfarmer;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.dodo.urbanfarmer.Pojos.ProfilePojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class ProfileViewModel extends ViewModel {






    ProfilePojo profilePojo;

    public MutableLiveData<String> usernameStr, DOBStr, FullNameStr, emailStr, ComapnyNameStr, PhoneNunberStr;
    public MutableLiveData<String> AadharNumberStr, PincodeStr, CityNameStr, StateNameStr, HomeAddressStr, OfficeAddressStr;
    public MutableLiveData<String> ProfilePicuri,aadharPicUri;
    public  String [] fileds={"username","DOB","FullName","email","ComapnyName","PhoneNunber","AadharNumber","Pincode","CityName","StateName","HomeAddress","OfficeAddress","ProfilePicUri","AaadharPic"};
    public List<String> filedsList;
    public ArrayList<String> values;
    public MutableLiveData<Boolean> qus;
    private String mVerificationId;
    private Dialog dialog;
    public MutableLiveData<String> code;
    Context context;



    MutableLiveData<String> imageUri;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    public ProfileViewModel(Context context) {
        this.context=context;
        init();
        checkLength();
    }


    private void init() {
     usernameStr= new MutableLiveData<>();
        DOBStr=new MutableLiveData<>();
        FullNameStr= new MutableLiveData<>();
        emailStr=new MutableLiveData<>();
        ComapnyNameStr=new MutableLiveData<>();
        PhoneNunberStr=new MutableLiveData<>();
        AadharNumberStr=new MutableLiveData<>();
        PincodeStr= new MutableLiveData<>();
        CityNameStr=new MutableLiveData<>();
        StateNameStr=new MutableLiveData<>();
        HomeAddressStr=new MutableLiveData<>();
        OfficeAddressStr=new MutableLiveData<>();
        profilePojo=new ProfilePojo();
        ProfilePicuri=new MutableLiveData<>();
        aadharPicUri=new MutableLiveData<>();
        qus=new MutableLiveData<>(false);
        //Firebase
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();

        //Lists with values
        filedsList=Arrays.asList(fileds);
        values=new ArrayList<>();

        //Dialog
        dialog=new Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert);

        dialog.setContentView(R.layout.activity_o_t_p_verification);
        //otp code
        code=new MutableLiveData<>(" ");



    }

    // save button onclickListner
    public void onSubmitbtnClick(){
        try {
            profilePojo.setUsernameStr(usernameStr.getValue());
            profilePojo.setDOBStr(DOBStr.getValue());
            profilePojo.setFullNameStr(FullNameStr.getValue());
            profilePojo.setEmailStr(emailStr.getValue().trim());
            profilePojo.setComapnyNameStr(ComapnyNameStr.getValue());
            profilePojo.setPhoneNunberStr(PhoneNunberStr.getValue());
            profilePojo.setAadharNumberStr(AadharNumberStr.getValue());
            profilePojo.setPincodeStr(PincodeStr.getValue());
            profilePojo.setCityNameStr(CityNameStr.getValue());
            profilePojo.setStateNameStr(StateNameStr.getValue());
            profilePojo.setHomeAddressStr(HomeAddressStr.getValue());
            profilePojo.setOfficeAddressStr(OfficeAddressStr.getValue());
            profilePojo.setProfiePicuri(ProfilePicuri.getValue());
            profilePojo.setAadherPicUri(aadharPicUri.getValue());

            values.addAll(profilePojo.getValues());

            if(profilePojo.isValidate()){

                Log.i("TAG_V", "u can update");

                Uploadata(filedsList,values);
            }

//            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Pictures: "), 1);



        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Please fill all values", Toast.LENGTH_SHORT).show();


        }


    }
    //Firebase upload data button
    public void Uploadata(List<String> filedsList,ArrayList<String> values){

        String Uid=firebaseUser.getUid();

      HashMap<String,String> map=new HashMap<>();

      Log.i("TAG1", ""+values.size());
      Log.i("TAG2", ""+filedsList.size());

      for(int i=0;i<filedsList.size();i++){

          map.put(filedsList.get(i),values.get(i));
      }

        firebaseFirestore.collection("Users").document(Uid).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Sucess", Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    //the callback to detect the verification status

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

             code.setValue(phoneAuthCredential.getSmsCode());


            if (code != null) {
                //verifying the code
                verifyVerificationCode(code.getValue());
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mVerificationId = s;
        }
    };

    // Phonenumnber verification
    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        if(code!=null){

            qus.setValue(true);
            dialog.dismiss();


        }



    }

    private void checkLength() {
        PhoneNunberStr.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.length()==10){
                    SendOTP();
                    dialog.show();
                    Button button=dialog.findViewById(R.id.verifybtn);
                    EditText codeText=dialog.findViewById(R.id.otpreciverET);
                    code.observeForever(new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            codeText.setText(s);
                        }
                    });

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            code.setValue(codeText.getText().toString().trim());

                            verifyVerificationCode(code.getValue());
                        }
                    });
                }
            }
        });
    }
    // Phonenumnber verification
    public void SendOTP() {
        Toast.makeText(context, "Please wait", Toast.LENGTH_SHORT).show();
        String mobile = PhoneNunberStr.getValue();
        if (!mobile.isEmpty()) {
            dialog.show();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + mobile,
                    60,
                    TimeUnit.SECONDS,
                    TaskExecutors.MAIN_THREAD,
                    mCallbacks);
        }else {
            Toast.makeText(context, "Please Enter Phone number", Toast.LENGTH_SHORT).show();
        }

    }


    public void onPicImage(int reqCode){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        ((Activity)context).startActivityForResult(gallery,reqCode);
    }

    public void onPickDate(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String s=dayOfMonth+"/"+month+"/"+year;

                DOBStr.setValue(s);
            }
        },year,month,day);

        datePickerDialog.show();
    }






}
