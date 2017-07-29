package com.example.jiahang.skateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jiahang on 7/26/2017.
 */

public class MainFragment extends Fragment {

    private Button addModel;

    private static final String DIALOG_MODEL = "DialogModel";
    public static final int REQUEST_MODEL = 0;

    private Model model;

    //lifecycle call
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    // initialize the views
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.main_fragment, container, false);

        addModel = (Button)v.findViewById(R.id.createNewModel);
        // set the click listener
        // TODO: present a dialog that lets user set the data for the new model?
        addModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates a new Model and adds it to our database
                FragmentManager manager = getFragmentManager();
                ModelCreatorFragment dialog = ModelCreatorFragment.newInstance();
                // TODO: Can we set the target fragment to be a SkateFragment?
                dialog.setTargetFragment(MainFragment.this, REQUEST_MODEL);
                dialog.show(manager, DIALOG_MODEL);

                // Creates an intent to start a SkateActivity with our newly created model
                Intent intent = SkateActivity.newIntent(getActivity(), model.getId());
                startActivity(intent);
            }
        });

        return v;
    }

    //lifecycle call
    @Override
    public void onResume(){
        super.onResume();
    }


}
