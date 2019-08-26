package com.aries.www.fgh;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class posthomtwo extends AppCompatActivity {


    public static final int Image_request = 1,camerarequest=3;
    public static final int pdf_request=2;
    static int count ;
    private ImageButton imageButton;
    private FirebaseAuth mauth;
    private Uri imageuri,camerauri,path;
    private StorageReference storageReference,pdfstore;
    private DatabaseReference myref,tref,jref,kref,ref,notifref,callref;
    private EditText pdfname;
    private String deviceIdentifier;
    private String forcircle,senderid;
    final int PIC_CROP = 1;
    private String m[] = new String[100];
    private TextView th;
    Context mcontext;
    private String a,b,c;
    ImageView idl;
    ImageView dekho;
    EditText editTexttitle;
    BottomNavigationView btm;
    private String picpath;
    private RecyclerView mrecyclerview;
    private textadap pdr;
    private DatabaseReference texref ;
    private ArrayList<text> mdata;
    private Integer checkm ;
    private String pictureFilePath;

    private FirebaseUser firebaseUser;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private File file;
    private Uri uri;
    private byte[] datam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posthomtwo);



      assert getSupportActionBar() != null;
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        idl = findViewById(R.id.dialogimage);
        //  th = findViewById(R.id.textView);
        // pdfname = findViewById(R.id.pdfname);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //  imageButton = findViewById(R.id.imageButton);
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        pdfstore = FirebaseStorage.getInstance().getReference("pdf");
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        senderid = mauth.getCurrentUser().getUid();
        myref = FirebaseDatabase.getInstance().getReference("adminupload").child(senderid);

        ref = FirebaseDatabase.getInstance().getReference("mprofile").child(mauth.getCurrentUser().getUid());
        tref = FirebaseDatabase.getInstance().getReference("imagepost");
        jref = FirebaseDatabase.getInstance().getReference("pdfupload");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        callref = FirebaseDatabase.getInstance().getReference("allusers");

        notifref = FirebaseDatabase.getInstance().getReference().child("Notifications");
        //   getInstallationIdentifier();

        dekho = findViewById(R.id.imgshowtwo);
        dekho.setVisibility(View.GONE);
        firebaseUser=mauth.getCurrentUser();

        mrecyclerview = findViewById(R.id.postrecylertwo);
        mrecyclerview.hasFixedSize();

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mrecyclerview.setLayoutManager(mLayoutManager);


        mdata = new ArrayList<>();
        pdr = new textadap(posthomtwo.this,mdata);
        mrecyclerview.setAdapter(pdr);
        //  madapter.setOnItemClickListener(imagedis.this);

        texref = FirebaseDatabase.getInstance().getReference("textupload");
        myref.keepSynced(true);

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
                Toast.makeText(posthomtwo.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void logout()
    {

        mauth.signOut();
        posthomtwo.this.finish();
        startActivity(new Intent(posthomtwo.this,hma.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phmtwo,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutmenutwo: {
                logout();
                break;
            }

            case R.id.viewpdftwo:{
                startActivity(new Intent(posthomtwo.this,pdfshow.class));
                break;
            }
            case R.id.viewgallerytwo:{
                startActivity(new Intent(posthomtwo.this,imagedis.class));
                break;
            }
            case R.id.profiletwo:{
                startActivity(new Intent(posthomtwo.this,profile.class));
                break;
            }
            case R.id.gallerytwo: {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Image_request);

                break;
            }
            case R.id.cameratwo: {



                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                launchCamera();
                break;
            }



            case R.id.filetwo: {
                browseDocuments();
                break;
            }
            case R.id.newustwo:{
                startActivity(new Intent(posthomtwo.this,newuser.class));
                break;

            }

        }
        return super.onOptionsItemSelected(item);

    }

    /*   private File create() {
           File pic = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM);
           String picname = getpicname();
           File img = new File(pic,picname);
           picpath=img.getAbsolutePath();
           return img;
       }

       private String getpicname() {

           SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
           String st= sdf.format(new Date());
           return st+".jpeg";
       }

   */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    /*    if(requestCode==Image_request&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null) {

            imageuri=data.getData();



            dekho.setImageURI(imageuri);

            dekho.setVisibility(View.VISIBLE);
            mrecyclerview.setVisibility(View.GONE);

            posthomtwo.this.checkm=1;
            //  uploadimage(imageuri,"sfd");




        }

        else if( requestCode==pdf_request&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){

            path =data.getData();
            uploadpdffile(path);

        }


        else if( requestCode==camerarequest&&resultCode==RESULT_OK) {

            File imgFile = new  File(pictureFilePath);
            if(imgFile.exists())
            {


                dekho.setImageURI(Uri.fromFile(imgFile));
                dekho.setVisibility(View.VISIBLE);
                mrecyclerview.setVisibility(View.GONE);


                camerauri=Uri.parse(pictureFilePath);
                posthomtwo.this.checkm=2;

                //uploadimagetwo(camerauri,"b");
            }
*/
        switch (requestCode) {
            case camerarequest:

                CropImage.activity(android.net.Uri.parse(file.toURI().toString()))
                        .setAspectRatio(1, 1)
                        .setFixAspectRatio(false)

                        .start(posthomtwo.this);
                posthomtwo.this.checkm=2;
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    uri=result.getUri();
                    File  thisfile = new File(uri.getPath());
                    try {
                        Bitmap compressedImage = new Compressor(this)
                                .setMaxWidth(1024)
                                .setMaxHeight(581)
                                .setQuality(50)

                                .compressToBitmap(thisfile);


                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        compressedImage.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                        datam = byteArrayOutputStream.toByteArray();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                    if(checkm==2) {
                        dekho.setImageURI(result.getUri());
                        dekho.setVisibility(View.VISIBLE);
                        mrecyclerview.setVisibility(View.GONE);
                        camerauri=result.getUri();
                    }else if(checkm==1){
                        dekho.setImageURI(result.getUri());
                        dekho.setVisibility(View.VISIBLE);
                        mrecyclerview.setVisibility(View.GONE);
                        imageuri=result.getUri();
                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Log.d("fsd", "onActivityResult: " + error.getMessage());
                }
                break;

            case Image_request:

                try {
                    CropImage.activity(data.getData())
                            .setAspectRatio(1, 1)
                            .setFixAspectRatio(false)

                            .start(posthomtwo.this);
                    posthomtwo.this.checkm = 1;
                }catch (Exception e){

                }
                break;
            case pdf_request:

                if (resultCode == RESULT_OK && data != null && data.getData() != null) {

                    path = data.getData();
                    uploadpdffile(path);

                    break;
                }


        }
    }






    private void uploadimagetwo(final Uri mdata, final String title) {
        if(camerauri!=null){


            final ProgressBar progressDialog =findViewById(R.id.progresstwo);

            progressDialog.setVisibility(View.VISIBLE);
            final  StorageReference filestore =storageReference.child(System.currentTimeMillis()+".jpg");
            UploadTask uploadTask = filestore.putBytes(datam);
           uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                        final Uri downloaduri = task.getResult();


                        final File file = new File(downloaduri.toString());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                                a = map.get("primg");
                                b = map.get("name");
                                String filename =file.getName();

                                String sb = System.currentTimeMillis()+".jpg";
                                Log.d("n",sb);
                                Date date = new Date();

                                String datem =  SimpleDateFormat.getDateTimeInstance().format(date);
                                vmdata datarecycle =new vmdata(downloaduri.toString(),a,sb,title,datem,b);
                                String uploadid =myref.push().getKey();
                                myref.child("imagen").push().setValue(datarecycle);


                                tref.child(uploadid).setValue(datarecycle);



                                Toast.makeText(posthomtwo.this,"upload successful",Toast.LENGTH_SHORT).show();
                                notification();
                                progressDialog.setVisibility(View.INVISIBLE);
                                dekho.setVisibility(View.GONE);
                                mrecyclerview.setVisibility(View.VISIBLE);
                                startActivity(new Intent(posthomtwo.this,imagesdistwo.class));

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    else{

                        Toast.makeText(posthomtwo.this,"upload failed"+task.getException(),Toast.LENGTH_SHORT).show();
                        progressDialog.setVisibility(View.INVISIBLE);
                        mrecyclerview.setVisibility(View.VISIBLE);
                    }
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(posthomtwo.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.setVisibility(View.INVISIBLE);
                    mrecyclerview.setVisibility(View.VISIBLE);
                }
            });
        }
        else{


            Toast.makeText(posthomtwo.this,"No file selected",Toast.LENGTH_SHORT).show();
            mrecyclerview.setVisibility(View.VISIBLE);
        }

    }

    private void uploadpdffile(final Uri data) {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("uploading..");
        progressDialog.show();
        final  StorageReference pdffilestore =pdfstore.child(System.currentTimeMillis()+"."+getFileExtension(data));
        pdffilestore.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();

                while (!uri.isComplete());
                Uri url = uri.getResult();
                File file = new File(data.toString());
                String filename =file.getName();
                Log.d("uri",data.toString());
                Log.d("filename",filename);
                Date date = new Date();

                String datem =  SimpleDateFormat.getDateTimeInstance().format(date);
                dpd  object = new dpd(filename,url.toString(),datem);
                myref.child("pdfn").push().setValue(object);
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());


                String uploadpdfid =myref.push().getKey();
                jref.child(uploadpdfid).setValue(object);

                Toast.makeText(posthomtwo.this,"File uploaded",Toast.LENGTH_SHORT).show();
                notification();
                progressDialog.dismiss();
                startActivity(new Intent(posthomtwo.this,pdfshow.class));

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("uploaded"+(int)progress+"%");

            }
        });
    }

    public void chooseimage(Uri data) {


    }

    public void uploadimage(final Uri data, final String title) {



        if(imageuri!=null){
            final ProgressBar progressDialog = findViewById(R.id.progresstwo);
            progressDialog.setVisibility(View.VISIBLE);

            final  StorageReference filestore =storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageuri));
            UploadTask uploadTask = filestore.putBytes(datam);
          uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                        final Uri downloaduri = task.getResult();

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Map<String,String> map = ( Map<String,String>) dataSnapshot.getValue();

                                a= map != null ? map.get("primg") : null;
                                b=map!=null ? map.get("name") :null;
                                File file = new File(data.toString());
                                String filename = System.currentTimeMillis()+".jpg";
                                Date date = new Date();

                                String datem =  SimpleDateFormat.getDateTimeInstance().format(date);
                                vmdata datarecycle =new vmdata(downloaduri.toString(),a,filename,title,datem,b);
                                String uploadid =myref.push().getKey();
                                myref.child("imagen").push().setValue(datarecycle);



                                tref.child(uploadid).setValue(datarecycle);



                                Toast.makeText(posthomtwo.this,"upload successful",Toast.LENGTH_SHORT).show();
                                notification();
                                progressDialog.setVisibility(View.INVISIBLE);
                                dekho.setVisibility(View.GONE);
                                mrecyclerview.setVisibility(View.VISIBLE);
                                startActivity(new Intent(posthomtwo.this,imagesdistwo.class));

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        //   Toast.makeText(posthome.this,"upload failed"+task.getException(),Toast.LENGTH_SHORT).show();

                    }
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(posthomtwo.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    mrecyclerview.setVisibility(View.VISIBLE);
                }
            });
        }
        else{


            Toast.makeText(posthomtwo.this,"No file selected",Toast.LENGTH_SHORT).show();
            mrecyclerview.setVisibility(View.VISIBLE);
        }
    }
    private  String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void showupload(View view) {
        Intent intent = new Intent(getApplicationContext(),imagedis.class);
        startActivity(intent);
    }

    public void pdfupload(View view) {



    }








    private void browseDocuments(){

        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType+ "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));

        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), pdf_request);

    }

    private  void notification()
    {

        final HashMap<String,String> chatnotification = new HashMap<>();
        chatnotification.put("from",senderid);
        chatnotification.put("type","request");

        callref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post: dataSnapshot.getChildren()){
                    String m;
                    m=post.getKey();

                    callref.child(m).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Map<String,String> map = (Map<String,String>) dataSnapshot.getValue();

                            String j= map.get("uid");
                            if(j!=null)
                                notifref.child(j).push().setValue(chatnotification);

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




    private void sendTakePictureIntent() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        cameraIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, camerarequest);

            File pictureFile = null;
            try {
                pictureFile = getPictureFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.aries.www.fgh.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, camerarequest);

            }
        }
    }
    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "ARIES" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpeg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }

    public void posttwo(View view) {

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.blop);
        mp.start();
        EditText editText = findViewById(R.id.noticetextwo);
        String post = editText.getText().toString().trim();
        if (checkm != null) {
            if (checkm == 1) {
                uploadimage(imageuri, post);
            } else if (checkm == 2)
                uploadimagetwo(camerauri, post);
        }
        else {



            String user = firebaseUser.getUid();
            Date date = new Date();

            String datem = SimpleDateFormat.getDateTimeInstance().format(date);
            text obj = new text(post, datem);

            texref.push().setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    notification();
                    Toast.makeText(posthomtwo.this, "uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    private void launchCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        file = new File(Environment.getExternalStorageDirectory(),
                String.valueOf(System.currentTimeMillis()) + ".jpg");
        uri =  FileProvider.getUriForFile(this,
                "com.aries.www.fgh.fileprovider",
                file);


        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(intent, camerarequest);
    }
    @Override
    public void onBackPressed() {
startActivity(getIntent());
    }

    @Override
    public boolean onSupportNavigateUp() {
     posthomtwo.this. finish();
Intent intentk= new Intent(posthomtwo.this,Hokme.class);
intentk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
startActivity(intentk);
        return true;
    }

}










