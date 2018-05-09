package com.github.quick1y.mvpexample.ui.views;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.quick1y.mvpexample.R;
import com.github.quick1y.mvpexample.ui.viewmodels.MainViewModel;


public class MainActivity extends AppCompatActivity{

    private MainViewModel mViewModel;
    private TextView mTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        observeViewModel(mViewModel);
    }


    private void initViews(){
        mTextView = findViewById(R.id.main_tv);
        mProgressBar = findViewById(R.id.main_pb);
    }


    /**
     * Подписываемся на события MainViewModel
     * @param vm
     */
    private void observeViewModel(MainViewModel vm) {
        vm.setOnErrorObserver(new MainViewModel.OnErrorObserver() {
            @Override
            public void onError(String code) {
                mTextView.setText("=(");
                Toast.makeText(MainActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            }
        });

        vm.getDataObservable().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mTextView.setText(s);
            }
        });

        vm.getLoadingVisibleObserver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) return;
                mProgressBar.setVisibility(aBoolean? View.VISIBLE : View.GONE);
                mTextView.setVisibility(aBoolean? View.GONE : View.VISIBLE);
            }
        });


    }


    /**
     * Вызывается по клику на кнопку update
     */
    public void onUpdateClick(View view){
        mViewModel.updateData();
    }



}
