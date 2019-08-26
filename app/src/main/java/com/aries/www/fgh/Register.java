package com.aries.www.fgh;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private EditText name, desg, mobn, email;
    private TextInputLayout rtext,rdesg,remail,rmob;
    private EditText psw;

    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
       

        name = findViewById(R.id.name);
        desg = findViewById(R.id.post);
        mobn = findViewById(R.id.mob);
        psw = findViewById(R.id.pass);
        email = findViewById(R.id.email);
        ref = FirebaseDatabase.getInstance().getReference("newuser").push();

    }


    public void register(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.blop);
        mp.start();

   kreg();






    }




    public void kreg()
    {
        boolean check =false;

        // text utils
        String kname= name.getText().toString().trim();
        String kdesg = desg.getText().toString().trim();
        String kemail = email.getText().toString().trim();
        String kpass = psw.getText().toString().trim();
        String kmob = mobn.getText().toString().trim();
        String devicetoken=FirebaseInstanceId.getInstance().getToken();

        if(TextUtils.isEmpty(kname)&&TextUtils.isEmpty(kemail)&&TextUtils.isEmpty(kpass)&&TextUtils.isEmpty(kmob)){
            name.setError("error");
            desg.setError("error");
            email.setError("error");
            mobn.setError("error");
            psw.setError("error");
        }

        else if(TextUtils.isEmpty(kdesg)){
            desg.setError("error");
        }
        else if
                (TextUtils.isEmpty(kemail)){
            email.setError("error");
        }
        else if(TextUtils.isEmpty(kmob))
            mobn.setError("error");
        else if(TextUtils.isEmpty(kpass)){
            psw.setError("error");
        }
        else if(TextUtils.isEmpty(kname)){
            name.setError("error");
        }
        else
            check=true;



        if(check){
            HashMap<String,String> hashMap = new HashMap<>();

            hashMap.put("email",kemail);
            hashMap.put("mob",kmob);
            hashMap.put("desg",kdesg);
            hashMap.put("name",kname);
            hashMap.put("pass",kpass);

            ref.setValue(hashMap);
            ref.child("devicetoken").setValue(devicetoken).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful()){


                   final AlertDialog.Builder alert  = new AlertDialog.Builder(Register.this);
                   View mview = getLayoutInflater().inflate(R.layout.dilog,null);
                   Button button = mview.findViewById(R.id.dilogbtn);


                   alert.setView(mview);


                   final AlertDialog alertDialog = alert.create();

                   alertDialog.setCanceledOnTouchOutside(false);
                   button.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           alertDialog.dismiss();
                       }
                   });


alertDialog.show();

               }
               else
                   Toast.makeText(Register.this,"error",Toast.LENGTH_LONG).show();
                }
            });

        }
    }


}
