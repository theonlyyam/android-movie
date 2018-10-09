package com.ysdc.movie.ui.movielist;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * This class is responsible to give to the user the feeling that we have an infinite list. It will in reality just retrieve the next elements (if there is
 * more) when we reach a define threshold (define in the class).
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 10;
    //The total number of items in the dataset after the last load
    private int mPreviousTotal = 0;
    //True if we are still waiting for the last set of data to load.
    private boolean mLoading = true;

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (totalItemCount == (visibleItemCount + firstVisibleItem) && mLoading) {
            endOfListReached();
        }

        if (mLoading && totalItemCount > mPreviousTotal) {
            mLoading = false;
            mPreviousTotal = totalItemCount;
        }
        if (!mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            loadMorelistContent();
            mLoading = true;
        }
    }

    public abstract void loadMorelistContent();

    public abstract void endOfListReached();

    public void reset() {
        mPreviousTotal = 0;
        mLoading = true;
    }
}