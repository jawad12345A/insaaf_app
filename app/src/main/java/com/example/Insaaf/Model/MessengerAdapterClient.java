package com.example.Insaaf.Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Insaaf.R;
import com.example.Insaaf.util.SharedPreferenceHelper;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;


public class MessengerAdapterClient extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<Messenger> list = new ArrayList<>();

    int EXTRA_MY_MESSAGE = 0;
    int EXTRA_OTHER_MESSAGE = 1;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {



        if (EXTRA_MY_MESSAGE == i){

            return new MessengerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_message,
                    viewGroup, false));
        } else {
            return new OtherMessageViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_other_message,
                    viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder messengerViewHolder, int i) {


        Messenger messenger = list.get(i);

        if (messengerViewHolder instanceof MessengerViewHolder){

            ((MessengerViewHolder)(messengerViewHolder)).message.setText(messenger.message);
        } else {
            ((OtherMessageViewHolder)(messengerViewHolder)).message.setText(messenger.message);

        }

    }


    @Override
    public int getItemViewType(int position) {

        Messenger messenger = list.get(position);


        if (messenger.sender.equalsIgnoreCase(SharedPreferenceHelper.INSTANCE.getId())){
            return EXTRA_MY_MESSAGE;
        } else {
            return EXTRA_OTHER_MESSAGE;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Messenger> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MessengerViewHolder extends RecyclerView.ViewHolder{

        TextView message;

        public MessengerViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.item_my_message_txt);
        }
    }



    public class OtherMessageViewHolder extends RecyclerView.ViewHolder{
        TextView message;

        public OtherMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.item_other_message_txt);
        }
    }


    public static class Messenger {
        String message;
        Timestamp time;
        String sender;

        public String getReceipt() {
            return receipt;
        }

        public void setReceipt(String receipt) {
            this.receipt = receipt;
        }

        String receipt;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }


    }
}
