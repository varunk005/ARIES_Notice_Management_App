package com.aries.www.fgh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class pview  extends RecyclerView.ViewHolder {

    TextView mname, mlink,pdate;
    ImageButton mdown ,mshow;
     Context cntx;
   ConstraintLayout constraintLayout;


    public pview(@NonNull View itemView) {
        super(itemView);


        mname = itemView.findViewById(R.id.pdname);
        mlink = itemView.findViewById(R.id.link);
        //mdown = itemView.findViewById(R.id.download);
     constraintLayout = itemView.findViewById(R.id.download);
       mshow  =itemView.findViewById(R.id.show);
       cntx= itemView.getContext();
  pdate= itemView.findViewById(R.id.pddate);



    }
}