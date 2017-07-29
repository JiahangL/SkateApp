package com.example.jiahang.skateapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.jiahang.skateapp.Model;

import java.util.UUID;

import static com.example.jiahang.skateapp.database.ModelDbSchema.*;

/**
 * Created by Jiahang on 7/23/2017.
 */

public class ModelCursorWrapper extends CursorWrapper {

    public ModelCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Model getModel() {
        String uuidString = getString(getColumnIndex(ModelTable.Cols.UUID));
        String player1 = getString(getColumnIndex(ModelTable.Cols.PLAYER_1));
        int player1_skate= getInt(getColumnIndex(ModelTable.Cols.PLAYER_1_SKATE));
        String player2 = getString(getColumnIndex(ModelTable.Cols.PLAYER_2));
        int player2_skate= getInt(getColumnIndex(ModelTable.Cols.PLAYER_2_SKATE));

        Model model = new Model(UUID.fromString(uuidString));
        model.setPlayer1(player1);
        model.setPlayer1_skate(player1_skate);
        model.setPlayer2(player2);
        model.setPlayer2_skate(player2_skate);

        return model;
    }
}
