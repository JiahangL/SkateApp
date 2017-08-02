package com.example.jiahang.skateapp;

import android.support.v4.app.Fragment;

/**
 * Created by Jiahang on 8/1/2017.
 */

public class ListActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new ListFragment();
    }
}
