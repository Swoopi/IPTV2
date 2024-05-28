package com.iptv.iptv2.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.core.content.ContextCompat;

import com.iptv.iptv2.R;
import com.iptv.iptv2.dao.MovieDao;
import com.iptv.iptv2.models.Movie;
import com.iptv.iptv2.presenters.MoviePresenter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MoviesFragment extends BrowseSupportFragment {

    private ExecutorService executorService;
    private MovieDao movieDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUIElements();

        executorService = Executors.newSingleThreadExecutor();
        movieDao = MovieDao.getInstance(getContext());
        displayMovies();
    }

    private void setUIElements() {
        setTitle(getString(R.string.browse_title));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(ContextCompat.getColor(getContext(), R.color.fastlane_background));
        setSearchAffordanceColor(ContextCompat.getColor(getContext(), R.color.search_opaque));
    }

    private void displayMovies() {
        executorService.submit(() -> {
            List<Movie> movies = movieDao.getAllMovies();

            ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
            MoviePresenter moviePresenter = new MoviePresenter();
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(moviePresenter);

            for (Movie movie : movies) {
                listRowAdapter.add(movie);
            }

            HeaderItem header = new HeaderItem(0, "Movies");
            rowsAdapter.add(new ListRow(header, listRowAdapter));
            new Handler(Looper.getMainLooper()).post(() -> setAdapter(rowsAdapter));
        });
    }
}
