package com.iptv.iptv2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.iptv.iptv2.R;
import com.iptv.iptv2.dao.ChannelDao;
import com.iptv.iptv2.dao.MovieDao;
import com.iptv.iptv2.dao.ShowDao;
import com.iptv.iptv2.models.Channel;
import com.iptv.iptv2.models.Movie;
import com.iptv.iptv2.models.Show;
import com.iptv.iptv2.utils.M3UFetcher;
import com.iptv.iptv2.utils.M3UParser;
import com.iptv.iptv2.utils.UpdateChecker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnMovies;
    private Button btnShows;
    private Button btnLiveTV;
    private Button btnUpdate;
    private ExecutorService executorService;
    private ChannelDao channelDao;
    private MovieDao movieDao;
    private ShowDao showDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMovies = findViewById(R.id.btn_movies);
        btnShows = findViewById(R.id.btn_shows);
        btnLiveTV = findViewById(R.id.btn_livetv);
        btnUpdate = findViewById(R.id.btn_update);

        btnMovies.setOnClickListener(view -> navigateToCategory("Movies"));
        btnShows.setOnClickListener(view -> navigateToCategory("Shows"));
        btnLiveTV.setOnClickListener(view -> navigateToCategory("Live TV"));
        btnUpdate.setOnClickListener(view -> updateContent());

        executorService = Executors.newSingleThreadExecutor();
        channelDao = ChannelDao.getInstance(this);
        movieDao = MovieDao.getInstance(this);
        showDao = ShowDao.getInstance(this);

        UpdateChecker updateChecker = new UpdateChecker(this);

        // List all tables in the database
        channelDao.listTables();
        //movieDao.listTables();
        //showDao.listTables();
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
        executorService.submit(() -> {
            try {
                Log.d(TAG, "Starting Update");
                Log.d(TAG, "Clear previous databases");
                // Clear existing data
                channelDao.clearChannels();
                movieDao.clearMovies();
                showDao.clearShows();

                // Update Live TV
                Log.d(TAG, "Fetching Channels");
                String liveTvContent = M3UFetcher.fetchM3U("https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/livetv");


                // Print the first few lines of the M3U content
                Log.d(TAG, "First few lines of M3U content:");
                String[] lines = liveTvContent.split("\n");
                for (int i = 0; i < Math.min(20, lines.length); i++) {
                    Log.d(TAG, lines[i]);
                }

                Log.d(TAG, "Parsing Channels");
                List<Channel> liveTvChannels = M3UParser.parseM3UForChannels(liveTvContent);
                Log.d(TAG, "Inserting Channels into database");
                for (Channel channel : liveTvChannels) {
                    channelDao.insertChannel(channel);
                }
                Log.d(TAG, "Updated channels");
                for (int i = 0; i < Math.min(5, liveTvChannels.size()); i++) {
                    Log.d(TAG, "Channel " + (i + 1) + ": " + liveTvChannels.get(i));
                }

                // Update Movies
                Log.d(TAG, "Fetching Movies");
                String moviesContent = M3UFetcher.fetchM3U("https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/movies");

                // Print the first few lines of the M3U content
                Log.d(TAG, "First few lines of Movies M3U content:");
                lines = moviesContent.split("\n");
                for (int i = 0; i < Math.min(20, lines.length); i++) {
                    Log.d(TAG, lines[i]);
                }

                Log.d(TAG, "Parsing Movies");
                List<Movie> movieChannels = M3UParser.parseM3UForMovies(moviesContent);
                Log.d(TAG, "Inserting Movies into database");
                for (Movie movie : movieChannels) {
                    movieDao.insertMovie(movie);
                }
                Log.d(TAG, "Updated Movies");

                // Update Shows
                Log.d(TAG, "Fetching Shows");
                String showsContent = M3UFetcher.fetchM3U("https://tvnow.best/api/list/couch0723@gmail.com/67745443/m3u8/tvshows");

                // Print the first few lines of the M3U content
                Log.d(TAG, "First few lines of Shows M3U content:");
                lines = showsContent.split("\n");
                for (int i = 0; i < Math.min(20, lines.length); i++) {
                    Log.d(TAG, lines[i]);
                }

                Log.d(TAG, "Parsing Shows");
                List<Show> showChannels = M3UParser.parseM3UForShows(showsContent);
                Log.d(TAG, "Inserting Shows into database");
                for (Show show : showChannels) {
                    showDao.insertShow(show);
                }
                Log.d(TAG, "Updated shows");

                Log.d(TAG, "Update complete");

            } catch (Exception e) {
                Log.e(TAG, "Error during update", e);
            }
        });
    }
}
