package com.jow_app.ui.fragment;

import android.support.v4.app.Fragment;

import com.jow_app.R;

import org.androidannotations.annotations.EFragment;

/**
 * Created by diego on 3/26/17.
 */

@EFragment(R.layout.fragment_create_task)
public class CreateTaskFragment extends Fragment{

    public static CreateTaskFragment newInstance(){
        return CreateTaskFragment_.builder().build();
    }
}
