package com.aries.www.fgh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {
    private FirebaseAuth pauth;
    private DatabaseReference dref,kref;
    String userd;
    boolean pj;
    private String pk[] = new String [20];
    int l=0;
    private int sleep_timer=1200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        logo logg = new logo();
        logg.start();
        pauth = FirebaseAuth.getInstance();

    }
    // manifest me jakar ke intet wala part main activity se splash screen me copy kr do fir splash screen first
    //activity n=bn jayegi
    private class logo extends Thread {
        @Override
        public void run() {
            try {
                sleep(sleep_timer);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Splash.this.finish();
            //Intent intent = new Intent(Splash.this,hma.class);
            //startActivity(intent);

            final FirebaseUser user = pauth.getCurrentUser();

            if (user != null) {
                userd = pauth.getCurrentUser().getUid();

                kref = FirebaseDatabase.getInstance().getReference("users");

                kref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean chk = false;
                        for (DataSnapshot obj : dataSnapshot.getChildren()) {

                            pk[l] = obj.getKey();

                            l++;

                        }

                        for (int u = 0; u < 10; u++) {

                            if (user != null) {

                                if (pk[u] != null) {
                                    if (userd.equals(pk[u])) {

                                        pj = true;
                                        break;
                                    } else {

                                        pj = false;
                                        chk = true;
                                    }
                                } else
                                    break;

                            }
                        }

                        if (pj) {
                            Splash.this.finish();
                            startActivity(new Intent(Splash.this, Hokme.class));
                        } else if (chk) {
                            Log.d("id", userd);
                            Splash.this.finish();
                            startActivity(new Intent(Splash.this, profilehome.class));
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            } else {
                Splash.this.finish();
                Intent intent = new Intent(Splash.this, hma.class);
                startActivity(intent);
            }

            //
        }

    }     }


