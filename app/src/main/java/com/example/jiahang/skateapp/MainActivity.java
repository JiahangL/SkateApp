package com.example.jiahang.skateapp;

import android.support.v4.app.Fragment;

/**
 * Created by Jiahang on 7/26/2017.
 */

public class MainActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment(){
        return new MainFragment();
    }
}
