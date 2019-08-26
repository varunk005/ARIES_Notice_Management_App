package com.aries.www.fgh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class imagesdistwo extends AppCompatActivity {
    private RecyclerView mrecyclerview;
    private vmadapter madapter;
    private DatabaseReference myref;
    private List<vmdata> mdata;
    private FirebaseStorage firebaseStorage;
    private ValueEventListener mdb;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagesdistwo);
        mrecyclerview = findViewById(R.id.recyclerimagetwo);
        mrecyclerview.hasFixedSize();
        mrecyclerview.setItemViewCacheSize(20);
        mrecyclerview.setDrawingCacheEnabled(true);
        mrecyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        final  LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mrecyclerview.setLayoutManager(mLayoutManager);


        mdata = new ArrayList<>();
        madapter = new vmadapter(imagesdistwo.this, mdata);
        mrecyclerview.setAdapter(madapter);
        //  madapter.setOnItemClickListener(imagedis.this);
        firebaseStorage = FirebaseStorage.getInstance();
        myref = FirebaseDatabase.getInstance().getReference("imagepost");
        myref.keepSynced(true);

        mdb = myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mdata.clear();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    vmdata r = post.getValue(vmdata.class);
                    r.setMkey(post.getKey());

                    mdata.add(r);

                }
                madapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(imagesdistwo.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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




    public void onBackPressed() {
        startActivity(new Intent(imagesdistwo.this,posthomtwo.class));

    }

}
