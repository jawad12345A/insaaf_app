package com.example.Insaaf.Presenter.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


import com.example.Insaaf.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Presenter extends AppCompatActivity {



    @BindView(R.id.presenter_web)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenter);
        ButterKnife.bind(this);



        webView.getSettings().setJavaScriptEnabled(true);



        switch (getIntent().getIntExtra("present", 0)){
            case 0:
                webView.loadUrl("https://peshawarhighcourt.gov.pk/PHCCMS/reportedJudgments.php?action=search");
                break;
            case 1:
                webView.loadUrl("http://mis.ihc.gov.pk/frmSrchOrdr.aspx");
                break;
        }


    }
}
