package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;
import com.iptv.iptv2.R;
import com.iptv.iptv2.fragments.PlaybackVideoFragment;

public class PlaybackActivity extends FragmentActivity {

    public static final String CHANNEL_URL = "CHANNEL_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new PlaybackVideoFragment())
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
