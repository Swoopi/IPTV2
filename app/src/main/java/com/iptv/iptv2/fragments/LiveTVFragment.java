package com.iptv.iptv2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.core.content.ContextCompat;
import com.iptv.iptv2.R;
import com.iptv.iptv2.activities.PlaybackActivity;
import com.iptv.iptv2.dao.ChannelDao;
import com.iptv.iptv2.models.Channel;
import com.iptv.iptv2.presenters.ChannelPresenter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LiveTVFragment extends BrowseSupportFragment {

    private ExecutorService executorService;
    private ChannelDao channelDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUIElements();

        executorService = Executors.newSingleThreadExecutor();
        channelDao = ChannelDao.getInstance(getContext());
        displayChannels();
    }

    private void setUIElements() {
        setTitle(getString(R.string.browse_title));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(ContextCompat.getColor(getContext(), R.color.fastlane_background));
        setSearchAffordanceColor(ContextCompat.getColor(getContext(), R.color.search_opaque));
    }

    private void displayChannels() {
        executorService.submit(() -> {
            List<Channel> channels = channelDao.getAllChannels();
            Log.d("LiveTVFragment", "Channels loaded: " + channels.size());

            ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
            ChannelPresenter channelPresenter = new ChannelPresenter();

            // Set the item click listener
            channelPresenter.setOnItemClickListener(channel -> {
                Log.d("LiveTVFragment", "Item clicked: " + channel.getName());
                Intent intent = new Intent(getActivity(), PlaybackActivity.class);
                intent.putExtra(PlaybackActivity.CHANNEL_URL, channel.getUrl());
                intent.putExtra(PlaybackActivity.CHANNEL_TITLE, channel.getName());
                startActivity(intent);
            });

            Log.d("LiveTVFragment", "Setting adapter");
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(channelPresenter);

            for (Channel channel : channels) {
                listRowAdapter.add(channel);
                Log.d("LiveTVFragment", "Added channel: " + channel.getName());
            }

            HeaderItem header = new HeaderItem(0, "Live TV");
            rowsAdapter.add(new ListRow(header, listRowAdapter));
            new Handler(Looper.getMainLooper()).post(() -> setAdapter(rowsAdapter));
        });
    }
}
