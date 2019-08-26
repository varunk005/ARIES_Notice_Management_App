package com.aries.www.fgh;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class imgdisplay extends AppCompatActivity  {

    private RecyclerView mrecyclerview;
    private imageadapter madapter;
    private DatabaseReference myref;
    private List<vmdata> mdata;
    private FirebaseStorage firebaseStorage;
    private ValueEventListener mlistener;
private FirebaseAuth mauth;
    private StorageReference storageReference;
    private FirebaseStorage op;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgdisplay);

        mrecyclerview = findViewById(R.id.recyclerimage);
        mrecyclerview.hasFixedSize();
        mrecyclerview.setItemViewCacheSize(20);
        mrecyclerview.setDrawingCacheEnabled(true);
        mrecyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        op = FirebaseStorage.getInstance();


        mdata = new ArrayList<>();
        madapter = new imageadapter(imgdisplay.this, mdata);
        mrecyclerview.setAdapter(madapter);



        firebaseStorage = FirebaseStorage.getInstance();
        myref = FirebaseDatabase.getInstance().getReference("imagepost");
        myref.keepSynced(true);

        mlistener = myref.addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(imgdisplay.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }








    public void downloadfile(Context context, String destinationdirectory, String fileextension, Uri url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);


        DownloadManager.Request request = new DownloadManager.Request(url);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationdirectory, fileextension);
        downloadManager.enqueue(request);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logmenu:{
                logout();
            }
        }
        return super.onOptionsItemSelected(item);

    }
    public void logout()
    {

        mauth.signOut();
        imgdisplay.this.finish();
        startActivity(new Intent(imgdisplay.this,hma.class));
    }



}