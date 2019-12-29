package com.example.Insaaf.Presenter.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


import com.example.Insaaf.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatBot extends AppCompatActivity {

    @BindView(R.id.chat_bot_webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        ButterKnife.bind(this);



        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://console.dialogflow.com/api-client/demo/embedded/333cf26e-67c1-42b9-b94a-7d37f0c2b0a0");

    }
}
