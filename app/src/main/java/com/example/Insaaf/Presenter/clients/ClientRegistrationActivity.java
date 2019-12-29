
package com.example.Insaaf.Presenter.clients;


import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Insaaf.Model.ClientData;
import com.example.Insaaf.R;
import com.example.Insaaf.util.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.rpc.Help;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientRegistrationActivity extends AppCompatActivity {


    private static final int PICK_IMAGE = 125;
    @BindView(R.id.client_registration_contact)
    AutoCompleteTextView contact;


    @BindView(R.id.client_registration_email)
    AutoCompleteTextView email;


    @BindView(R.id.client_registration_name)
    AutoCompleteTextView name;


    @BindView(R.id.client_registration_password)
    AutoCompleteTextView password;



    @BindView(R.id.btnSignUp)
    Button signUp;


    @BindView(R.id.tvSignIn)
    TextView signIn;
    private ListenerRegistration listener;




    @BindView(R.id.ivRegLogo)
    ImageView imageView;



    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_client);
        ButterKnife.bind(this);
        
    }



    @OnClick(R.id.ivRegLogo)
    public void selectImage(){


        Intent pickImage = new Intent(Intent.ACTION_PICK);
        pickImage.setType("image/*");
        startActivityForResult(pickImage, PICK_IMAGE);
    }







    @OnClick(R.id.btnSignUp)
    public void saveUser(){

        if (!validate()){
            return;
        }

        Helper.showProgressDialog(this);
       listener =  FirebaseFirestore.getInstance().collection("client")
                .whereEqualTo("email", email.getText().toString() )
               .addSnapshotListener((queryDocumentSnapshots, e) -> {
                   if (queryDocumentSnapshots.getDocuments().size() == 0 ){

                       saveImageInStorage();
                       listener.remove();
                   } else{
                       Helper.hideProgressDialog();
                       listener.remove();
                       Toast.makeText(ClientRegistrationActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                   }
               });
    }


    private void saveImageInStorage() {

        StorageReference imagesReference = FirebaseStorage.getInstance().getReference("images/"+System.currentTimeMillis()+".jpg");


        imagesReference.putFile(imageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        imagesReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                clearToSaveData(task.getResult());
                            }
                        });
                    }
                });
    }

    private boolean validate() {

        if (email.getText().toString().isEmpty()){
            email.setError("Empty");
            return false;
        }

        if (password.getText().toString().isEmpty()){
            password.setError("Empty");
            return false;
        }

        if (contact.getText().toString().isEmpty()){
            contact.setError("Empty");
            return false;
        }

        if (imageUri == null){
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void clearToSaveData(Uri result) {
        ClientData clientData = new ClientData(name.getText().toString(),
                email.getText().toString(),
                contact.getText().toString(),
                password.getText().toString(),
                ""+result);


        FirebaseFirestore.getInstance().collection("client")
                .add(clientData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Helper.hideProgressDialog();
                        Toast.makeText(ClientRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        signIn();
                        return;
                    } else{
                        Helper.hideProgressDialog();
                        Toast.makeText(ClientRegistrationActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                        return;
                    }

                });
    }


    @OnClick(R.id.tvSignIn)
    public void signIn(){
        
        startActivity(new Intent(this, ClientLogin.class));
        finish();
        
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @android.support.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE){
            if (resultCode == RESULT_OK){
                imageUri = data.getData();
                try {
                    imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null) listener.remove();
    }
}
