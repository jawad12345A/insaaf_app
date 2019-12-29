package com.example.Insaaf.Presenter.lawyer;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.Insaaf.Model.AttorneyData;
import com.example.Insaaf.R;
import com.example.Insaaf.util.Helper;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationLawyer extends AppCompatActivity {


    private static final int PICK_IMAGE = 123;
    @BindView(R.id.lawyer_reg_name)
    AutoCompleteTextView name;


    @BindView(R.id.lawyer_reg_email)
    AutoCompleteTextView email;


    @BindView(R.id.lawyer_reg_contact)
    AutoCompleteTextView contact;

    @BindView(R.id.lawyer_reg_bcid)
    AutoCompleteTextView bcid;


    @BindView(R.id.lawyer_reg_city)
    AutoCompleteTextView city;

    @BindView(R.id.lawyer_reg_high)
    RadioButton highCourt;


    @BindView(R.id.lawyer_reg_civil)
    RadioButton civil;



    @BindView(R.id.lawyer_reg_address)
    AutoCompleteTextView address;




    @BindView(R.id.lawyer_reg_services_civil)
    RadioButton services_civil;


    @BindView(R.id.lawyer_reg_services_criminal)
    RadioButton services_criminal;


    @BindView(R.id.lawyer_reg_bio)
    EditText bio;

    @BindView(R.id.lawyer_reg_password)
    AutoCompleteTextView password;


    @BindView(R.id.ivRegLogo)
    ImageView imageView;


    @BindView(R.id.lawyer_reg_total_case)
    AutoCompleteTextView total_case;


    @BindView(R.id.lawyer_reg_total_case_win)
    AutoCompleteTextView totalCaseWin;



    Uri imageUri;

    private ListenerRegistration listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_lawyer);
        ButterKnife.bind(this);

    }



    @OnClick(R.id.ivRegLogo)
    public void selectImage(){


        Intent pickImage = new Intent(Intent.ACTION_PICK);
        pickImage.setType("image/*");
        startActivityForResult(pickImage, PICK_IMAGE);
    }




    @OnClick(R.id.lawyer_reg_signup)
    public void signUp(){
        if(!validate()) {
            return;
        }

        Helper.showProgressDialog(this);



        listener =  FirebaseFirestore.getInstance().collection("lawyers")
                .whereEqualTo("email", email.getText().toString() )
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots.getDocuments().size() == 0 ){
                            listener.remove();
                            saveImageInStorage();
                        } else{
                            Helper.hideProgressDialog();
                            listener.remove();
                            Toast.makeText(RegistrationLawyer.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void saveImageInFireStore(Uri uri) {
        String court  = highCourt.isSelected() ? highCourt.getText().toString() : civil.getText().toString() ;

        String services = services_civil.isSelected() ? services_civil.getText().toString() : services_criminal.getText().toString();
        AttorneyData attorneyData = new AttorneyData(
                name.getText().toString(),
                email.getText().toString(),
                contact.getText().toString(),
                bcid.getText().toString(),
                city.getText().toString(),
                court,
                address.getText().toString(),
                services,
                bio.getText().toString(),
                ""+uri,
                password.getText().toString(),
                total_case.getText().toString(),
                totalCaseWin.getText().toString()
        );



        FirebaseFirestore.getInstance().collection("lawyers")
                .add(attorneyData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Helper.hideProgressDialog();
                        Toast.makeText(RegistrationLawyer.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        signIn();
                        return;
                    } else{
                        Helper.hideProgressDialog();
                        Toast.makeText(RegistrationLawyer.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                        return;
                    }

                });
    }

    private void saveImageInStorage() {
        StorageReference imagesReference = FirebaseStorage.getInstance().getReference("images/"+System.currentTimeMillis()+".jpg");


        imagesReference.putFile(imageUri)
                .addOnCompleteListener(task -> imagesReference.getDownloadUrl().addOnCompleteListener(task1 -> saveImageInFireStore(task1.getResult())));
    }

    private boolean validate() {

        if (name.getText().toString().isEmpty()){
            name.setError("Empty");
            return false;
        }


        if (email.getText().toString().isEmpty()){
            email.setError("Empty");
            return false;
        }


        if (contact.getText().toString().isEmpty()){
            contact.setError("Empty");
            return false;
        }


        if (bcid.getText().toString().isEmpty()){
            bcid.setError("Empty");
            return false;
        }

        if (city.getText().toString().isEmpty()){
            city.setError("Empty");
            return false;
        }
        if (address.getText().toString().isEmpty()){
            address.setError("Empty");
            return false;
        }

        if (bio.getText().toString().isEmpty()){
            bio.setError("Empty");
            return false;
        }



        if (password.getText().toString().isEmpty()){
            password.setError("Empty");
            return false;
        }


        if (imageUri == null){
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
            return false;
        }




        return true;
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

    @OnClick(R.id.lawyer_reg_signin)
    public void signIn(){

        startActivity(new Intent(this, LawyerLoginActivity.class));
        finish();

    }
}
