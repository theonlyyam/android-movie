package com.ysdc.movie.ui.movielist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ysdc.movie.R;
import com.ysdc.movie.data.model.Movie;
import com.ysdc.movie.injection.module.GlideApp;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.ysdc.movie.utils.AppConstants.MOVIE_DB_DATE_FORMAT;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final OnMovieClickedListener listener;
    private final SimpleDateFormat dateFormat;
    private List<Movie> movies;

    public MoviesAdapter(List<Movie> movies, OnMovieClickedListener listener) {
        this.movies = movies;
        this.listener = listener;
        this.dateFormat = new SimpleDateFormat(MOVIE_DB_DATE_FORMAT, Locale.getDefault());
    }

    public void updateMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.cell_movie, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        if (!TextUtils.isEmpty(movie.getPoster())) {
            holder.updatePicture(movie.getPoster());
        } else {
            holder.updatePicture(null);
        }
        holder.title.setText(movie.getTitle());
        holder.releaseDate.setText(dateFormat.format(movie.getReleaseDate()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    interface OnMovieClickedListener {
        void onMovieClicked(Integer movieId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView title;
        private final TextView releaseDate;
        private WeakReference<OnMovieClickedListener> listenerRef;

        ViewHolder(View v, OnMovieClickedListener listener) {
            super(v);

            listenerRef = new WeakReference<>(listener);

            v.findViewById(R.id.cell_layout).setOnClickListener(v1 -> listenerRef.get().onMovieClicked(movies.get(getAdapterPosition()).getId()));
            img = v.findViewById(R.id.movie_img);
            title = v.findViewById(R.id.movie_title);
            releaseDate = v.findViewById(R.id.movie_release_date);
        }

        protected void updatePicture(String imageUrl) {
            GlideApp.with(img.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.movie_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);
        }
    }
}