package com.ysdc.movie.ui.main;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.ysdc.movie.R;
import com.ysdc.movie.ui.base.BaseActivity;
import com.ysdc.movie.ui.splash.SplashMvpPresenter;
import com.ysdc.movie.ui.splash.SplashMvpView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity in charge to display our two fragment: movie list and details
 */
public class MainActivity extends BaseActivity implements MainMvpView{

    @Inject
    MainMvpPresenter<MainMvpView> presenter;
    @BindView(R.id.progress)
    protected ProgressBar progressBar;
    @BindView(R.id.fragmentContainer)
    protected FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
