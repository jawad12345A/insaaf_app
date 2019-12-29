package com.example.Insaaf.Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Insaaf.R;
import com.example.Insaaf.util.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CancelAppointmentsClientAdapter extends RecyclerView.Adapter<CancelAppointmentsClientAdapter.MyViewHolder>{

    List<Appointment> list;
    private ListenerRegistration listener;
    private ListenerRegistration queryListener;

    public CancelAppointmentsClientAdapter() {
        this.list = new ArrayList<>();
    }

    public void setList(List<Appointment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_client_cancel_appointment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        Appointment appointment = list.get(i);

        myViewHolder.name.setText(appointment.lawyerName);
        myViewHolder.phoneNumber.setText(appointment.lawyerPhoneNumber);
        getImageUrl(myViewHolder, appointment.lawyerPhoneNumber);


        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Helper.showProgressDialog(myViewHolder.delete.getContext());
                 queryListener  = FirebaseFirestore.getInstance().collection("appointments")
                        .whereEqualTo("lawyerName", appointment.lawyerName)
                        .whereEqualTo("lawyerPhoneNumber", appointment.lawyerPhoneNumber)
                        .whereEqualTo("clientName", appointment.clientName)
                        .whereEqualTo("clientPhoneNumber", appointment.clientPhoneNumber)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                                queryListener.remove();

                                if (e != null)
                                    return;
                                String docId = queryDocumentSnapshots.getDocuments().get(0).getId();
                                FirebaseFirestore.getInstance().collection("appointments")
                                        .document(docId).delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Helper.hideProgressDialog();
                                            }
                                        });
                            }
                        });
            }
        });


    }

    private void getImageUrl(final MyViewHolder myViewHolder, String lawyerPhoneNumber) {
        listener = FirebaseFirestore.getInstance().collection("lawyers")
                .whereEqualTo("contact_no", lawyerPhoneNumber)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    listener.remove();

                    if (e != null) return;

                    AttorneyData attorneyData = queryDocumentSnapshots.getDocuments().get(0).toObject(AttorneyData.class);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.placeholder);
                    Glide.with(myViewHolder.imageView)
                            .setDefaultRequestOptions(requestOptions)
                            .load(attorneyData.getPhoto_url())
                            .into(myViewHolder.imageView);
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView, delete;
        TextView name, phoneNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_client_cancel_appointment_img);
            name = itemView.findViewById(R.id.item_client_cancel_appointment_name);
            phoneNumber = itemView.findViewById(R.id.item_client_cancel_appointment_phonenumber);
            delete = itemView.findViewById(R.id.item_client_cancel_appointment_delete);
        }
    }
}
