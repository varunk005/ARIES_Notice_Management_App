package com.aries.www.fgh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class textadap extends RecyclerView.Adapter<textadap.texview>   {

    Context mcontext;
    ArrayList<text> model;

    public textadap(Context mcontext, ArrayList<text> model) {
        this.mcontext = mcontext;
        this.model = model;
    }

    @NonNull
    @Override
    public texview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mcontext).inflate(R.layout.textlay,viewGroup,false);
        return  new texview(v);

    }

    @Override
    public void onBindViewHolder(@NonNull texview texview,  final int i) {

        texview.textView.setText(model.get(i).getText());
        texview.textViewdate.setText(model.get(i).getTexdate());

        texview.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference("textupload").child(model.get(i).getMkey());
                dref.removeValue();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
       return model.size();
    }

    public class texview  extends RecyclerView.ViewHolder{

        private TextView textView,textViewdate;
        private CardView cardView;

        public texview(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.potext);
            textViewdate= itemView.findViewById(R.id.potextdate);
            cardView=itemView.findViewById(R.id.cardtext);
        }
    }

}
