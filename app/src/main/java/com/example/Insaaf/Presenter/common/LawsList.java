package com.example.Insaaf.Presenter.common;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.Insaaf.Model.LawListAdapter;
import com.example.Insaaf.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LawsList extends AppCompatActivity {


    public static  String EXTRA_LIST_TYPE = "list_type";
    public static String EXTRA_LIST_CRIMINAL = "criminal";
    public static String EXTRA_LIST_CIVIL = "civil";


    @BindView(R.id.list_recyclerView)
    RecyclerView recyclerView;




    List<LawListAdapter.LawModel> civilList = new ArrayList<>();
    List<LawListAdapter.LawModel> criminalList = new ArrayList<>();
    private LawListAdapter lawListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws_list);

        ButterKnife.bind(this);



        setUpData();

        setUpRecyclerView();

        bindData();

    }

    private void bindData() {

        if (getIntent().getStringExtra(EXTRA_LIST_TYPE).equalsIgnoreCase(EXTRA_LIST_CIVIL)){
            lawListAdapter.setLawModels(civilList);
        } else  if (getIntent().getStringExtra(EXTRA_LIST_TYPE).equalsIgnoreCase(EXTRA_LIST_CRIMINAL)){
            lawListAdapter.setLawModels(criminalList);
        }

        }

    private void setUpData() {
        setUpCivilList();
        setUpCriminalList();



    }

    private void setUpCriminalList() {
        LawListAdapter.LawModel lawModel1 = new LawListAdapter.LawModel("Abolition of the Punishment of Whipping Act, 1996(Criminal law)",
                "whipping_act.pdf");
        LawListAdapter.LawModel lawModel2 = new LawListAdapter.LawModel("Anti Narcotics Force Act, 1997(Criminal law)",
                "anti_narcotics.pdf");
        LawListAdapter.LawModel lawModel3 = new LawListAdapter.LawModel("Arms Act",
                "arms_act.pdf");
        LawListAdapter.LawModel lawModel4 = new LawListAdapter.LawModel("National Counter Terrorism Authority Act, 2013",
                "national_counter_terrorism.pdf");
        LawListAdapter.LawModel lawModel5 = new LawListAdapter.LawModel("Public Gambling Act, 1867",
                "gambling.pdf");

        criminalList.add(lawModel1);
        criminalList.add(lawModel2);
        criminalList.add(lawModel3);
        criminalList.add(lawModel4);
        criminalList.add(lawModel5);

    }


    private void setUpCivilList() {

        LawListAdapter.LawModel lawModel1 = new LawListAdapter.LawModel("Births, Deaths and Marriages Registration Act, 1886",
                "births.pdf");
        LawListAdapter.LawModel lawModel2 = new LawListAdapter.LawModel("Intellectual Property Organization of Pakistan Act, 2012",
                "intellectual_property.pdf");
        LawListAdapter.LawModel lawModel3 = new LawListAdapter.LawModel("Legal Practitioners and Bar Councils Act, 1973",
                "legal_practitioners.pdf");
        LawListAdapter.LawModel lawModel4 = new LawListAdapter.LawModel("Marriages Validation Act, 1892",
                "mriiages_validation.pdf");
        LawListAdapter.LawModel lawModel5 = new LawListAdapter.LawModel("National Highway Authority Act, 1991",
                "national_highway_act.pdf");

        civilList.add(lawModel1);
        civilList.add(lawModel2);
        civilList.add(lawModel3);
        civilList.add(lawModel4);
        civilList.add(lawModel5);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        lawListAdapter = new LawListAdapter();
        recyclerView.setAdapter(lawListAdapter);

    }


    public static void dispatchCriminalList(Context context){
        Intent intent = new Intent(context, LawsList.class);
        intent.putExtra(EXTRA_LIST_TYPE, EXTRA_LIST_CRIMINAL);
        context.startActivity(intent);
    }


    public static void dispatchCivilList(Context context){
        Intent intent = new Intent(context, LawsList.class);
        intent.putExtra(EXTRA_LIST_TYPE, EXTRA_LIST_CIVIL);
        context.startActivity(intent);
    }
}
