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
import com.iptv.iptv2.dao.ShowDao;
import com.iptv.iptv2.models.Show;
import com.iptv.iptv2.presenters.ChannelPresenter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowsFragment extends BrowseSupportFragment {

    private ExecutorService executorService;
    private ShowDao showDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUIElements();

        executorService = Executors.newSingleThreadExecutor();
        showDao = ShowDao.getInstance(getContext());
        displayShows();
    }

    private void setUIElements() {
        setTitle(getString(R.string.browse_title));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(ContextCompat.getColor(getContext(), R.color.fastlane_background));
        setSearchAffordanceColor(ContextCompat.getColor(getContext(), R.color.search_opaque));
    }

    private void displayShows() {
        executorService.submit(() -> {
            List<Show> shows = showDao.getAllShows();

            ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
            ChannelPresenter channelPresenter = new ChannelPresenter();
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(channelPresenter);

            for (Show show : shows) {
                listRowAdapter.add(show);
            }

            HeaderItem header = new HeaderItem(0, "TV Shows");
            rowsAdapter.add(new ListRow(header, listRowAdapter));
            new Handler(Looper.getMainLooper()).post(() -> setAdapter(rowsAdapter));
        });
    }
}
