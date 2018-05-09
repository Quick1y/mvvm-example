package com.github.quick1y.mvpexample.connection;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Connection {

    private static Connection mInstance;

    private List<ConnectionEvents> mListeners;

    private Connection(){
        mListeners = new ArrayList<>();
    }

    public static Connection getInstance(){
        if (mInstance == null){
            mInstance = new Connection();
        }
        return mInstance;
    }



    public void loadString(){
        final Handler handler = new Handler();

        new Thread(){
            @Override
            public void run(){
                final String string = System.nanoTime() + " ns from startup";

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (ConnectionEvents e : mListeners){
                            if (e != null) {
                                if (new Random().nextInt(100) < 30){
                                    e.onError("network error");
                                } else {
                                    e.onStringLoad(string);
                                }
                            }
                        }
                    }
                });
            }
        }.start();


    }



    public void setEventsListener(ConnectionEvents listener){
        mListeners.add(listener);
    }

    public void removeEventsListener(ConnectionEvents listener){
        mListeners.remove(listener);
    }

}
