package com.dodo.urbanfarmer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.MediaController;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddViewModel extends ViewModel {

    public MutableLiveData<String> thoghText,thoughtPicUrl,thoughtNetwork,Addhashtag;
    Context context;
    MediaController mediaController;
    public MutableLiveData<Uri> throughtVideoUri;


    public AddViewModel(Context mcontext){
        context=mcontext;
        init();
        mediaController=new MediaController(context);
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

    }

    public void getthoughtVideo(int reqCode) {

        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/mp4");
        ((Activity)context).startActivityForResult(intent,reqCode);

    }
}
