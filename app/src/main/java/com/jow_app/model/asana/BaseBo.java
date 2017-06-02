package com.jow_app.model.asana;

import android.content.Context;

import com.jow_app.webservice.AsanaFetchManager;

/**
 * Created by diego on 4/9/17.
 */

public abstract class BaseBo {

    public AsanaFetchManager asanaFetchManager;
    private Context mContext;

    public BaseBo(Context mContext) {
        this.asanaFetchManager = new AsanaFetchManager(mContext);
        this.mContext = mContext;
    }

}
