package com.example.Insaaf.util;

import android.app.ProgressDialog;
import android.content.Context;

public class Helper {

    private static ProgressDialog progressDialog;
    public static void showProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    public static void hideProgressDialog(){
        progressDialog.dismiss();
    }
}
