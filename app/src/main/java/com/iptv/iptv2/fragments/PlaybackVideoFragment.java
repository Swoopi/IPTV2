package com.iptv.iptv2.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.MediaPlayerAdapter;
import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.widget.PlaybackControlsRow;
import com.iptv.iptv2.activities.PlaybackActivity;

public class PlaybackVideoFragment extends VideoSupportFragment {

    private PlaybackTransportControlGlue<MediaPlayerAdapter> mTransportControlGlue;
    private String channelUrl;
    private String channelTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            channelUrl = getArguments().getString(PlaybackActivity.CHANNEL_URL);
            channelTitle = getArguments().getString(PlaybackActivity.CHANNEL_TITLE);
        }

        VideoSupportFragmentGlueHost glueHost = new VideoSupportFragmentGlueHost(this);

        MediaPlayerAdapter playerAdapter = new MediaPlayerAdapter(getContext());
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE);

        mTransportControlGlue = new PlaybackTransportControlGlue<>(getContext(), playerAdapter);
        mTransportControlGlue.setHost(glueHost);
        mTransportControlGlue.setTitle(channelTitle);
        mTransportControlGlue.playWhenPrepared();

        if (channelUrl != null) {
            playerAdapter.setDataSource(Uri.parse(channelUrl));
        } else {
            // Handle the case where the URL is not available
            // Show an error message or perform an appropriate action
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTransportControlGlue != null) {
            mTransportControlGlue.pause();
        }
    }
}
