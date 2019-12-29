package com.example.Insaaf.Presenter.lawyer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.Insaaf.Presenter.common.Presenter;
import com.example.Insaaf.R;

public class JudgementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judgement);
    }

    public void peshawarCourt(View view) {

        Intent intent = new Intent(this, Presenter.class);
        intent.putExtra("present", 0);
        startActivity(intent);
    }

    public void lahoreClick(View view) {

        Intent intent = new Intent(this, Presenter.class);
        intent.putExtra("present", 1);
        startActivity(intent);
    }
}
