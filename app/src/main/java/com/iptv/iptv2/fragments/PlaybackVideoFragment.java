package com.iptv.iptv2.fragments;

import android.net.Uri;
import android.os.Bundle;
import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.MediaPlayerAdapter;
import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.widget.PlaybackControlsRow;

import com.iptv.iptv2.activities.PlaybackActivity;

public class PlaybackVideoFragment extends VideoSupportFragment {

    private PlaybackTransportControlGlue<MediaPlayerAdapter> mTransportControlGlue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String channelUrl = getActivity().getIntent().getStringExtra(PlaybackActivity.CHANNEL_URL);

        VideoSupportFragmentGlueHost glueHost = new VideoSupportFragmentGlueHost(this);

        MediaPlayerAdapter playerAdapter = new MediaPlayerAdapter(getContext());
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE);

        mTransportControlGlue = new PlaybackTransportControlGlue<>(getContext(), playerAdapter);
        mTransportControlGlue.setHost(glueHost);
        mTransportControlGlue.setTitle("Live Stream");
        mTransportControlGlue.setSubtitle("Streaming live content");
        mTransportControlGlue.playWhenPrepared();
        playerAdapter.setDataSource(Uri.parse(channelUrl));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTransportControlGlue != null) {
            mTransportControlGlue.pause();
        }
    }
}
