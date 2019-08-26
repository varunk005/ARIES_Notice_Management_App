package com.aries.www.fgh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class usertextadap extends RecyclerView.Adapter<usertextadap.utexview>{
    Context mcontext;
    ArrayList<text> model;

    public usertextadap(Context mcontext, ArrayList<text> model) {
        this.mcontext = mcontext;
        this.model = model;
    }

    @NonNull
    @Override
    public utexview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mcontext).inflate(R.layout.textlay,viewGroup,false);
        return  new usertextadap.utexview(v);
    }

    @Override
    public void onBindViewHolder(@NonNull utexview utexview, int i) {
        utexview.textView.setText(model.get(i).getText());
        utexview.textViewdate.setText(model.get(i).getTexdate());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class utexview  extends RecyclerView.ViewHolder{

        private TextView textView,textViewdate;
        private CardView cardView;

        public utexview(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.potext);
            textViewdate= itemView.findViewById(R.id.potextdate);
            cardView=itemView.findViewById(R.id.cardtext);
        }
    }
}
