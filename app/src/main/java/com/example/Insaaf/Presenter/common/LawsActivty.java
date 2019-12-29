package com.example.Insaaf.Presenter.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


import com.example.Insaaf.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LawsActivty extends AppCompatActivity {



//
//    @BindView(R.id.laws_webview)
//    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws_activty);
        ButterKnife.bind(this);

//
//
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("https://pakistancode.gov.pk/english/index");
    }






    @OnClick(R.id.law_civil)
    public void showCivil(){
        LawsList.dispatchCivilList(this);

    }


    @OnClick(R.id.law_criminal)
    public void showCriminal(){
        LawsList.dispatchCriminalList(this);



    }
}
