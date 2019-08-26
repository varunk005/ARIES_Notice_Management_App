package com.aries.www.fgh;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class passwordactivity extends AppCompatActivity {

    private EditText emailreset;
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordactivity);
emailreset =findViewById(R.id.rmilrest);
mauth = FirebaseAuth.getInstance();
    }



    public void reset(View view) {

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.blop);
        mp.start();
        String email = emailreset.getText().toString().trim();

      if(TextUtils.isEmpty(email)){
           emailreset.setError("Error");
        }
        else{
            mauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        final AlertDialog.Builder alert  = new AlertDialog.Builder(passwordactivity.this);
                        View mview = getLayoutInflater().inflate(R.layout.dilogpass,null);
                        Button button = mview.findViewById(R.id.dilogbtnpass);


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
                    else{
                        Toast.makeText(passwordactivity.this,"Error sending password reset",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
