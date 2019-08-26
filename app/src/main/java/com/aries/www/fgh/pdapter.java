package com.aries.www.fgh;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class pdapter extends RecyclerView.Adapter<pview>  {

   private Context mcontext;
  private   ArrayList<dpd> model;
String a;

    public pdapter(Context mcontext, ArrayList<dpd> model) {
        this.mcontext = mcontext;
        this.model = model;
    }

    @NonNull
    @Override
    public pview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mcontext).inflate(R.layout.pdlayout, viewGroup, false);
        return new pview(v);



    }

    @Override
    public void onBindViewHolder(@NonNull final pview pview, final int i) {
        pview.mname.setText(model.get(i).getName());
        //  pview.mlink.setText(model.get(i).getLink());
        pview.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r = pview.getAdapterPosition();
                Uri uri = Uri.parse(model.get(r).getLink());
                String state = Environment.getExternalStorageState();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mcontext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED)) {
                        if (Environment.MEDIA_MOUNTED.equals(state)) {

                            File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/" + model.get(r).getName());
                            if (file.exists()) {


                                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                        StrictMode.setVmPolicy(builder.build());

                                     //   File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)+"/"+model.get(pview.getAdapterPosition()).getName());

                                        Uri urim = FileProvider.getUriForFile(mcontext,mcontext.getPackageName()+".fileprovider",file);
                                    //    Log.d("path", uri.toString());
                                        Intent intent = new Intent(Intent.ACTION_VIEW);



                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.setDataAndType(urim, "*/*");


                                        mcontext.startActivity(intent);
                                    }

                                    else{

                                downloadfile(pview.mname.getContext(), model.get(r).getName(), model.get(r).getName(), getFileExtension(uri), model.get(r).getLink());
                                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                StrictMode.setVmPolicy(builder.build());

                                //   File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)+"/"+model.get(pview.getAdapterPosition()).getName());

                                Uri urim = FileProvider.getUriForFile(mcontext,mcontext.getPackageName()+".fileprovider",file);
                                Log.d("path", uri.toString());
                                Intent intent = new Intent(Intent.ACTION_VIEW);



                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setDataAndType(urim, "*/*");


                                mcontext.startActivity(intent);
                            }





                            }



                        }
                    } else
                        ActivityCompat.requestPermissions((Activity) mcontext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);


                }

        });

        if (model.get(i).getName().contains(".pdf"))
            pview.mshow.setImageResource(R.drawable.pdf3);

        else if (model.get(i).getName().contains(".xlsx") || model.get(i).getName().contains(".xls"))
            pview.mshow.setImageResource(R.drawable.ex);
        else if (model.get(i).getName().contains(".ppt") || model.get(i).getName().contains(".pptx"))
            pview.mshow.setImageResource(R.drawable.ppt);
        else if (model.get(i).getName().contains(".doc") || model.get(i).getName().contains(".docx"))
            pview.mshow.setImageResource(R.drawable.wrd);
        else if (model.get(i).getName().contains(".zip"))
            pview.mshow.setImageResource(R.drawable.zp);
        else if (model.get(i).getName().contains(".txt"))
            pview.mshow.setImageResource(R.drawable.txt);
        else
            pview.mshow.setImageResource(R.drawable.fl);

        pview.pdate.setText(model.get(i).getPddate());
    }
    public void downloadfile(Context context, String filename, String destinationdirectory, String fileextension, String url) {
        DownloadManager downloadManager =
                //(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        /*String jk = filename+fileextension;
        int r = jk.indexOf('.');
        int k =r+4;
        String yk = filename+jk.substring(r,k);
*/
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

      //  request.setDestinationInExternalFilesDir(context, destinationdirectory, filename);
request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS,destinationdirectory);
        downloadManager.enqueue(request);
    }


    @Override
    public int getItemCount() {
        return model.size();
    }


    private  String getFileExtension(Uri uri){
        ContentResolver cr =mcontext.getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        String a=  mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
        return a;

}


}
