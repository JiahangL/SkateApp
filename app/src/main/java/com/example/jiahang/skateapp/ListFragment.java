package com.example.jiahang.skateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jiahang on 8/1/2017.
 */

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CustomAdapter myAdapter;

    //This is the ViewHolder
    private class ModelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView listItemNames;
        private TextView listItemScore;

        private Model model;

        //ViewHolder constructor, initializes and links views of a row to the list
        public ModelHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item, parent, false));
            // itemView is a system generated variable(?)
            itemView.setOnClickListener(this);

            listItemNames = (TextView)itemView.findViewById(R.id.list_item_names);
            listItemScore = (TextView)itemView.findViewById(R.id.list_item_scores);
        }

        //called by the Adapter; ViewHolder should change views based on data passed from Adapter
        public void bind(Model model){
            this.model = model;
            listItemNames.setText(model.getPlayer1()+":"+model.getPlayer2());
            listItemScore.setText(model.getPlayer1_skate()+":"+model.getPlayer2_skate());
        }

        // when list item is clicked, resume a game according to the selected list item
        @Override
        public void onClick(View view) {
            Intent intent = SkateActivity.newIntent(getActivity(), model.getId());
            startActivity(intent);
        }
    }

    //This is the list adapter
    private class CustomAdapter extends RecyclerView.Adapter<ModelHolder>{
        private List<Model> modelList;

        public CustomAdapter(List<Model> modelList) {
            this.modelList = modelList;
        }

        @Override
        public ModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ModelHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ModelHolder holder, int position) {
            Model model = modelList.get(position);
            holder.bind(model);
        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }

        public void setModelList(List<Model> modelList) {
            this.modelList = modelList;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateList();

        return v;
    }

    private void updateList() {
        ModelGroup modelGroup = ModelGroup.get(getActivity());
        List<Model> models = modelGroup.getModelList();

        if(myAdapter == null) {
            myAdapter = new CustomAdapter(models);
            recyclerView.setAdapter(myAdapter);
        } else {
            myAdapter.setModelList(models);
            myAdapter.notifyDataSetChanged();
        }
    }
}
