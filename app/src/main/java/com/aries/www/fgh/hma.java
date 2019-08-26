package com.aries.www.fgh;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class hma extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth pauth;
    private DatabaseReference dref,kref;
    String userd;
    boolean pj;
    private String pk[] = new String [20];
    int l=0;
private EditText e,p;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hma);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pauth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference("allusers");
        e = findViewById(R.id.edittext);
        p = findViewById(R.id.edittext4);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        user = pauth.getCurrentUser();

    /*   if (user != null) {
            userd = pauth.getCurrentUser().getUid();
        }
        kref = FirebaseDatabase.getInstance().getReference("users");

        kref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            boolean chk =false;
                for (DataSnapshot obj : dataSnapshot.getChildren()) {

                    pk[l] = obj.getKey();

                    l++;

                }

                for (int u = 0; u < 10; u++) {

                    if (user != null) {

                        if(pk[u]!=null) {
                            if (userd.equals(pk[u])) {

                                pj=true;
                                break;
                            } else {

                              pj=false;
                                chk = true;
                            }
                        }
                        else
                            break;

                        }
                    }

              if(pj) {
                  hma.this.finish();
                  startActivity(new Intent(hma.this, Hokme.class));
              }
              else if(chk){
                    Log.d("id",userd);
                  hma.this.finish();
                  startActivity(new Intent(hma.this, profilehome.class));
              }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/
    }


        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.admin) {

            startActivity(new Intent(hma.this, AdminLogin.class));

        } else if (id == R.id.About) {

        } else if (id == R.id.website) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://www.aries.res.in/"));
            startActivity(intent);


        } else if (id == R.id.developer) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void reg(View view) {
        startActivity(new Intent(hma.this, Register.class));
    }

    public void rsp(View view) {
        startActivity(new Intent(hma.this, passwordactivity.class));
    }

    public void log(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.blop);
        mp.start();
        String email = e.getText().toString().trim();
        String pass = p.getText().toString().trim();
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pass)) {
            e.setError("error");
            p.setError("error");
        } else if (TextUtils.isEmpty(email)) {
            e.setError("error");
        } else if (TextUtils.isEmpty(pass)) {
            p.setError("error");
        } else {
            final ProgressBar progressDialog = findViewById(R.id.hmaprogress);


            progressDialog.setVisibility(View.VISIBLE);

            pauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                        String userid = task.getResult().getUser().getUid();
                        String devicetoken = FirebaseInstanceId.getInstance().getToken();

                        dref.child(userid).child("devicetoken").setValue(devicetoken);
                        dref.child(userid).child("uid").setValue(userid);


                        progressDialog.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(hma.this, profilehome.class));






                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(hma.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.setVisibility(View.INVISIBLE);
                }
            });
        }

    }
}

