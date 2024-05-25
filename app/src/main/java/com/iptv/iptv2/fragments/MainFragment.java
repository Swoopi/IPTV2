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
import com.iptv.iptv2.dao.ChannelDao;
import com.iptv.iptv2.models.Channel;
import com.iptv.iptv2.presenters.CardPresenter;
import com.iptv.iptv2.utils.M3UFetcher;
import com.iptv.iptv2.utils.M3UParser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFragment extends BrowseSupportFragment {

    private ExecutorService executorService;
    private ChannelDao channelDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUIElements();

        executorService = Executors.newSingleThreadExecutor();
        channelDao = new ChannelDao(getContext());
        updateDatabase();
    }

    private void setUIElements() {
        setTitle(getString(R.string.browse_title));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(ContextCompat.getColor(getContext(), R.color.fastlane_background));
        setSearchAffordanceColor(ContextCompat.getColor(getContext(), R.color.search_opaque));
    }

    public void updateDatabase() {
        executorService.submit(() -> {
            try {
                // Clear existing data
                channelDao.clearChannels();

                // Update Live TV
                String liveTvContent = M3UFetcher.fetchM3U("https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/livetv");
                List<Channel> liveTvChannels = M3UParser.parseM3U(liveTvContent);
                for (Channel channel : liveTvChannels) {
                    channelDao.insertChannel(channel);
                }

                // Update Movies
                String moviesContent = M3UFetcher.fetchM3U("https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/movies");
                List<Channel> movieChannels = M3UParser.parseM3U(moviesContent);
                for (Channel channel : movieChannels) {
                    channelDao.insertChannel(channel);
                }

                // Update Shows
                String showsContent = M3UFetcher.fetchM3U("https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/tvshows");
                List<Channel> showChannels = M3UParser.parseM3U(showsContent);
                for (Channel channel : showChannels) {
                    channelDao.insertChannel(channel);
                }

                new Handler(Looper.getMainLooper()).post(this::displayChannels);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void displayChannels() {
        List<Channel> channels = channelDao.getAllChannels();

        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);

        for (Channel channel : channels) {
            listRowAdapter.add(channel);
        }

        HeaderItem header = new HeaderItem(0, "Live TV");
        rowsAdapter.add(new ListRow(header, listRowAdapter));
        setAdapter(rowsAdapter);
    }
}
