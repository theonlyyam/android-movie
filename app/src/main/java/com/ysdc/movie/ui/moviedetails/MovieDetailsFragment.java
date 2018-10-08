package com.ysdc.movie.ui.moviedetails;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ysdc.movie.R;
import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.injection.module.GlideApp;
import com.ysdc.movie.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MovieDetailsFragment extends BaseFragment implements MovieDetailsMvpView {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    @Inject
    MovieDetailsMvpPresenter<MovieDetailsMvpView> presenter;

    @BindView(R.id.movie_img)
    protected ImageView poster;
    @BindView(R.id.movie_original_title)
    protected TextView originalTitle;
    @BindView(R.id.movie_release_date)
    protected TextView releaseDate;
    @BindView(R.id.movie_genre)
    protected TextView genre;
    @BindView(R.id.movie_vote)
    protected TextView vote;
    @BindView(R.id.movie_overview)
    protected TextView overview;

    public static MovieDetailsFragment newInstance(Movie movie) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MOVIE, movie);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        getFragmentComponent().inject(this);
        setUnBinder(ButterKnife.bind(this, view));

        Bundle arguments = getArguments();
        if (arguments != null) {
            presenter.setMovie(arguments.getParcelable(EXTRA_MOVIE));
        }
        presenter.onAttach(MovieDetailsFragment.this);

        getBaseActivity().getSupportActionBar().setTitle(presenter.getTitle());
        getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getBaseActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        GlideApp.with(getActivity())
                .load(presenter.getMovieImg())
                .placeholder(R.drawable.movie_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(poster);

        originalTitle.setText(presenter.getOriginalTitle());
        releaseDate.setText(presenter.getReleaseDate());
        genre.setText(presenter.getGenre());
        vote.setText(presenter.getVote());
        overview.setText(presenter.getOverview());
    }
}