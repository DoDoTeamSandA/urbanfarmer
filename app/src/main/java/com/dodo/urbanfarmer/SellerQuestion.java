package com.dodo.urbanfarmer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SellerQuestion extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    private boolean visited=false;

    private String Current_Uid;

    PreferenceManager preferenceManager;

    private Button yesbtn,nobtn;

   private Intent intent;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_question);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        Current_Uid=firebaseAuth.getCurrentUser().getUid();

        visited=true;

        yesbtn=findViewById(R.id.Yesbtn);
        nobtn=findViewById(R.id.Nobtn);


        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(SellerQuestion.this,Profile.class);
                intent.putExtra("Yesbtn","Yes");
                startActivity(intent);


            }
        });

        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            intent=  new Intent(SellerQuestion.this,Profile.class);
            intent.putExtra("Yesbtn","No");
            startActivity(intent);



            }
        });



       /* preferenceManager=new PreferenceManager(this);

        if(!preferenceManager.FirstLaunch()){
            MainPage();
            finish();
        }*/

     /*   HashMap<String, Boolean> map=new HashMap<>();
        map.put("Visited",visited);




        if(firebaseFirestore.collection("VisiList")
                .document(Current_Uid).set(map)!=null){

        }
*/
    }

    private void MainPage() {
        preferenceManager.setFirstTimeLaunch(false);
        startActivity(new Intent(SellerQuestion.this,MainActivity.class));
        finish();
    }
}
