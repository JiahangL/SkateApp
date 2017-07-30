package com.example.jiahang.skateapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jiahang on 7/29/2017.
 */

public class ModelCreatorFragment extends DialogFragment {

    public static final String EXTRA_PLAYER_1 = "com.example.jiahang.skateapp.player_1";
    public static final String EXTRA_PLAYER_2 = "com.exmaple.jiahang.skateapp.player_2";

    TextView player_1_label;
    TextView player_2_label;
    EditText player_1_edit;
    EditText player_2_edit;

    public static ModelCreatorFragment newInstance(){
        ModelCreatorFragment fragment = new ModelCreatorFragment();
        return fragment;
    }
    // used below in onCreateDialog() to send entered data to our target fragment
    private void sendResult(int resultCode, String player1, String player2) {
        if(getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_PLAYER_1, player1);
        intent.putExtra(EXTRA_PLAYER_2, player2);
        // calling the target fragment's (MainFragment) onActivityResult()
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get the view from layout file
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_model, null);

        // initialize widgets
        player_1_label = (TextView)v.findViewById(R.id.player_1_label);
        player_2_label = (TextView)v.findViewById(R.id.player_2_label);
        player_1_edit = (EditText)v.findViewById(R.id.player_1_edit);
        player_2_edit = (EditText)v.findViewById(R.id.player_2_edit);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String player1_name = player_1_edit.getText().toString();
                                String player2_name = player_2_edit.getText().toString();
                                // send the parsed data from EditText widgets to convenience method,
                                // it will put the parsed data into intent as extras, then pass the
                                // intent over to the activity of the target fragment(?)
                                sendResult(Activity.RESULT_OK, player1_name, player2_name);
                            }
                        }

                ).create();
    }
}
