package com.iptv.iptv2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.iptv.iptv2.R;
import com.iptv.iptv2.fragments.MainFragment;
import com.iptv.iptv2.dao.ChannelDao;
import com.iptv.iptv2.models.Channel;
import com.iptv.iptv2.utils.M3UFetcher;
import com.iptv.iptv2.utils.M3UParser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnMovies;
    private Button btnShows;
    private Button btnLiveTV;
    private Button btnUpdate;

    private MainFragment mainFragment;
    private ChannelDao channelDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        channelDao = new ChannelDao(this);

        btnMovies = findViewById(R.id.btn_movies);
        btnShows = findViewById(R.id.btn_shows);
        btnLiveTV = findViewById(R.id.btn_livetv);
        btnUpdate = findViewById(R.id.btn_update);

        btnMovies.setOnClickListener(view -> navigateToCategory("Movies"));
        btnShows.setOnClickListener(view -> navigateToCategory("Shows"));
        btnLiveTV.setOnClickListener(view -> navigateToCategory("Live TV"));
        btnUpdate.setOnClickListener(view -> updateContent());
    }

    private void navigateToCategory(String category) {
        Intent intent;
        switch (category) {
            case "Movies":
                intent = new Intent(MainActivity.this, MoviesActivity.class);
                break;
            case "Shows":
                intent = new Intent(MainActivity.this, ShowsActivity.class);
                break;
            case "Live TV":
                intent = new Intent(MainActivity.this, LiveTVActivity.class);
                break;
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
        startActivity(intent);
    }

    private void updateContent() {
        updateDatabase();
    }

    private void updateDatabase() {
        new Thread(() -> {
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

                runOnUiThread(() -> {
                    // Update UI or notify user that the update is complete
                    Toast.makeText(MainActivity.this, "Content updated successfully", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    // Notify user about the error
                    Toast.makeText(MainActivity.this, "Failed to update content", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}
