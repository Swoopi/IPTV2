package com.iptv.iptv2.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.core.content.ContextCompat;

import com.iptv.iptv2.R;
import com.iptv.iptv2.models.Channel;
import com.iptv.iptv2.presenters.CardPresenter;
import com.iptv.iptv2.utils.M3UFetcher;
import com.iptv.iptv2.utils.M3UParser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFragment extends BrowseSupportFragment {

    private ExecutorService executorService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUIElements();
        executorService = Executors.newSingleThreadExecutor();
        fetchAndParseM3UData("https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/livetv");
    }

    private void setUIElements() {
        setTitle(getString(R.string.browse_title));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(ContextCompat.getColor(getContext(), R.color.fastlane_background));
        setSearchAffordanceColor(ContextCompat.getColor(getContext(), R.color.search_opaque));
    }

    private void fetchAndParseM3UData(String url) {
        executorService.submit(() -> {
            try {
                String m3uContent = M3UFetcher.fetchM3U(url);
                List<Channel> channels = M3UParser.parseM3U(m3uContent);

                ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
                CardPresenter cardPresenter = new CardPresenter();
                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);

                for (Channel channel : channels) {
                    listRowAdapter.add(channel);
                }

                HeaderItem header = new HeaderItem(0, "Live TV");
                rowsAdapter.add(new ListRow(header, listRowAdapter));
                setAdapter(rowsAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
