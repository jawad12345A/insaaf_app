package com.example.Insaaf.Presenter.common;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.Insaaf.R;
import com.github.barteksc.pdfviewer.PDFView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PDFViewerClass extends AppCompatActivity {

    public static String EXTRA_PATH = "path";




    @BindView(R.id.pdfview_pdfviewer)
    PDFView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer_class);

        ButterKnife.bind(this);


        pdfView.fromAsset(getIntent().getStringExtra(EXTRA_PATH))
                .enableSwipe(true)
                .swipeHorizontal(true)
                .load();

    }


    public static void dispatchIntent(Context context, String path){

        Intent intent = new Intent(context, PDFViewerClass.class);
        intent.putExtra(EXTRA_PATH, path);
        context.startActivity(intent);

    }
}
