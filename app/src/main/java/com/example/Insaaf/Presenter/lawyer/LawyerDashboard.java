package com.example.Insaaf.Presenter.lawyer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.Insaaf.Presenter.common.LawsActivty;
import com.example.Insaaf.Presenter.dialog.UpdateBioDialog;
import com.example.Insaaf.R;

public class LawyerDashboard extends AppCompatActivity {
    private Button logout ,cancell,update;
    private ImageView img,Bus;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);




    }



    public void showLaws(View view) {

            Intent intent = new Intent(this, LawsActivty.class);
            startActivity(intent);

    }

    public void updateBio(View view) {
        UpdateBioDialog updateBioDialog = new UpdateBioDialog(this);
        updateBioDialog.show();
    }

    public void openJudgement(View view) {
        Intent intent = new Intent(this, JudgementActivity.class);
        startActivity(intent);
    }

    public void showAppointements(View view) {
        Intent intent = new Intent(this, LawyerAppointments.class);
        startActivity(intent);



    }

    public void deleteAppointments(View view) {
        Intent intent = new Intent(this, LawyerDeleteAppointments.class);
        startActivity(intent);
    }
}
