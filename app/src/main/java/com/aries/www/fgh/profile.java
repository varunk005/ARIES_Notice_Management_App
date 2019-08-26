package com.aries.www.fgh;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
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
import com.google.firebase.storage.UploadTask;

import java.util.Map;

public class profile extends AppCompatActivity {

    final static private int img = 1;
    private Uri imuri;
    private ImageButton btn;
private EditText name , desg,mobn;
private FirebaseAuth mauth;
private StorageReference storageReference;
private DatabaseReference ref , sef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn = findViewById(R.id.imageView);
        name = findViewById(R.id.profilename);
        desg = findViewById(R.id.profiledesg);
        mobn = findViewById(R.id.profilemob);

        mauth= FirebaseAuth.getInstance();
        storageReference =FirebaseStorage.getInstance().getReference("profile");
        ref = FirebaseDatabase.getInstance().getReference("mprofile").child(mauth.getCurrentUser().getUid());
        sef=FirebaseDatabase.getInstance().getReference("mprofile").child(mauth.getCurrentUser().getUid());

        sef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();

                    String a = map.get("name");
                    String b = map.get("dsg");
                    String c = map.get("mobno");
                    String d = map.get("primg");

                    name.setText(a);
                    desg.setText(b);
                    mobn.setText(c);

                    Glide.with(profile.this)
                            .load(d)
                            .fitCenter()
                            .centerCrop()

                            .placeholder(R.drawable.ic_menu_gallery)

                            .into(btn);
                }catch (Exception e){
                    Toast.makeText(profile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void chooseimage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, img);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == img && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imuri = data.getData();
            Glide.with(profile.this)
                    .load(imuri)
                    .fitCenter()
                    .centerCrop()

                    //  .placeholder(R.drawable.def)
                    // .error(R.drawable.error)
                    .into(btn);
        }


    }


    public void savedata(View view) {

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.blop);
        mp.start();

        if(imuri!=null){

            final StorageReference filestore =storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imuri));

            filestore.putFile(imuri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();

                    }


                    return filestore.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloaduri = task.getResult();


                       // profiledata datarecycle = new vmdata(/*editText.getText().toString().trim(),*/downloaduri.toString());
                        profiledata profiledata = new profiledata(name.getText().toString(),desg.getText().toString(),
                                downloaduri.toString(),     mobn.getText().toString());

                       // String uploadid = myref.push().getKey();
                        ref.child("name").setValue(profiledata.getPrname());
                        ref.child("dsg").setValue(profiledata.getPrdesg());
                        ref.child("mobno").setValue(profiledata.getPrmob());
                        ref.child("primg").setValue(profiledata.getPrimage());


                        Toast.makeText(profile.this, "upload successful", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }


    private  String getFileExtension(Uri uri){
        ContentResolver cr =getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
}
