package com.aries.www.fgh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.aries.www.fgh.postdata.onetype;
import static com.aries.www.fgh.postdata.thirdtype;
import static com.aries.www.fgh.postdata.twotype;

public class postadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mcontext;
    private ArrayList<postdata> list;

    public postadapter(Context mcontext, ArrayList<postdata> list) {
        this.mcontext = mcontext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case onetype:
                View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.disimage,viewGroup,false);
                return new imageholder(v);
            case twotype:
                View vk=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pdlayouttwo,viewGroup,false);
                return new pdfholder(vk);
            case thirdtype:
                View vkm=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.textlay,viewGroup,false);
                return new textholder(vkm);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        postdata dk = list.get(i);
        switch (dk.getType()){


            case onetype:
                ((imageholder) viewHolder).pititle.setText(dk.getImgtit());
                ((imageholder) viewHolder).piname.setText(dk.getImname());
                ((imageholder) viewHolder).pidate.setText(dk.getImdat());

                Glide.with(mcontext)
                        .load(dk.getIml())
                        .fitCenter()
                        .centerCrop()

                        //  .placeholder(R.drawable.def)
                        // .error(R.drawable.error)
                        .into(((imageholder) viewHolder).pimg);

                Glide.with(mcontext)
                        .load(dk.getImgcirc())
                        .fitCenter()
                        .centerCrop()

                        //  .placeholder(R.drawable.def)
                        // .error(R.drawable.error)
                        .into(((imageholder) viewHolder).pcirc);
                break;
            case twotype:
                ((pdfholder) viewHolder).pddate.setText(dk.getPddate());
                ((pdfholder) viewHolder).pdnm.setText(dk.getPdname());
                ((pdfholder) viewHolder).pdlink.setText(dk.getPdl());
                if(dk.getPdname()!=null)
                if (dk.getPdname().contains(".pdf"))
                    ((pdfholder) viewHolder).pdim.setImageResource(R.drawable.pdf3);
                  break;

            case thirdtype:
                ((textholder) viewHolder).texdate.setText(dk.getTexdate());
                ((textholder) viewHolder).tex.setText(dk.getNortex());


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class imageholder extends RecyclerView.ViewHolder {

     ImageView pimg,pcirc;
     TextView pititle,piname,pidate;
        public imageholder(@NonNull View itemView) {
            super(itemView);
         //   pimg = itemView.findViewById(R.id.poimage);
           // pcirc = itemView.findViewById(R.id.pocircularimage);
           // pititle = itemView.findViewById(R.id.poimagetitle);
            //piname = itemView.findViewById(R.id.poimgname);
           // pidate = itemView.findViewById(R.id.poimgdate);
        }
    }

    public class pdfholder extends RecyclerView.ViewHolder {

        ImageView pdim;
        TextView pdnm,pdlink,pddate;
        public pdfholder(@NonNull View itemView) {
            super(itemView);

            pdim =itemView.findViewById(R.id.popdimage);
            pdnm =itemView.findViewById(R.id.popdname);
            pdlink=itemView.findViewById(R.id.popdlink);
            pddate =itemView.findViewById(R.id.popddate);

        }
    }

    public class textholder extends RecyclerView.ViewHolder {
        TextView tex ,texdate;
        public textholder(@NonNull View itemView) {
            super(itemView);
            tex = itemView.findViewById(R.id.potext);
            texdate =itemView.findViewById(R.id.potextdate);
        }
    }
    @Override
    public int getItemViewType(int position) {
        postdata d = list.get(position);
        if(d!=null){
            return   d.getType();
        }
        return 0;
    }
}
