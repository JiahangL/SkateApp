package com.example.jiahang.skateapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Jiahang on 7/23/2017.
 */

public class SkateFragment extends Fragment {
    private static final String ARG_MODEL_ID = "model_id";

    private static final String[] SKATE = new String[] {"Clear", "S", "S.K", "S.K.A", "S.K.A.T", "S.K.A.T.E"};

    private TextView player_1;
    private TextView player_1_STATUS;
    private TextView player_2;
    private TextView player_2_STATUS;

    private Button player_1_fail;
    private Button player_2_fail;

    private Model model;

    // convenience method for SkateActivity to call to create the fragment with a model's UUID to get
    // a specific instance of a model, contained inside the argument bundle;
    //     Used in SkateActivity's createFragment(), which is then used in SingleFragmentActivity.
    public static SkateFragment newInstance(UUID modelId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MODEL_ID, modelId);

        SkateFragment fragment = new SkateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // initializes the UUID and model object for a specific fragment corresponding to that model
    //     UUID is pulled from the arguments bundle, which should have been created by the container
    // activity, which should've used newInstance(UUID) to create the fragment and the arguments bundle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Each fragment has its own arguments bundle, which is where we get and set the UUID
        UUID modelId = (UUID)getArguments().getSerializable(ARG_MODEL_ID);
        // TODO: confused about why we need to get activity context...
        // FIXED (STUPID) BUG: forgot to put in the UUID for the ContentValue, field was null in database

        model = ModelGroup.get(getActivity()).getModel(modelId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                 Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.skate_fragment, container, false);

        //initialize player name TextViews
        player_1 = (TextView)v.findViewById(R.id.player_1);
        player_1_STATUS = (TextView)v.findViewById(R.id.player_1_SKATE);
        player_2 = (TextView)v.findViewById(R.id.player_2);
        player_2_STATUS = (TextView)v.findViewById(R.id.player_2_SKATE);
        //initialize player buttons
        player_1_fail = (Button)v.findViewById(R.id.player_1_add);

        //TODO: set STATUS texts based on previous state;
        player_1_STATUS.setText(SKATE[model.getPlayer1_skate()]);
        player_2_STATUS.setText(SKATE[model.getPlayer2_skate()]);


        player_1_fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int playerStatus = model.getPlayer1_skate();
                if(playerStatus == 5) {
                    player_1_STATUS.setText("YOU LOSE");
                } else {
                    /* originally ran into the mistake of using post incrementer in
                     * parameter. Java is PASS BY VALUE. post incrementer passes the
                     * original value, then increments it. The method would the original value
                     * before it was incremented.
                    */
                    playerStatus++;
                    model.setPlayer1_skate(playerStatus);
                    player_1_STATUS.setText(SKATE[playerStatus]);
                }
            }
        });

        player_2_fail = (Button)v.findViewById(R.id.player_2_add);
        player_2_fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int playerStatus = model.getPlayer2_skate();
                //TODO: Define constants for the switch cases?
                if(playerStatus == 5) {
                    player_2_STATUS.setText("YOU LOSE");
                } else {
                    /* originally ran into the mistake of using post incrementer in
                     * parameter. Java is PASS BY VALUE. post incrementer passes the
                     * original value, then increments it. The method would the original value
                     * before it was incremented.
                    */
                    playerStatus++;
                    player_2_STATUS.setText(SKATE[playerStatus]);
                    model.setPlayer2_skate(playerStatus);
                }
            }
        });
        // set the TextViews correctly
        player_1.setText(model.getPlayer1());
        player_2.setText(model.getPlayer2());

        return v;
    }
    /* If we were to exit this fragment, the onStop() method would be called before being destroyed
     *    Before destroying this fragment, we should save the changes we made to our model instance
     * and update the database accordingly.
     *
     * PROBLEM: when SkateFragment started from ListFragment, list would not update when returning
     *          from started SkateFragment via the back button
     * FIX: Remember lifecycle call sequence when starting activities, let's say A starts B:
     *          - A's onPause executes
     *          - B's onCreate(), onStart(), onResume() executes, B is now visible
     *          - A no longer visible, onStop() executes
     *
     *      if we update our singleton's data in the A's onStop(), while we updates B's UI in
     *      B's onResume(), the UI gets updated before the data gets updated! In our case, it is
     *      OK to update our singleton's data in A's onPause() first, so the data set is changed
     *      before the UI is updated
     *
     */

    @Override
    public void onPause() {
        super.onPause();
        // pass the model instance we are currently working on, and probably have modified, into
        // the ModelGroup, which is the "model manager".
        ModelGroup.get(getActivity()).updateModel(model);
    }
}
