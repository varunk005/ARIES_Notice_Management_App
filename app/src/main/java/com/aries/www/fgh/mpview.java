package com.aries.www.fgh;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class mpview extends RecyclerView.ViewHolder {

    TextView mname,mlink,nwname,nwdesg,pddate;

    ImageButton mdelete ,mshow;

    ConstraintLayout constraintLayout;
    public mpview(@NonNull View itemView) {
        super(itemView);


     mname=   itemView.findViewById(R.id.popdname);
        mlink=itemView.findViewById(R.id.popdlink);
        mdelete=itemView.findViewById(R.id.deletetask);
        constraintLayout = itemView.findViewById(R.id.download);
        mshow  =itemView.findViewById(R.id.popdimage);

pddate =itemView.findViewById(R.id.popddate);






    }
}
