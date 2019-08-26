package com.aries.www.fgh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class pdfshow extends AppCompatActivity {
    private RecyclerView mrecyclerview;
    private pdadapter pdr;
    private DatabaseReference myref,tref;
    private ArrayList<dpd> mdata;
    private FirebaseStorage firebaseStorage;
    private ValueEventListener mdb;
    private Button btn;
    private FirebaseAuth mauth;
    EditText editText;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfshow);

        mrecyclerview = findViewById(R.id.pdfrecycler);
        mrecyclerview.hasFixedSize();
        final  LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mrecyclerview.setLayoutManager(mLayoutManager);






        editText= findViewById(R.id.sertext);

        mauth = FirebaseAuth.getInstance();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if(!s.toString().isEmpty()){
                    search(s.toString().toUpperCase());
                    search(s.toString());
                }
                else
                    search("");
            }
        });
        mdata = new ArrayList<>();
        pdr = new pdadapter(pdfshow.this,mdata);
        mrecyclerview.setAdapter(pdr);
        //  madapter.setOnItemClickListener(imagedis.this);
        firebaseStorage = FirebaseStorage.getInstance();
        myref = FirebaseDatabase.getInstance().getReference("pdfupload");
        myref.keepSynced(true);





        mdb = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mdata.clear();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    dpd r = post.getValue(dpd.class);
                    r.setMkey(post.getKey());

                    mdata.add(r);

                }
                pdr.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(pdfshow.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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



    private void search(String s) {

        String jk = "name";

        Query query = myref.orderByChild(jk)
                .startAt(s).endAt(s+"\uf0ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    mdata.clear();
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        dpd r = post.getValue(dpd.class);
                        r.setMkey(post.getKey());

                        mdata.add(r);

                    }
                    pdr.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuph,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutmenu:{
                logout();
            }
        }
        return super.onOptionsItemSelected(item);

    }
    public void logout()
    {

        mauth.signOut();
        pdfshow.this.finish();
        startActivity(new Intent(pdfshow.this,hma.class));
    }

}

