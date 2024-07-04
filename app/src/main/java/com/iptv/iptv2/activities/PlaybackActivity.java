package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;
import com.iptv.iptv2.R;
import com.iptv.iptv2.fragments.PlaybackVideoFragment;

public class PlaybackActivity extends FragmentActivity {

    public static final String CHANNEL_URL = "CHANNEL_URL";
    public static final String CHANNEL_TITLE = "CHANNEL_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        String channelUrl = getIntent().getStringExtra(CHANNEL_URL);
        String channelTitle = getIntent().getStringExtra(CHANNEL_TITLE);

        Log.d("PlaybackActivity", "Channel URL: " + channelUrl);
        Log.d("PlaybackActivity", "Channel Title: " + channelTitle);

        if (savedInstanceState == null) {
            PlaybackVideoFragment fragment = new PlaybackVideoFragment();
            Bundle args = new Bundle();
            args.putString(CHANNEL_URL, channelUrl);
            args.putString(CHANNEL_TITLE, channelTitle);
            fragment.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }

        Button backButton = findViewById(R.id.btn_back);
        Button favoriteButton = findViewById(R.id.btn_favorite);

        backButton.setOnClickListener(v -> finish());

        favoriteButton.setOnClickListener(v -> {
            // Code to add the current channel to favorites
            // Implement your favorite functionality here
        });
    }
}
