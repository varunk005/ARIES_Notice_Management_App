package com.aries.www.fgh;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class newuseradapter extends RecyclerView.Adapter<newuseradapter.newuserview>  {

    Context mcontext;
    ArrayList<nudata> dmodel;
   private FirebaseAuth mauth,pauth;
   private String a,b,c,d;

    public newuseradapter(Context mcontext, ArrayList<nudata> dmodel) {
        this.mcontext = mcontext;
        this.dmodel = dmodel;
    }

    @NonNull
    @Override
    public newuserview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mcontext).inflate(R.layout.pub,viewGroup,false);
        return  new newuserview(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final newuserview newuserview,final int i) {
        newuserview.kk.setText(dmodel.get(i).getName());
        newuserview.d.setText(dmodel.get(i).getDesg());
        newuserview.rm.setText(dmodel.get(i).getMobn());
        newuserview.em.setText(dmodel.get(i).getEmail());
newuserview.al.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(final View v) {
        final MediaPlayer mp = MediaPlayer.create(mcontext, R.raw.blop);
        mp.start();


      mauth= FirebaseAuth.getInstance();
      pauth=FirebaseAuth.getInstance();
        DatabaseReference yref = FirebaseDatabase.getInstance().getReference("newuser").child(dmodel.get(i).getMkey());
          final  DatabaseReference kref =FirebaseDatabase.getInstance().getReference("registeredusers");
          final DatabaseReference regref = FirebaseDatabase.getInstance().getReference("allusers");
        yref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();

                a = map.get("email");
                b = map.get("pass");
                c = map.get("name");
                d = map.get("devicetoken");


                    mauth.createUserWithEmailAndPassword(a, b).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            String user = task.getResult().getUser().getUid();
                            kref.child(user).child("name").setValue(c);
                            kref.child(user).child("email").setValue(a);
                            kref.child(user).child("uid").setValue(user);

                            regref.child(user).child("devicetoken").setValue(d);
                            regref.child(user).child("name").setValue(c);
                            regref.child(user).child("email").setValue(a);
                            regref.child(user).child("uid").setValue(user);


                            Toast.makeText(v.getContext(), "registered", Toast.LENGTH_SHORT).show();

                            newuserview.dne.setVisibility(View.VISIBLE);

                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + a));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Aries App Registration");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Your account has been created " +
                                    "Regards Team Aries :)");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                            mcontext.startActivity(Intent.createChooser(emailIntent, "Choose App"));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
});
newuserview.cn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final MediaPlayer mp = MediaPlayer.create(mcontext, R.raw.blop);
        mp.start();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("newuser").child(dmodel.get(i).getMkey());
        databaseReference.removeValue();

    }
});
    }

    @Override
    public int getItemCount() {
        return  dmodel.size();
    }
   public class newuserview extends RecyclerView.ViewHolder {

        TextView kk,d,rm,em,dne;
        Button al,cn;

        public newuserview(@NonNull View itemView) {
            super(itemView);

            kk= itemView.findViewById(R.id.newname);
            d= itemView.findViewById(R.id.newdesg);
            rm= itemView.findViewById(R.id.newmob);
            em= itemView.findViewById(R.id.newemail);

            al=itemView.findViewById(R.id.userselect);
            cn= itemView.findViewById(R.id.userreject);
            dne = itemView.findViewById(R.id.done);
        }
    }

}
