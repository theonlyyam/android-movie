package com.ysdc.movie.ui.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Base presenter that contains what every presenter needs. For this application, there is not so much, only the RX disposable.
 */

public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final CompositeDisposable compositeDisposable;
    private V mvpView;

    protected BasePresenter() {
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

    protected V getMvpView() {
        return mvpView;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

}