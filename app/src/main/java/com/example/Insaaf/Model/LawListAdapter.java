package com.example.Insaaf.Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Insaaf.Presenter.common.PDFViewerClass;
import com.example.Insaaf.R;

import java.util.ArrayList;
import java.util.List;

public class LawListAdapter extends RecyclerView.Adapter<LawListAdapter.LawListViewHolder> {




    List<LawModel> lawModels = new ArrayList<>();
    @NonNull
    @Override
    public LawListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LawListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_law, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LawListViewHolder lawListViewHolder, int i) {
        LawModel lawModel = lawModels.get(i);
        lawListViewHolder.setData(lawModel);

    }

    @Override
    public int getItemCount() {
        return lawModels.size();
    }


    public void setLawModels(List list){
        lawModels = list;
    }

    public class LawListViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public LawListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_law_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PDFViewerClass.dispatchIntent(itemView.getContext(), lawModels.get(getAdapterPosition()).path);
                }
            });
        }

        public void setData(LawModel lawModel){
            name.setText(lawModel.name);
        }
    }

    static public class LawModel{
        String name;
        String path;

        public LawModel(String name, String path) {
            this.name = name;
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}



