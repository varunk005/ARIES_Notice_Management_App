package com.aries.www.fgh;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Environment.DIRECTORY_DCIM;

public class vmadapter extends RecyclerView.Adapter<vmadapter.Imageviewholder> {

    private Context mcontext;
    private List<vmdata> mrecylerdata;
    private OnItemClickListener mlistener;
    private boolean krke = false;


    public vmadapter(Context context, List<vmdata> uploads) {
        mcontext = context;
        mrecylerdata = uploads;
    }

    @NonNull
    @Override
    public Imageviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.disimagetwo, viewGroup, false);
        return new Imageviewholder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull final Imageviewholder imageviewholder, int i) {

        final vmdata dr = mrecylerdata.get(i);
        //  imageviewholder.noticedetail.setText(dr.getHeading());
        Glide.with(mcontext).asBitmap()
                .load(dr.getImageurl())

                .fitCenter()
                .centerCrop()

                //  .placeholder(R.drawable.def)
                // .error(R.drawable.error)
                .into(imageviewholder.noticeimage);

        Glide.with(mcontext).asBitmap()

                .load(dr.getCircleimg())
                .fitCenter()
                .centerCrop()
                .into(imageviewholder.crc);


        imageviewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(mcontext,largeimage.class);
                intent.putExtra("url",dr.getImageurl());

                mcontext.startActivity(intent);

            */


                File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM) + "/" + dr.getImagename());
                Log.d("path", file.toString());
                if (file.exists()) {
                    Uri uri = FileProvider.getUriForFile(mcontext, mcontext.getPackageName() + ".fileprovider", file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    //isme else lagana hai

                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setDataAndType(uri, "image/*");

                    mcontext.startActivity(intent);
                } else {
                    String state = Environment.getExternalStorageState();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (mcontext.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED)) {
                            if (Environment.MEDIA_MOUNTED.equals(state)) {
                                downloadfile(imageviewholder.noticeimage.getContext(), dr.getImagename(), dr.getImagename(), dr.getImageurl());
                            }
                        } else
                            ActivityCompat.requestPermissions((Activity) mcontext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    }

                }


            }


        });

        imageviewholder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference("imagepost").child(dr.getMkey());
                dref.removeValue();
            }
        });

if(krke){
    imageviewholder.progressBar.setVisibility(View.INVISIBLE);
}

        imageviewholder.imtit.setText(dr.getTitle());
        imageviewholder.imdate.setText(dr.getDate());
        imageviewholder.imname.setText(dr.getSendername());
    }


    @Override
    public int getItemCount() {
        return mrecylerdata.size();
    }

    public class Imageviewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public ImageView noticeimage;
        public CircleImageView crc;
        CardView cardView;
        Button imageButton;
        ProgressBar progressBar;
        TextView imname, imdate, imtit;

        public Imageviewholder(@NonNull View itemView) {
            super(itemView);

            noticeimage = itemView.findViewById(R.id.poimage);
            crc = itemView.findViewById(R.id.pocircularimage);
            cardView = itemView.findViewById(R.id.cardimage);
            imageButton = itemView.findViewById(R.id.deletepost);
            imname = itemView.findViewById(R.id.imgnametwo);
            imdate = itemView.findViewById(R.id.imgdatetwo);
            imtit = itemView.findViewById(R.id.imgtitletwo);
            progressBar = itemView.findViewById(R.id.posthomeprogress);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mlistener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mlistener.onDeleteClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem menuItem = menu.add(Menu.NONE, 1, 1, "Delete");
            menuItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if (mlistener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    item.getItemId();
                    mlistener.onDeleteClick(position);
                    return true;
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {

        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public void downloadfile(Context context, String filename, String destinationdirectory, String url) {


        DownloadManager downloadManager =
                (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(DIRECTORY_DCIM, destinationdirectory);
        downloadManager.enqueue(request);

    }
}
