package com.example.Insaaf.Presenter.clients;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.Insaaf.Model.ForumMianAdapter;
import com.example.Insaaf.Presenter.dialog.MainForumDialog;
import com.example.Insaaf.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForumMainActivity extends AppCompatActivity {

    @BindView(R.id.forum_recyclerview)
    RecyclerView recyclerView;


    @BindView(R.id.forum_main_toolbar)
    Toolbar toolbar;


    ForumMianAdapter forumMianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_main);
        ButterKnife.bind(this);



        setSupportActionBar(toolbar);
        setUpViews();

        getData();

    }

    private void getData() {


        FirebaseFirestore.getInstance().collection("forum")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        List<ForumMianAdapter.ForumModel> forumModelList = new ArrayList<>();

                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                            ForumMianAdapter.ForumModel forumModel = documentSnapshot.toObject(ForumMianAdapter.ForumModel.class);
                            forumModel.setId(documentSnapshot.getId());
                            forumModelList.add(forumModel);
                        }
                        forumMianAdapter.setList(forumModelList);
                    }
                });

    }

    private void setUpViews() {
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        forumMianAdapter = new ForumMianAdapter();
        recyclerView.setAdapter(forumMianAdapter);



    }


    @OnClick(R.id.forum_main_fab)
    public void fabClicked(){

        MainForumDialog mainForumDialog = new MainForumDialog(this, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        mainForumDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_forum, menu);

        MenuItem searchItem = menu.findItem(R.id.forum_main_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                forumMianAdapter.getFilter().filter(s);
                return true;
            }
        });
        return true;
    }
}
