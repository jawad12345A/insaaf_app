package com.example.Insaaf.Presenter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.example.Insaaf.Model.ForumMianAdapter;
import com.example.Insaaf.R;
import com.example.Insaaf.util.SharedPreferenceHelper;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainForumDialog extends Dialog {


    @BindView(R.id.dialog_forum_title)
    EditText title;


    @BindView(R.id.dialog_forum_description)
    EditText description;



    OnCancelListener cancelListener;


    public MainForumDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContentView(R.layout.dialog_main_forum);

        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.cancelListener = cancelListener;
    }




    @OnClick(R.id.dialog_forum_okay)
    public void okayClicked(){


        saveData();

        cancelListener.onCancel(this);
    }

    private void saveData() {
        ForumMianAdapter.ForumModel forumModel = new ForumMianAdapter.ForumModel();
        forumModel.setDescription( description.getText().toString());
        forumModel.setUrl(SharedPreferenceHelper.INSTANCE.getUrl());
        forumModel.setTitle(title.getText().toString());
        forumModel.setPostDate(Timestamp.now());


        FirebaseFirestore.getInstance().collection("forum")
                .document().set(forumModel);
    }


    @OnClick(R.id.dialog_forum_cancel)
    public void cancelClicked(){
        cancelListener.onCancel(this);

    }
}
