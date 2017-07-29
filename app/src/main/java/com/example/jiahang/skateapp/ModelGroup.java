package com.example.jiahang.skateapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jiahang.skateapp.database.ModelBaseHelper;
import com.example.jiahang.skateapp.database.ModelCursorWrapper;
import com.example.jiahang.skateapp.database.ModelDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.jiahang.skateapp.database.ModelDbSchema.*;

/**
 * Created by Jiahang on 7/23/2017.
 */

public class ModelGroup {
    private static ModelGroup modelGroup;

    // is the list needed?
    //private List<Model> modelList;
    private Context context;
    private SQLiteDatabase database;

    // Are we creating a singleton for an instance of ModelGroup?
    public static ModelGroup get(Context context) {
        if (modelGroup == null)
            modelGroup = new ModelGroup(context);
        return modelGroup;
    }
    // private constructor indicates a singleton pattern?
    private ModelGroup (Context passedContext) {
        // create writable database
        context = passedContext.getApplicationContext();
        database = new ModelBaseHelper(context).getWritableDatabase();
    }

    public void addModel(Model model) {
        ContentValues values = getContentValues(model); // method is defined below as database helper
        Log.d("TAG", "ModelGroup's addModel(): " + model.getId().toString());
        database.insert(ModelTable.NAME, null, values);
    }

    public String checkForEntries(UUID uuid){
        String query = "Select * from " + ModelTable.NAME;
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return "entry DNE";
        }
        cursor.close();
        return "entry EXISTS: " + cursor.getCount();
    }

    public void completelyDelete(){
        String deleteCommand = "DELETE FROM " + ModelTable.NAME;
        database.execSQL(deleteCommand);
    }

    public List<Model> getModelList() {
        List<Model> modelList = new ArrayList<>();

        ModelCursorWrapper cursorWrapper = queryModels(null, null);

        try{
            cursorWrapper.moveToFirst();
            while(!cursorWrapper.isAfterLast()) {
                // calling the wrapper's method, getModel() pulls the data from that row in the
                // database, creates a Model object according the data, then passes it back
                modelList.add(cursorWrapper.getModel());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return modelList;
    }

    public Model getModel(UUID id) {
        // instead of passing in null for queryModels(), we can specify which values inside the table
        // for the cursorWrapper to search for, according to the UUID column,
        ModelCursorWrapper cursorWrapper = queryModels(
                ModelTable.Cols.UUID + " = ?",
                new String [] { id.toString() }
        );

        try {

            if(cursorWrapper.getCount() == 0)
                return null;
            cursorWrapper.moveToFirst();
            return cursorWrapper.getModel();
        } finally {
            cursorWrapper.close();
        }
    }

    public void updateModel(Model model) {
        String uuidString = model.getId().toString();
        ContentValues values = getContentValues(model);

        database.update(ModelTable.NAME, values, ModelTable.Cols.UUID + " = ?",
                new String[]{ uuidString });
    }

    /*************DATABASE HELPER METHODS**********************/
    private static ContentValues getContentValues(Model model) {
        ContentValues values = new ContentValues();
        values.put(ModelTable.Cols.UUID, model.getId().toString());
        values.put(ModelTable.Cols.PLAYER_1, model.getPlayer1());
        values.put(ModelTable.Cols.PLAYER_1_SKATE, model.getPlayer1_skate());
        values.put(ModelTable.Cols.PLAYER_2, model.getPlayer2());
        values.put(ModelTable.Cols.PLAYER_2_SKATE, model.getPlayer2_skate());

        return values;
    }

    private ModelCursorWrapper queryModels(String whereClause, String [] whereArgs) {
        Cursor cursor = database.query(
                ModelTable.NAME,
                null,   //columns - null selects all columns
                whereClause,
                whereArgs,
                null,   // groupBy
                null,   // having
                null    // orderBy
        );

        return new ModelCursorWrapper(cursor);
    }
}
