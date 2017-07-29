package com.example.jiahang.skateapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.jiahang.skateapp.database.ModelDbSchema.*;

/**
 * Created by Jiahang on 7/23/2017.
 */

public class ModelBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "modelBase.db";

    public ModelBaseHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + ModelTable.NAME + "(" +
                    ModelTable.Cols.UUID + ", " +
                    ModelTable.Cols.PLAYER_1 + ", " +
                    ModelTable.Cols.PLAYER_1_SKATE + ", " +
                    ModelTable.Cols.PLAYER_2 + ", " +
                    ModelTable.Cols.PLAYER_2_SKATE + ")"
                  );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
