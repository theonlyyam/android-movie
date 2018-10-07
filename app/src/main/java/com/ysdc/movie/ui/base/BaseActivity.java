package com.ysdc.movie.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ysdc.movie.R;
import com.ysdc.movie.app.MyApplication;
import com.ysdc.movie.exception.MovieDbException;
import com.ysdc.movie.exception.NoConnectivityException;
import com.ysdc.movie.injection.component.ActivityComponent;
import com.ysdc.movie.injection.module.ActivityModule;
import com.ysdc.movie.utils.NetworkUtils;

import javax.inject.Inject;

import butterknife.Unbinder;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    @Inject
    NetworkUtils networkUtils;
    private ActivityComponent activityComponent;
    private Unbinder unBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = ((MyApplication) getApplication()).getAppComponent().childActivityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onError(Throwable throwable) {
        Timber.e(throwable, "An Error occurred");
        if (throwable instanceof NoConnectivityException) {
            onError(R.string.exception_no_connectivity);
        } else if (throwable instanceof MovieDbException) {
            showMessage(throwable.getMessage());
        } else {
            onError(R.string.exception_undefined);
        }
    }

    private void onError(String error) {
        Timber.e("ERROR: %s", error);
        showMessage(error);
    }

    @Override
    public void onError(@StringRes int resId) {
        String msg = getString(resId);
        onError(msg);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return networkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        if (unBinder != null) {
            unBinder.unbind();
        }
        super.onDestroy();
    }

    protected void setUnBinder(Unbinder unBinder) {
        this.unBinder = unBinder;
    }

    protected ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
