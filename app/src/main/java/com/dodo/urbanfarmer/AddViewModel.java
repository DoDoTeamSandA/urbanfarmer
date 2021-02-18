package com.dodo.urbanfarmer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddViewModel extends ViewModel {

    public MutableLiveData<String> thoghText,thoughtPicUrl,thoughtNetwork,Addhashtag;
    Context context;
    MediaController mediaController;
    public MutableLiveData<Uri> throughtVideoUri;
    public FirebaseFirestore firebaseFirestore;
    public FirebaseAuth firebaseAuth;
    public String Uid;
    public Map<String,Object> thoughtMap=new HashMap<>();





    public AddViewModel(Context mcontext){
        context=mcontext;
        init();
        mediaController=new MediaController(context);
        Uid=firebaseAuth.getInstance().getUid();
        firebaseFirestore=FirebaseFirestore.getInstance();

    }

    private void init() {
        thoghText=new MutableLiveData();
        thoughtPicUrl=new MutableLiveData<>();
        thoughtNetwork=new MutableLiveData<>();
        Addhashtag=new MutableLiveData<>();
        throughtVideoUri=new MutableLiveData<>();
    }
    public void AddThoughtPic(int code){

        if(code==1){
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");


            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            ((Activity)context).startActivityForResult(chooserIntent,100);
        }else if(code==0){
            Intent gallery=new Intent("android.media.action.IMAGE_CAPTURE");
            ((Activity)context).startActivityForResult(gallery,100);
        }

    }

    public void uploadThoughData(){

        thoughtMap.put("ThoughtText",thoghText);
        thoughtMap.put("ThoughtPicURL",thoughtPicUrl);
        thoughtMap.put("ThoughtNetwork",thoughtNetwork);
        thoughtMap.put("AddHashTag",Addhashtag);
        //thoughtMap.put("ThoughtVideoURL",throughtVideoUri);

        firebaseFirestore.collection("ThoughtData").document(Uid).set(thoughtMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.e("ADDView","Sucess");
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("AddViewError","Flonk");
                Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();



            }
        });



    }

    public void getthoughtVideo(int reqCode) {

        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/mp4");
        ((Activity)context).startActivityForResult(intent,reqCode);

    }
}
