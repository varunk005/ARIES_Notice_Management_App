package com.aries.www.fgh;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Map;
import java.util.Objects;

public class AdminLogin extends AppCompatActivity {
    private FirebaseAuth mauth, pauth;
    private EditText e, p;
    private String m, em, u, email, pass;
    private static Boolean check = false;
    private static int i = 0;
    String r[] = new String[20];
    private static int l = 0;

    String userd;
    int jk;
    FirebaseUser user;
    private DatabaseReference dref, kref;
    private String vm[] = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mauth = FirebaseAuth.getInstance();
        pauth = FirebaseAuth.getInstance();
        e = findViewById(R.id.adminemail);
        p = findViewById(R.id.adminpass);
        user = mauth.getCurrentUser();
        if (user != null) {
            userd = mauth.getCurrentUser().getUid();
        }
        kref = FirebaseDatabase.getInstance().getReference("users");

        kref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot obj : dataSnapshot.getChildren()) {

                    r[l] = obj.getKey();

                    l++;

                }
                for (int u = 0; u < 10; u++) {
                    if (user != null) {
                        if (userd.equals(r[u])) {
                            AdminLogin.this.finish();
                            startActivity(new Intent(AdminLogin.this, Hokme.class));
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dref = FirebaseDatabase.getInstance().getReference("users");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot obj : dataSnapshot.getChildren()) {

                    m = obj.getKey();
                    Log.d("s", m);
                    dref.child(m).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();

                            vm[i] = map.get("email");

                            i++;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void adminlog(View view) {

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.blop);
        mp.start();

        email = e.getText().toString().trim();
        pass = p.getText().toString().trim();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pass)) {
            e.setError("error");
            p.setError("error");
        } else if (TextUtils.isEmpty(email)) {
            e.setError("error");
        } else if (TextUtils.isEmpty(pass)) {
            p.setError("error");
        } else {
            for (int k = 0; k < vm.length; k++) {

                if (email.equals(vm[k])) {
                    final ProgressBar progressDialog = findViewById(R.id.adminprogress);


                    progressDialog.setVisibility(View.VISIBLE);
                    mauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                String userid = task.getResult().getUser().getUid();
                                String devicetoken = FirebaseInstanceId.getInstance().getToken();

                                dref.child(userid).child("devicetoken").setValue(devicetoken);
                                dref.child(userid).child("uid").setValue(userid);

                                progressDialog.setVisibility(View.INVISIBLE);
                                Intent intentk = new Intent(AdminLogin.this, Hokme.class);
                                intentk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentk);

                                Log.d("r", "loggedin");
                            }
                            else{
                                Exception e = new Exception();
                                Toast.makeText(AdminLogin.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminLogin.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.setVisibility(View.INVISIBLE);
                        }
                    });


                }

            }
        }
    }
}