package com.ysdc.movie.ui.movielist;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ysdc.movie.R;
import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.ui.base.BaseFragment;
import com.ysdc.movie.ui.filter.FilterFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Fragment that display the list of the movies
 */
public class MovieListFragment extends BaseFragment implements MovieListMvpView {

    @BindView(R.id.layout_empty)
    protected RelativeLayout emptyLayout;
    @BindView(R.id.movies_list)
    protected RecyclerView moviesList;
    @BindView(R.id.progress)
    protected ProgressBar progressBar;
    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.shimmer_view_container)
    protected ShimmerFrameLayout shimmerViewContainer;
    @Inject
    MovieListMvpPresenter<MovieListMvpView> presenter;

    private MovieSelectionListener movieSelectionListener;
    private CompositeDisposable compositeDisposable;
    private EndlessRecyclerOnScrollListener scrollListener;
    private MoviesAdapter adapter;

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setTitle(R.string.app_name);
            getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        getFragmentComponent().inject(this);
        setUnBinder(ButterKnife.bind(this, view));

        presenter.onAttach(MovieListFragment.this);
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        compositeDisposable.dispose();
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getBaseActivity() instanceof MovieSelectionListener)) {
            throw new IllegalArgumentException("Activity must implement MovieSelectionListener");
        }
        movieSelectionListener = (MovieSelectionListener) getBaseActivity();
    }

    @Override
    protected void setUp(View view) {
        compositeDisposable = new CompositeDisposable();

        moviesList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        moviesList.setLayoutManager(layoutManager);

        scrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void loadMorelistContent() {
                if (presenter.hasMoreMovies()) {
                    Timber.d("LoadMore");
                    loadMoreMovies();
                }
            }

            @Override
            public void endOfListReached() {
                if (presenter.isQueryInProgress()) {
                    showProgress();
                }
            }
        };

        moviesList.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            showShimmer();
            loadMovies();
        });

        if (presenter.isMovieListInitialized()) {
            moviesList.setAdapter(adapter);
        } else {
            showShimmer();
            loadMovies();
        }
    }

    @OnClick(R.id.search_menu)
    public void onSearchClicked() {
        FilterFragment filterFragment = FilterFragment.newInstance(() -> {
            showShimmer();
            loadMovies();
        });
        filterFragment.show(getActivity().getSupportFragmentManager(), filterFragment.getTag());
    }

    private void showShimmer() {
        shimmerViewContainer.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
        moviesList.setVisibility(View.GONE);
        shimmerViewContainer.startShimmer();
    }

    private void showEmpty() {
        shimmerViewContainer.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        moviesList.setVisibility(View.GONE);
    }

    private void showMovies(List<Movie> movies) {
        shimmerViewContainer.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.GONE);
        moviesList.setVisibility(View.VISIBLE);

        if (adapter == null) {
            adapter = new MoviesAdapter(movies, this::showDetailsFragment);
            moviesList.setAdapter(adapter);
        } else {
            adapter.updateMovies(movies);
        }
        adapter.notifyDataSetChanged();
    }

    private void loadMovies() {
        scrollListener.reset();
        presenter.initializeMovies();
        compositeDisposable.add(presenter.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (shimmerViewContainer.isShimmerStarted()) {
                        shimmerViewContainer.stopShimmer();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    hideProgress();
                })
                .subscribe(movies -> {
                    if (movies.isEmpty()) {
                        showEmpty();
                    } else {
                        showMovies(movies);
                    }
                }, this::onError)
        );
    }

    private void loadMoreMovies() {
        showProgress();
        compositeDisposable.add(presenter.getMoreMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (shimmerViewContainer.isShimmerStarted()) {
                        shimmerViewContainer.stopShimmer();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    hideProgress();
                })
                .subscribe(movies -> {
                    if (movies.isEmpty()) {
                        showEmpty();
                    } else {
                        showMovies(movies);
                    }
                }, this::onError)
        );
    }

    private void showProgress() {
        if (progressBar.getVisibility() == View.GONE && getActivity() != null) {
            progressBar.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void hideProgress() {
        if (progressBar.getVisibility() == View.VISIBLE && getActivity() != null) {
            progressBar.setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void showDetailsFragment(Integer movieId) {
        showProgress();
        hideKeyboard();
        compositeDisposable.add(
                presenter.getMovie(movieId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(this::hideProgress)
                        .subscribe(movie -> {
                                    hideProgress();
                                    movieSelectionListener.onMovieSelected(movie);
                                }, throwable -> onError(throwable)
                        )
        );
    }

    /**
     * Interface use to communicate with the activity when the user has selected an item in the list
     */
    public interface MovieSelectionListener {
        void onMovieSelected(Movie movie);
    }
}