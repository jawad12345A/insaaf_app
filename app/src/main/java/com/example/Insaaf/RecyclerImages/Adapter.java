package com.example.Insaaf.RecyclerImages;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Insaaf.R;


public class Adapter  extends RecyclerView.Adapter <Adapter.RVviewholder>{

    private String[] data;
    private int[] images;


    public Adapter(String[] data,int[] images){
        this.data=data;
        this.images=images;


    }


    @NonNull
    @Override
    public Adapter.RVviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());

        View view= inflater.inflate(R.layout.list_item,viewGroup,false);
        return new Adapter.RVviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RVviewholder rVviewholder, int i) {

        String tilte=data[i];
        rVviewholder.textView.setText(tilte);
        int images_id=images[i];
        rVviewholder.imgicon.setImageResource(images_id);
    }




    @Override
    public int getItemCount() {
        return data.length;
    }

    public class RVviewholder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imgicon;

        public RVviewholder(@NonNull View itemView) {

            super(itemView);
            textView=itemView.findViewById(R.id.tv);
            imgicon= itemView.findViewById(R.id.img);

        }




    }
}