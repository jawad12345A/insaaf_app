package com.example.Insaaf.Presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.Insaaf.Presenter.clients.ClientLogin;
import com.example.Insaaf.Presenter.lawyer.LawyerLoginActivity;
import com.example.Insaaf.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void client(View view) {

        startActivity(new Intent(this, ClientLogin.class));
    }

    public void lawyer(View view) {
        startActivity(new Intent(this, LawyerLoginActivity.class));

    }
}
