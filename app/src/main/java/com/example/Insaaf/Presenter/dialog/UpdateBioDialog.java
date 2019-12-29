package com.example.Insaaf.Presenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Insaaf.Model.AttorneyData;
import com.example.Insaaf.R;
import com.example.Insaaf.util.SharedPreferenceHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateBioDialog extends Dialog {


    private final ListenerRegistration listener;
    @BindView(R.id.update_bio_ed)
    EditText bio;



    String userId;



    public UpdateBioDialog(@NonNull Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null){
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setContentView(R.layout.dialog_update_bio);
        ButterKnife.bind(this);


        listener = FirebaseFirestore.getInstance().collection("lawyers")
                .whereEqualTo("contact_no", SharedPreferenceHelper.INSTANCE.getContactNumber())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        listener.remove();

                        userId = queryDocumentSnapshots.getDocuments().get(0).getId();
                        AttorneyData attorneyData = queryDocumentSnapshots.getDocuments().get(0).toObject(AttorneyData.class);
                        bio.setText(attorneyData.getBio());
                    }
                });

        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }



    @OnClick(R.id.update_bio_save)
    public void saveBio(){
        if (bio.getText().toString().isEmpty()) return;
        FirebaseFirestore.getInstance().collection("lawyers")
                .document(userId)
                .update("bio", bio.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT);
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), " Update error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @OnClick(R.id.update_bio_cancel)
    public void closeDialog(){
        dismiss();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (listener != null) listener.remove();
    }
}
