package com.ysdc.movie.ui.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by david on 5/10/18.
 */

public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    protected final CompositeDisposable compositeDisposable;
    private V mvpView;

    public BasePresenter() {
        super();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        mvpView = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public V getMvpView() {
        return mvpView;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

}