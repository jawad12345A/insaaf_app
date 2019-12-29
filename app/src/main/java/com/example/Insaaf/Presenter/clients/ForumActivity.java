package com.example.Insaaf.Presenter.clients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Insaaf.Model.ForumAdapter;
import com.example.Insaaf.R;
import com.example.Insaaf.util.SharedPreferenceHelper;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForumActivity extends AppCompatActivity {


    private static final String EXTRA_FORUM_TITLE = "EXTRA_FORUM_TITLE";
    private static final String EXTRA_FORUM_DESCRIPTION = "EXTRA_FORUM_DESCRIPTION";
    @BindView(R.id.forum_recyclerview)
    RecyclerView recyclerView;


    @BindView(R.id.forum_message)
    EditText forum_message_ed;

    @BindView(R.id.forum_toolbar)
    Toolbar toolbar;


    @BindView(R.id.forum_description)
    TextView description;

    ForumAdapter forumAdapter;



    private static final String EXTRA_FORUM_ID = "EXTRA_FORUM_ID";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_FORUM_TITLE));

        description.setText(getIntent().getStringExtra(EXTRA_FORUM_DESCRIPTION));

        setUpRecyclerView();

        getData();
    }

    private void getData() {

        FirebaseFirestore.getInstance().collection("forum")
                .document(getIntent().getStringExtra(EXTRA_FORUM_ID))
                .collection("comments")
                .orderBy("date", Query.Direction.DESCENDING )

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                        List<ForumAdapter.ForumComment> forumCommentList = new ArrayList();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                            ForumAdapter.ForumComment forumComment = documentSnapshot.toObject(ForumAdapter.ForumComment.class);
                            forumComment.setUserId(documentSnapshot.getId());
                            forumCommentList.add(forumComment);
                        }



                        forumAdapter.setList(forumCommentList);
                    }
                });
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        forumAdapter = new ForumAdapter();
        recyclerView.setAdapter(forumAdapter);
    }


    @OnClick(R.id.forum_send)
    public void sendMessageClicked(){

        ForumAdapter.ForumComment forumComment = new ForumAdapter.ForumComment();

        forumComment.setComment(forum_message_ed.getText().toString());
        forumComment.setUserName(SharedPreferenceHelper.INSTANCE.getName());
        forumComment.setUserImage(SharedPreferenceHelper.INSTANCE.getUrl());
        forumComment.setDate(Timestamp.now());

        sendMessage(forumComment);
    }

    private void sendMessage(ForumAdapter.ForumComment forumComment) {
        FirebaseFirestore.getInstance().collection("forum")
                .document(getIntent().getStringExtra(EXTRA_FORUM_ID))
                .collection("comments").document().set(forumComment);

        forum_message_ed.setText("");
    }

    public static void dispatchIntent(Context context, String id, String title, String description){

        Intent intent = new Intent(context, ForumActivity.class);
        intent.putExtra(EXTRA_FORUM_ID, id);
        intent.putExtra(EXTRA_FORUM_TITLE, title);
        intent.putExtra(EXTRA_FORUM_DESCRIPTION, description);
        context.startActivity(intent);
    }





}
