package com.example.Insaaf.Presenter.lawyer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.Insaaf.Model.AttorneyData;
import com.example.Insaaf.R;
import com.example.Insaaf.util.SharedPreferenceHelper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LawyerLoginActivity extends AppCompatActivity {




    @BindView(R.id.lawyer_email)
    AutoCompleteTextView email;

    @BindView(R.id.lawyer_login_password)
    AutoCompleteTextView password;
    private ListenerRegistration listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer_activity_login);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.lawyer_login_signin)
    public void signIn(){

        listener = FirebaseFirestore.getInstance().collection("lawyers")
                .whereEqualTo("email", email.getText().toString())
                .whereEqualTo("password", password.getText().toString())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {



                    if (e != null) {
                        Toast.makeText(LawyerLoginActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (queryDocumentSnapshots.getDocuments().size() == 0 ){
                        Toast.makeText(LawyerLoginActivity.this, "User doesnot exists", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (queryDocumentSnapshots.getDocuments().size() > 0 ){
                        AttorneyData attorneyData = queryDocumentSnapshots.getDocuments().get(0).toObject(AttorneyData.class);
                        SharedPreferenceHelper.INSTANCE.saveContactNumber(attorneyData.getContact_no());
                        SharedPreferenceHelper.INSTANCE.saveName(attorneyData.getName());
                        SharedPreferenceHelper.INSTANCE.saveId(queryDocumentSnapshots.getDocuments().get(0).getId());
                        SharedPreferenceHelper.INSTANCE.saveImageUrl(attorneyData.getPhoto_url());
                        dispatchDashboardIntent();
                        Toast.makeText(LawyerLoginActivity.this, "User exists", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    listener.remove();

                });
    }

    private void dispatchDashboardIntent() {
        Intent dashboardIntent = new Intent(this, LawyerDashboard.class);
        startActivity(dashboardIntent);
    }

    @OnClick(R.id.lawyer_login_signup)
    public void signUp(){

        startActivity(new Intent(this, RegistrationLawyer.class));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null){
            listener.remove();
        }
    }
}
