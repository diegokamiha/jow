package com.jow_app.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jow_app.R;
import com.jow_app.ui.fragment.CreateTaskFragment;
import com.jow_app.ui.fragment.CreateTaskFragment_;

import org.androidannotations.annotations.EActivity;

/**
 * Created by diego on 3/25/17.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    CreateTaskFragment mCreateTaskFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            mCreateTaskFragment = CreateTaskFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mCreateTaskFragment)
                    .commit();
        }
    }
}
