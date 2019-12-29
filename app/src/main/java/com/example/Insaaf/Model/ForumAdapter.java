package com.example.Insaaf.Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.Insaaf.R;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.MyMessageForumViewHolder>{

    List<ForumComment> list = new ArrayList<>();




    @NonNull
    @Override
    public MyMessageForumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MyMessageForumViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_forum_my_comment, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyMessageForumViewHolder viewHolder, int i) {
        viewHolder.setData(list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ForumComment> forumCommentList) {
        list = forumCommentList;
        notifyDataSetChanged();
    }

    public class MyMessageForumViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        ImageView userImage;
        TextView name;
        TextView comment;

        public MyMessageForumViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.forum_comment_time);
            userImage = itemView.findViewById(R.id.forum_comment_image);
            name = itemView.findViewById(R.id.forum_comment_name);
            comment = itemView.findViewById(R.id.forum_comment_text);
        }



        public void setData(ForumComment forumComment){
            name.setText(forumComment.userName);
            comment.setText(forumComment.comment);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


            String dateString  = dateFormat.format(forumComment.date.toDate());
            date.setText(dateString);


            Glide.with(userImage)
                    .load(forumComment.userImage)
                    .into(userImage);

        }
    }


     public static class ForumComment {
        String forumId;
        String userId;
        String userImage;
        String userName;
        String comment;
        Timestamp date;

        public String getForumId() {
            return forumId;
        }

        public void setForumId(String forumId) {
            this.forumId = forumId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }
    }
}
