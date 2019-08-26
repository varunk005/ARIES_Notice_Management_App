package com.aries.www.fgh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class newuser extends AppCompatActivity {
    private RecyclerView mrecyclerview;
    private newuseradapter pdr;
    private DatabaseReference myref;
    private ArrayList<nudata> mdata;
    private FirebaseStorage firebaseStorage;
    private ValueEventListener mdb;
    private FirebaseAuth pauth;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

pauth= FirebaseAuth.getInstance();
        mrecyclerview = findViewById(R.id.newrecycle);
        mrecyclerview.hasFixedSize();
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mdata = new ArrayList<>();
        pdr = new newuseradapter(newuser.this,mdata);
        mrecyclerview.setAdapter(pdr);
        //  madapter.setOnItemClickListener(imagedis.this);

        myref = FirebaseDatabase.getInstance().getReference("newuser");




        mdb = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mdata.clear();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    nudata r = post.getValue(nudata.class);
                    r.setMkey(post.getKey());

                    mdata.add(r);

                }
                pdr.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(newuser.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        pauth.signOut();
        newuser.this.finish();
        Intent intent = new Intent(newuser.this,hma.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
