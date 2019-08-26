package com.aries.www.fgh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class profilehome extends AppCompatActivity {

    private FirebaseAuth mauth;

    private BottomNavigationView btm;
    private RecyclerView mrecyclerview;
    private usertextadap pdr;
    private DatabaseReference texref ;
    private ArrayList<text> mdata;




    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilehome);
        mauth = FirebaseAuth.getInstance();
        btm = findViewById(R.id.fr);
        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.not:
                        Intent intentk = new Intent(profilehome.this, pdfview.class);
                        startActivity(intentk);
                        return true;
                    case R.id.imgnt:
                        Intent intent = new Intent(profilehome.this, imgdisplay.class);
                        startActivity(intent);
                        return true;

                    default:
                        return false;
                }
            }
        });

        mrecyclerview = findViewById(R.id.texuserrecycler);
        mrecyclerview.hasFixedSize();

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mrecyclerview.setLayoutManager(mLayoutManager);


        mdata = new ArrayList<>();
        pdr = new usertextadap(profilehome.this,mdata);
        mrecyclerview.setAdapter(pdr);
        //  madapter.setOnItemClickListener(imagedis.this);

        texref = FirebaseDatabase.getInstance().getReference("textupload");

        texref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mdata.clear();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    text r = post.getValue(text.class);
                    r.setMkey(post.getKey());

                    mdata.add(r);

                }
                pdr.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(profilehome.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mrecyclerview.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {

                    loading = true;
                }
            }

        });





    }


    //on button log out
    public void logout() {

        mauth.signOut();
        profilehome.this.finish();
        startActivity(new Intent(profilehome.this, hma.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log, menu);


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


}
