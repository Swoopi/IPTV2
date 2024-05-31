package com.iptv.iptv2.activities;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.iptv.iptv2.fragments.PlaybackVideoFragment;

public class PlaybackActivity extends FragmentActivity {

    public static final String CHANNEL_URL = "CHANNEL_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new PlaybackVideoFragment())
                    .commit();
        }
    }
}
