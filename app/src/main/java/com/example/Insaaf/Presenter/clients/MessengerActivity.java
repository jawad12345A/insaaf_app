package com.example.Insaaf.Presenter.clients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.Insaaf.Model.MessengerAdapterClient;
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

public class MessengerActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    EditText messenger;
    ImageView sendImg;
    MessengerAdapterClient adapterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);



        setUpViews();
        setUpUIListeners();

        getData();

    }

    private void setUpUIListeners() {


        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MessengerAdapterClient.Messenger messenger = new MessengerAdapterClient.Messenger();
                messenger.setSender(SharedPreferenceHelper.INSTANCE.getId());
                messenger.setMessage(MessengerActivity.this.messenger.getText().toString());
                messenger.setTime(Timestamp.now());
                messenger.setReceipt(getIntent().getStringExtra(EXTRA_OTHERUSER));

                sendData(messenger);


            }
        });

    }



    private void sendData(MessengerAdapterClient.Messenger message){
        FirebaseFirestore.getInstance().collection("appointments")
                .document(getIntent().getStringExtra(EXTRA_ID))
                .collection("messages")
                .document().set(message);

        messenger.setText("");
    }



    private void getData() {


        FirebaseFirestore.getInstance().collection("appointments")
                .document(getIntent().getStringExtra(EXTRA_ID))
                .collection("messages")
                .orderBy("time", Query.Direction.DESCENDING )
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                        List<MessengerAdapterClient.Messenger> dataList = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                            MessengerAdapterClient.Messenger messenger = new MessengerAdapterClient.Messenger();

                            messenger.setMessage(documentSnapshot.getString("message"));
                            messenger.setReceipt(documentSnapshot.getString("receiver"));
                            messenger.setSender(documentSnapshot.getString("sender"));
                            messenger.setTime(documentSnapshot.getTimestamp("time"));


                            dataList.add(messenger);

                        }

                   //    List<MessengerAdapterClient.Messenger> list = queryDocumentSnapshots.toObjects(MessengerAdapterClient.Messenger.class);

                      adapterClient.setList(dataList);

                    }
                });

    }

    private void setUpViews() {
        recyclerView = findViewById(R.id.messenger_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));

        adapterClient = new MessengerAdapterClient();
        recyclerView.setAdapter(adapterClient);


        messenger = findViewById(R.id.messenger_name);
        sendImg = findViewById(R.id.messenger_send);

    }




    static String EXTRA_ID = "EXTRA_ID";
    static String EXTRA_OTHERUSER = "EXTRA_OTHER_USER";
    public static void dispatchIntent(Context context, String contentId, String otherUserId){
        Intent intent = new Intent(context, MessengerActivity.class);
        intent.putExtra(EXTRA_ID, contentId);
        intent.putExtra(EXTRA_OTHERUSER, otherUserId);
        context.startActivity(intent);

    }
}
