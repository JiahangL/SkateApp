package com.example.jiahang.skateapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
    }

    // create the option menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_show_list, menu);
    }
    // when the menu item is selected, open up the list of SKATE games
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity(), ListActivity.class);
        startActivity(intent);
        return true;
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
                // FIX: Pass the model data to MainFragment, and let MainFragment create the mode;
                //      and start SkateFragment
                dialog.setTargetFragment(MainFragment.this, REQUEST_MODEL);
                dialog.show(manager, DIALOG_MODEL);
            }
        });

        return v;
    }

    //lifecycle call
    @Override
    public void onResume(){
        super.onResume();
    }

    // receive the result from ModelCreatorFragment as an intent, get the player names from that intent,
    // which are contained as extras.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
            return;

        if(requestCode == REQUEST_MODEL) {
            String player1 = data.getSerializableExtra(ModelCreatorFragment.EXTRA_PLAYER_1).toString();
            String player2 = data.getSerializableExtra(ModelCreatorFragment.EXTRA_PLAYER_2).toString();

            model = new Model();
            model.setPlayer1(player1);
            model.setPlayer2(player2);

            //Once new model is created; add it to the database
            ModelGroup.get(getActivity()).addModel(model);

            // Creates an intent to start a SkateActivity with our newly created model
            Intent intent = SkateActivity.newIntent(getActivity(), model.getId());
            startActivity(intent);
        }
    }

}
