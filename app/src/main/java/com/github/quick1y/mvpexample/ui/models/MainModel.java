package com.github.quick1y.mvpexample.ui.models;

import com.github.quick1y.mvpexample.connection.Connection;
import com.github.quick1y.mvpexample.connection.ConnectionEvents;

public class MainModel extends ConnectionEvents {

    private Connection mConnection;

    private OnStringLoad mCallback;

    public MainModel(OnStringLoad callback){
        mConnection = Connection.getInstance();
        mConnection.setEventsListener(this);
        mCallback = callback;
    }

    public void destroy(){
        mCallback = null;
        mConnection.removeEventsListener(this);
    }



    public void loadNewString(){
        mConnection.loadString();
    }

    @Override
    public void onStringLoad(String string) {
        if (mCallback != null) mCallback.onStringLoad(string);
    }

    @Override
    public void onError(String code) {
        if (mCallback != null) mCallback.onError(code);
    }

    public interface OnStringLoad {
        void onStringLoad(String string);

        void onError(String errorStr);
    }
}
