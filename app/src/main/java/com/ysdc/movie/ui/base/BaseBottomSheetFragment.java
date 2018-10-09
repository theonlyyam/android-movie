package com.ysdc.movie.ui.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import com.ysdc.movie.app.MyApplication;
import com.ysdc.movie.injection.component.FragmentComponent;
import com.ysdc.movie.injection.module.FragmentModule;

import butterknife.Unbinder;

/**
 * As for the Activity and fragment, we have a base bottom sheet fragment that has the same objective: reduce code duplicate, and simplify the mechanism of
 * our MVP implementation by doing
 * part of the job here (dagger DI, butterknife, etc.).
 */
public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment implements MvpView {

    private BaseActivity activity;
    private Unbinder unBinder;
    private FragmentComponent fragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.activity = (BaseActivity) context;
        }
    }

    protected abstract void setUp(View view);

    @Override
    public void onError(Throwable throwable) {
        if (activity != null) {
            activity.onError(throwable);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (activity != null) {
            activity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (activity != null) {
            activity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (activity != null) {
            activity.showMessage(resId);
        }
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (unBinder != null) {
            unBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public FragmentComponent getFragmentComponent() {
        if ((fragmentComponent == null) && (activity != null)) {
            fragmentComponent = ((MyApplication) activity.getApplication())
                    .getAppComponent()
                    .childFragmentComponent(new FragmentModule(this));
        }
        return fragmentComponent;
    }

    public void setUnBinder(Unbinder unBinder) {
        this.unBinder = unBinder;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    @Override
    public Resources provideResources() {
        return getResources();
    }
}