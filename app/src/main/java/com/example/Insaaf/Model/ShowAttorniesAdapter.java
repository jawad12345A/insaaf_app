package com.example.Insaaf.Model;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Insaaf.R;
import com.example.Insaaf.Presenter.clients.AttorneyDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowAttorniesAdapter extends RecyclerView.Adapter<ShowAttorniesAdapter.MyViewHolder> implements Filterable {



    List<AttorneyData> list;
    List<AttorneyData> duplicateList;

    public ShowAttorniesAdapter() {
        this.list = new ArrayList<>();
    }


    public void setList(List<AttorneyData> list) {
        this.list = list;
        duplicateList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_show_attornyes,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        AttorneyData data = list.get(i);

        myViewHolder.name.setText(data.name);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);

        Glide.with(myViewHolder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(data.photo_url).into(myViewHolder.attorneyImage);


        float rating = Float.parseFloat(data.getTotalCaseWin()) / Float.parseFloat(data.getTotalCaseFought()) * 5 ;

        myViewHolder.ratingBar.setRating(rating);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                switch (constraint.toString()){
                    case "High court" :
                    case "Supreme Court":
                        return createListOfCourt(constraint.toString());
                    case "Criminal Service":
                        return  createServiceList("Criminal");
                    case "Civil Service":
                        return createServiceList("Civil");
                    case "High Rating":
                        return createRatingList(false);
                    case "Low Rating":
                        return createRatingList(true);
                    case "None":
                        FilterResults results =  new FilterResults();
                        results.values = duplicateList;
                        return results;

                }



                return null;
            }



            private FilterResults createRatingList(boolean b) {


                List newFilterList = new ArrayList(duplicateList);

                Collections.sort(newFilterList, (Comparator<AttorneyData>) (o1, o2) -> {
                    float rating1 = Float.parseFloat(o1.getTotalCaseWin()) / Float.parseFloat(o1.getTotalCaseFought()) * 5;
                    float rating2 = Float.parseFloat(o2.getTotalCaseWin()) / Float.parseFloat(o2.getTotalCaseFought()) * 5;

                    return Float.compare(rating1, rating2);
                });
                if (!b){
                    Collections.reverse(newFilterList);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = newFilterList;
                return filterResults;
            }
            private FilterResults createServiceList(String filter) {
                List filterList = new ArrayList();
                for ( AttorneyData attorneyData : duplicateList){
                    if (attorneyData.services.equalsIgnoreCase(filter)){
                        filterList.add(attorneyData);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            private FilterResults createListOfCourt(String court) {
                List filterList = new ArrayList();
                for ( AttorneyData attorneyData : duplicateList){
                    if (attorneyData.courts.equalsIgnoreCase(court)){
                        filterList.add(attorneyData);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<AttorneyData>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView attorneyImage;
        TextView name;
        RatingBar ratingBar;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            attorneyImage = itemView.findViewById(R.id.item_show_attornyes_img);
            name = itemView.findViewById(R.id.item_show_attornyes_name);
            ratingBar = itemView.findViewById(R.id.show_attornies_rating_bar);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(attorneyImage.getContext(), AttorneyDetails.class);
                    intent.putExtra("attorney", list.get(getAdapterPosition()).getBc_id());
                    ((Activity)attorneyImage.getContext()).startActivity(intent);
                }
            });
        }
    }
}
