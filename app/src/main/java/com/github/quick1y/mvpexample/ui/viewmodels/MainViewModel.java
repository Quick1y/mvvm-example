package com.github.quick1y.mvpexample.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.github.quick1y.mvpexample.ui.models.MainModel;

public class MainViewModel extends ViewModel implements MainModel.OnStringLoad {

    private MutableLiveData<String> mString;
    private MutableLiveData<Boolean> mLoadingVisible;
    private OnErrorObserver mOnErrorObserver;

    private MainModel mModel;

    public MainViewModel() {
        super();
        mString = new MutableLiveData<>();
        mLoadingVisible = new MutableLiveData<>();

        mModel = new MainModel(this);
    }


    @Override
    protected void onCleared() {
        mModel.destroy();
        mOnErrorObserver = null;
        super.onCleared();
    }

    public void setOnErrorObserver(OnErrorObserver observer){
        mOnErrorObserver = observer;
    }




    public LiveData<Boolean> getLoadingVisibleObserver(){
        return mLoadingVisible;
    }


    public LiveData<String> getDataObservable(){
        return mString;
    }

    public void updateData(){
        mLoadingVisible.postValue(true);
        mModel.loadNewString();
    }

    @Override
    public void onStringLoad(String string) {
        mLoadingVisible.postValue(false);
        mString.postValue(string);
    }




    @Override
    public void onError(String errorStr) {
        mLoadingVisible.postValue(false);
        if (mOnErrorObserver != null) mOnErrorObserver.onError(errorStr);
    }



    public interface OnErrorObserver {
        void onError(String code);
    }


}