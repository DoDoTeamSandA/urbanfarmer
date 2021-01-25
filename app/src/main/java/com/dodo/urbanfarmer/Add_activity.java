package com.dodo.urbanfarmer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dodo.urbanfarmer.databinding.AddPostBinding;

public class Add_activity extends AppCompatActivity {
      public   String[] privacy ={"Anyone","MyNetwork"};
      private   AddPostBinding mAddPostBinder;
      private   AddViewModel addViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_post);
        mAddPostBinder= DataBindingUtil.setContentView(this,R.layout.add_post);
        mAddPostBinder.setLifecycleOwner(this);
        addViewModel=new AddViewModel(Add_activity.this);
        mAddPostBinder.setAddPost(addViewModel);
/*
        Spinner spin = (Spinner) findViewById(R.id.spinner);
       spin.setOnItemSelectedListener(Add_activity.this);

        spin.setAdapter(aa);*/


        mAddPostBinder.Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addViewModel.thoughtNetwork.setValue(parent.getItemAtPosition(position).toString());
                Toast.makeText(Add_activity.this, addViewModel.thoughtNetwork.getValue(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item,privacy);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAddPostBinder.Spinner.setAdapter(aa);

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==100){
            addViewModel.thoughtPicUrl.setValue(data.getDataString());
            Glide.with(this).load(data.getDataString()).into(mAddPostBinder.ThoughtImage);
        }else if(requestCode==101 && resultCode==RESULT_OK){
            addViewModel.throughtVideoUri.setValue(data.getData());

        }else if(requestCode==102 && resultCode==RESULT_OK){

            addViewModel.throughtVideoUri.setValue(data.getData());
            Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
            mAddPostBinder.ThoughtVideo.setVideoURI(addViewModel.throughtVideoUri.getValue());
            mAddPostBinder.ThoughtVideo.requestFocus();
            mAddPostBinder.ThoughtVideo.start();
        }
    }
}