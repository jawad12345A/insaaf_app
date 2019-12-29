package com.example.Insaaf.Presenter.clients;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.example.Insaaf.Model.AttorneyData;
import com.example.Insaaf.Model.ShowAttorniesAdapter;
import com.example.Insaaf.R;
import com.example.Insaaf.util.MyItemDecoration;
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

public class ShowAttorneys extends AppCompatActivity {




    @BindView(R.id.show_attornyes_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.show_attorney_toolbar)
    Toolbar toolbar;


    ShowAttorniesAdapter adapter;
    private ListenerRegistration listener;
    private ArrayList<MenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attorneies);

        ButterKnife.bind(this);


        setSupportActionBar(toolbar);

        setUpRecyclerview();
        getDataFromFirebase();




    }

    private void getDataFromFirebase() {

        listener = FirebaseFirestore.getInstance().collection("lawyers").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                List<AttorneyData> documents = new ArrayList();
                for (DocumentSnapshot documentSnapshot: list){
                    documents.add(documentSnapshot.toObject(AttorneyData.class));
                }
                adapter.setList(documents);
            }
        });

    }

    private void setUpRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new MyItemDecoration());
        adapter = new ShowAttorniesAdapter();
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null) listener.remove();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.client_attorny_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menuItems = new ArrayList<MenuItem>();
        for (int i =0; i < menu.size() ; i++ ){
            menuItems.add(menu.getItem(i));
        }
        return super.onPrepareOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.filter_highcourt:
                break;
            case R.id.filter_civil_service:
                break;
            case R.id.filter_criminal_service:
                break;
            case R.id.filter_supreme:
                break;
        }

        item.setChecked(!item.isChecked());
        setAllUnChecked(item);
        adapter.getFilter().filter(item.getTitle());

        return super.onOptionsItemSelected(item);
    }

    private void setAllUnChecked(MenuItem item) {

        for (MenuItem menuItem: menuItems){
            if (menuItem == item) continue;
            menuItem.setChecked(false);
        }
    }
}
