package com.ysdc.movie.ui.splash;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.ysdc.movie.R;
import com.ysdc.movie.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * First activity displayed in our app. Used to initialize the app through its presenter. If any error occured, a message is displayed with the possibility to retry.
 */
public class SplashActivity extends BaseActivity implements SplashMvpView{

    @Inject
    SplashMvpPresenter<SplashMvpView> presenter;

    @BindView(R.id.btn_retry)
    protected  Button retryBtn;
    @BindView(R.id.progress)
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(SplashActivity.this);
        initApplication();
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

    @OnClick(R.id.btn_retry)
    public void initApplication(){
        retryBtn.setVisibility(View.GONE);
        showProgress();
        presenter.initApplication();
    }

    @Override
    public void applicationInitialized() {
        hideProgress();
    }

    @Override
    public void onConfigurationRetrivalError(Throwable throwable) {
        hideProgress();
        retryBtn.setVisibility(View.VISIBLE);
        onError(throwable);
    }

    private void showProgress() {
        if (progressBar != null && progressBar.getVisibility() == View.GONE) {
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void hideProgress() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
