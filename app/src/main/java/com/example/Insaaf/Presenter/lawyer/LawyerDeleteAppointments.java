package com.example.Insaaf.Presenter.lawyer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.Insaaf.Model.Appointment;
import com.example.Insaaf.Model.LawyerDeleteAppointmentAdapter;
import com.example.Insaaf.R;
import com.example.Insaaf.util.MyItemDecoration;
import com.example.Insaaf.util.SharedPreferenceHelper;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LawyerDeleteAppointments extends AppCompatActivity {


    @BindView(R.id.clients_appointments_tab_bar)
    Toolbar toolbar;


    @BindView(R.id.appointments_recyclerview)
    RecyclerView recyclerView;
    private ListenerRegistration listener;
    private LawyerDeleteAppointmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_delete_appointments);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        setUpRecyclerView();
        getData();

    }

    private void getData() {

        listener = FirebaseFirestore.getInstance().collection("appointments")
                .whereEqualTo("lawyerPhoneNumber", SharedPreferenceHelper.INSTANCE.getContactNumber())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        List<Appointment> appointmentList = new ArrayList();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            Appointment appointment = documentSnapshot.toObject(Appointment.class);
                            appointmentList.add(appointment);
                        }

                        adapter.setList(appointmentList);


                    }
                });
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new MyItemDecoration());
        adapter = new LawyerDeleteAppointmentAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null ) listener.remove();
    }
}