package com.ysdc.movie.ui.main;

import com.ysdc.movie.data.repository.MovieRepository;
import com.ysdc.movie.ui.base.BasePresenter;

/**
 * Presenter linked to the Main Activity. Contains all the actions implementation of the main activity that are not related to the UI.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    public MainPresenter() {
        //Nothing to do
    }

}
