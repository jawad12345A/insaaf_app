package com.example.Insaaf.Presenter.clients;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Insaaf.Model.Appointment;
import com.example.Insaaf.Model.AttorneyData;
import com.example.Insaaf.R;
import com.example.Insaaf.util.Helper;
import com.example.Insaaf.util.SharedPreferenceHelper;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AttorneyDetails extends AppCompatActivity {

    private ListenerRegistration listener;



    @BindView(R.id.attorney_detail_bio)
    TextView bio;

    @BindView(R.id.attorney_details_sms)
    TextView sms;

    @BindView(R.id.attorney_detail_phone_number)
    TextView call;


    @BindView(R.id.attorney_details_name)
    TextView name;


    @BindView(R.id.attorney_detail_image)
    ImageView image;

    @BindView(R.id.attorney_detail_book)
    Button book;
    private AttorneyData attorneyData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attorney_details);
        ButterKnife.bind(this);



        getData();
    }

    private void getData() {

        Helper.showProgressDialog(this);
        listener = FirebaseFirestore.getInstance().collection("lawyers")
                .whereEqualTo("bc_id", getIntent().getStringExtra("attorney"))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e!= null) {
                            Helper.hideProgressDialog();
                            finish();
                            return;
                        }


                        Helper.hideProgressDialog();
                        attorneyData = queryDocumentSnapshots.getDocuments().get(0).toObject(AttorneyData.class);
                        bindData(attorneyData);

                    }
                });
    }

    private void bindData(AttorneyData attorneyData) {
        Glide.with(this)
                .load(attorneyData.getPhoto_url())
                .into(image);
        name.setText(attorneyData.getName());
        sms.setText(attorneyData.getContact_no());
        call.setText(attorneyData.getContact_no());
        bio.setText(attorneyData.getBio());

    }

    @OnClick(R.id.attorney_detail_book)
    public void bookAppointment(){
        Appointment appointment = new Appointment(SharedPreferenceHelper.INSTANCE.getName(),
                attorneyData.getName(),
                SharedPreferenceHelper.INSTANCE.getContactNumber(),
                attorneyData.getContact_no()
                );

        FirebaseFirestore.getInstance().collection("appointments")
                .add(appointment)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AttorneyDetails.this, "Appointment Made", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AttorneyDetails.this, "Appointment Could'nt made", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null )listener.remove();

    }



    @OnClick(R.id.attorney_detail_phone_number)
    public void setCall(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+attorneyData.getContact_no()));
        startActivity(intent);
    }



    @OnClick(R.id.attorney_details_sms)
    public void setSms(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:"+attorneyData.getContact_no()));
        startActivity(intent);

    }
}
