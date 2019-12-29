package com.example.Insaaf.Presenter.clients;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.Insaaf.Model.ClientData;
import com.example.Insaaf.R;
import com.example.Insaaf.util.SharedPreferenceHelper;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientLogin extends AppCompatActivity {





    @BindView(R.id.atvEmailLog)
    AutoCompleteTextView email;


    @BindView(R.id.atvPasswordLog)
    AutoCompleteTextView password;
    private ListenerRegistration listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);
        ButterKnife.bind(this);
    }

    public void clientSignIn(View view) {

        if (!validateData()){
            return;
        }


       listener=  FirebaseFirestore.getInstance().collection("client")
                .whereEqualTo("email", email.getText().toString())
                .whereEqualTo("password", password.getText().toString())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {



                        if (e != null){
                            Toast.makeText(ClientLogin.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (queryDocumentSnapshots.getDocuments().size() > 0){



                            ClientData clientData = queryDocumentSnapshots.getDocuments().get(0).toObject(ClientData.class);
                            clientData.setId(queryDocumentSnapshots.getDocuments().get(0).getId());
                            SharedPreferenceHelper.INSTANCE.saveName(clientData.getName());
                            SharedPreferenceHelper.INSTANCE.saveContactNumber(clientData.getContact());
                            SharedPreferenceHelper.INSTANCE.saveId(clientData.getId());
                            SharedPreferenceHelper.INSTANCE.saveImageUrl(clientData.getClientImage());
                            Toast.makeText(ClientLogin.this, "User exists", Toast.LENGTH_LONG).show();
                            dispatchClientDashboard();
                            return;
                        }

                        if (queryDocumentSnapshots.getDocuments().size() == 0) {
                            Toast.makeText(ClientLogin.this, "User Doesn't exists", Toast.LENGTH_LONG).show();
                            return;

                        }


                    }
                });


    }

    private void dispatchClientDashboard() {
        Intent intent = new Intent(this, ClientDashboard.class);
        startActivity(intent);
    }

    private boolean validateData() {

        if (email.getText().toString().isEmpty()){
            email.setError("Empty");
            return false;
        }

        if (password.getText().toString().isEmpty()){
            password.setError("Empty");
            return false;
        }

        return true;
    }

    public void clientSignUp(View view) {
        startActivity(new Intent(this, ClientRegistrationActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null) listener.remove();
    }
}
