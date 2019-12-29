package com.example.Insaaf.Presenter.clients;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.Insaaf.Presenter.common.ChatBot;
import com.example.Insaaf.Presenter.common.LawsActivty;
import com.example.Insaaf.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ClientDashboard extends AppCompatActivity {


    @BindView(R.id.client_dash_view_attornies)
    CardView attornies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);
    }




    @OnClick(R.id.client_dash_view_attornies)
    public void showAttornies(View view){
        startActivity(new Intent(this, ShowAttorneys.class));

    }

    public void openLaws(View view) {
        Intent intent = new Intent(this, LawsActivty.class);
        startActivity(intent);
    }

    public void view_appointments(View view) {
        Intent seeAppointments = new Intent(this, Appointments.class);
        startActivity(seeAppointments);
    }

    public void openChatBot(View view) {


        Intent intent =  new Intent(this, ChatBot.class);
        startActivity(intent);
    }

    public void cancelAppointments(View view) {

        Intent intent =  new Intent(this, CancelAppointments.class);
        startActivity(intent);

    }


    public void forumClicked(View view) {
        Intent intent = new Intent(this, ForumMainActivity.class);
        startActivity(intent);
    }
}
