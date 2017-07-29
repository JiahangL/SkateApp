package com.example.jiahang.skateapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;
import java.util.UUID;

public class SkateActivity extends SingleFragmentActivity {

    private static final String EXTRA_MODEL_ID = "com.example.jiahang.skateapp.model_id";

    public static Intent newIntent(Context packageContext, UUID modelId) {
        Intent intent = new Intent(packageContext, SkateActivity.class);
        intent.putExtra(EXTRA_MODEL_ID, modelId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID modelId = (UUID)getIntent().getSerializableExtra(EXTRA_MODEL_ID);
        return SkateFragment.newInstance(modelId);
    }

}
