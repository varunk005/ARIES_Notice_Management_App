package com.aries.www.fgh;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Hokme extends AppCompatActivity {

    private EditText da;
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hokme);
da= findViewById(R.id.mnadmin);
        mauth = FirebaseAuth.getInstance();
    }

    public void guest(View view) {


        startActivity(new Intent(Hokme.this,posthome.class));

    }

    public void logout()
    {

        mauth.signOut();
        Hokme.this.finish();
        startActivity(new Intent(Hokme.this,hma.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logmenu: {
                logout();

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void admin(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.blop);
        mp.start();
        String p = da.getText().toString().trim();
        if(p.equals("adsahu")){
            startActivity(new Intent(Hokme.this,posthomtwo.class));

        }
        else
            Toast.makeText(Hokme.this,"Wrong Password",Toast.LENGTH_SHORT).show();

    }


}
