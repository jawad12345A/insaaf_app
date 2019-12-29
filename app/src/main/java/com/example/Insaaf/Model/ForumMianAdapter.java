package com.example.Insaaf.Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.Insaaf.Presenter.clients.ForumActivity;
import com.example.Insaaf.R;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ForumMianAdapter extends RecyclerView.Adapter<ForumMianAdapter.ForumViewHolder>
 implements Filterable {



    List<ForumModel> forumModelList = new ArrayList<>();

    List<ForumModel> filteredList = new ArrayList<>();

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ForumViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_forum_main, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder forumViewHolder, int i) {
        forumViewHolder.setData(filteredList.get(i));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void setList(List<ForumModel> forumModelList) {
        this.forumModelList = forumModelList;
        filteredList = forumModelList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }



    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ForumModel> tempList = new ArrayList<>();


            if (constraint == null || constraint.length() == 0){

                tempList = forumModelList;

            } else{

                for(ForumModel forumModel: forumModelList){
                    if (forumModel.getTitle().toLowerCase().trim().contains(constraint.toString().toLowerCase())){
                        tempList.add(forumModel);
                    }
                }
            }



            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<ForumModel>) results.values;
            notifyDataSetChanged();

        }
    };

    public class ForumViewHolder extends RecyclerView.ViewHolder{

        ImageView user_image;
        TextView title, time;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);

            user_image = itemView.findViewById(R.id.item_forum_main_image);
            title = itemView.findViewById(R.id.item_forum_main_title);
            time = itemView.findViewById(R.id.item_forum_main_time);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ForumModel forumModel = filteredList.get(getAdapterPosition());
                    ForumActivity.dispatchIntent(itemView.getContext(), forumModel.id, forumModel.getTitle(), forumModel.description);

                }
            });
        }


        public void setData(ForumModel forumModel){


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


            String dateString  = dateFormat.format(forumModel.postDate.toDate());



            title.setText(forumModel.title);
            time.setText(dateString);

            Glide.with(user_image)
                    .load(forumModel.url)
                    .into(user_image);
        }
    }


    public static class ForumModel{
        String id;
        String url;
        String title;
        String description;
        Timestamp postDate;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Timestamp getPostDate() {
            return postDate;
        }

        public void setPostDate(Timestamp postDate) {
            this.postDate = postDate;
        }
    }

}





