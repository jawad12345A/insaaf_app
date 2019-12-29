package com.example.Insaaf.Model;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Insaaf.Presenter.clients.MessengerActivity;
import com.example.Insaaf.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class LawyerAppointmentsAdapter extends RecyclerView.Adapter<LawyerAppointmentsAdapter.MyViewHolder>{


    List<Appointment> list;
    private ListenerRegistration listener;

    public LawyerAppointmentsAdapter() {
        this.list = new ArrayList<>();
    }

    public void setList(List<Appointment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_client_appointment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        Appointment appointment = list.get(i);

        myViewHolder.name.setText(appointment.clientName);
        myViewHolder.phoneNumber.setText(appointment.clientPhoneNumber);
        getImageUrl(myViewHolder, appointment.clientPhoneNumber);


    }

    private void getImageUrl(final MyViewHolder myViewHolder, String lawyerPhoneNumber) {
        listener = FirebaseFirestore.getInstance().collection("client")
                .whereEqualTo("contact", lawyerPhoneNumber)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        listener.remove();

                        if (e != null) return;

                        ClientData clientData = queryDocumentSnapshots.getDocuments().get(0).toObject(ClientData.class);
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.drawable.placeholder);
                        Glide.with(myViewHolder.imageView)
                                .setDefaultRequestOptions(requestOptions)
                                .load(clientData.clientImage)
                                .into(myViewHolder.imageView);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, phoneNumber;
        ImageView sms, messenger, call;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_client_appointment_img);
            name = itemView.findViewById(R.id.item_client_appointment_name);
            phoneNumber = itemView.findViewById(R.id.item_client_appointment_phonenumber);
            sms = itemView.findViewById(R.id.item_client_appointment_sms);
            messenger = itemView.findViewById(R.id.item_client_appointment_messenger);
            call = itemView.findViewById(R.id.item_client_appointment_call);




            sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("smsto:"+phoneNumber.getText().toString());
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", "The SMS text");
                    sms.getContext().startActivity(intent);
                }
            });


            messenger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Appointment appointment = list.get(getAdapterPosition());
                    MessengerActivity.dispatchIntent(messenger.getContext(), appointment.appointmentId, appointment.clientId);
                }
            });


            call.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String phone = phoneNumber.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    call.getContext().startActivity(intent);

                }
            });




        }






    }
}
