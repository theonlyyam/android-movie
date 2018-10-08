package com.ysdc.movie.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.ysdc.movie.R;
import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.ui.base.BaseActivity;
import com.ysdc.movie.ui.base.BaseFragment;
import com.ysdc.movie.ui.moviedetails.MovieDetailsFragment;
import com.ysdc.movie.ui.movielist.MovieListFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity in charge to display our two fragment: movie list and details
 */
public class MainActivity extends BaseActivity implements MainMvpView, MovieListFragment.MovieSelectionListener {

    private static final int MIN_FRAGMENT = 1;

    @Inject
    MainMvpPresenter<MainMvpView> presenter;
    @BindView(R.id.fragmentContainer)
    protected FrameLayout fragmentContainer;

    private MovieListFragment movieListFragment;

    public static Intent getInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(MainActivity.this);

        initView();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onMovieSelected(Movie movie) {
        MovieDetailsFragment detailsFragment = MovieDetailsFragment.newInstance(movie);
        showFragment(detailsFragment);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > MIN_FRAGMENT) {
            super.onBackPressed();
        }
    }
    private void initView() {
        movieListFragment = MovieListFragment.newInstance();
        showFragment(movieListFragment);
    }

    private void showFragment(BaseFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
