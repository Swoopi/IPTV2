package com.iptv.iptv2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.iptv.iptv2.BuildConfig;
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
    private ImageButton btnMovies;
    private ImageButton btnShows;
    private ImageButton btnLiveTV;
    private ImageButton btnUpdate;
    private ImageButton profileButton;
    private ImageButton settingsButton;
    private ImageButton favoritesButton;
    private ExecutorService executorService;
    private ChannelDao channelDao;
    private MovieDao movieDao;
    private ShowDao showDao;
    private UpdateChecker updateChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMovies = findViewById(R.id.btn_movies);
        btnShows = findViewById(R.id.btn_shows);
        btnLiveTV = findViewById(R.id.btn_livetv);
        btnUpdate = findViewById(R.id.btn_update);
        profileButton = findViewById(R.id.profileButton);
        settingsButton = findViewById(R.id.settingsButton);
        favoritesButton = findViewById(R.id.btn_favorites);

        btnMovies.setOnClickListener(view -> navigateToCategory("Movies"));
        btnShows.setOnClickListener(view -> navigateToCategory("Shows"));
        btnLiveTV.setOnClickListener(view -> navigateToCategory("Live TV"));
        settingsButton.setOnClickListener(view -> settings());
        btnUpdate.setOnClickListener(view -> updateContent());
        profileButton.setOnClickListener(view -> viewProfile());

        setFocusChangeListeners();

        executorService = Executors.newSingleThreadExecutor();
        channelDao = ChannelDao.getInstance(this);
        movieDao = MovieDao.getInstance(this);
        showDao = ShowDao.getInstance(this);

        updateChecker = new UpdateChecker(this);
        updateChecker.checkForUpdate();

        btnLiveTV.requestFocus();

        // List all tables in the database
        channelDao.listTables();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        updateChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setFocusChangeListeners() {
        btnMovies.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                btnMovies.setImageResource(R.drawable.movies_icon_hover);
                adjustButtonMargins(R.id.btn_movies, true);
            } else {
                btnMovies.setImageResource(R.drawable.movies_icon);
                adjustButtonMargins(R.id.btn_movies, false);
            }
        });

        btnShows.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                btnShows.setImageResource(R.drawable.show_icon_hover);
                adjustButtonMargins(R.id.btn_shows, true);
            } else {
                btnShows.setImageResource(R.drawable.show_icon);
                adjustButtonMargins(R.id.btn_shows, false);
            }
        });

        btnLiveTV.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                btnLiveTV.setImageResource(R.drawable.livetv_icon_hover);
                adjustButtonMargins(R.id.btn_livetv, true);
            } else {
                btnLiveTV.setImageResource(R.drawable.livetv_icon);
                adjustButtonMargins(R.id.btn_livetv, false);
            }
        });

        profileButton.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                profileButton.setImageResource(R.drawable.profile_icon_hover);
            } else {
                profileButton.setImageResource(R.drawable.profile_icon);
            }
        });

        settingsButton.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                settingsButton.setImageResource(R.drawable.settings_icon_hover);
            } else {
                settingsButton.setImageResource(R.drawable.settings_icon);
            }
        });

        favoritesButton.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                favoritesButton.setImageResource(R.drawable.favorite_icon_hover);
            } else {
                favoritesButton.setImageResource(R.drawable.favorites_icon);
            }
        });

        btnUpdate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                btnUpdate.setImageResource(R.drawable.update_icon_hover);
            } else {
                btnUpdate.setImageResource(R.drawable.update_icon);
            }
        });
    }

    private void settings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void viewProfile() {
        Intent intent = new Intent(MainActivity.this, SubuserSelectionActivity.class);
        startActivity(intent);
    }

    private void adjustButtonMargins(int buttonId, boolean expand) {
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        int defaultMarginStart = 68;
        int defaultMarginEnd = 16;
        int expandedMargin = 48;

        // Reset all margins to default
        constraintSet.setMargin(R.id.btn_livetv, ConstraintSet.START, defaultMarginStart);
        constraintSet.setMargin(R.id.btn_movies, ConstraintSet.START, defaultMarginEnd);
        constraintSet.setMargin(R.id.btn_movies, ConstraintSet.END, defaultMarginEnd);
        constraintSet.setMargin(R.id.btn_shows, ConstraintSet.END, defaultMarginEnd);

        if (expand) {
            if (buttonId == R.id.btn_livetv) {
                constraintSet.setMargin(R.id.btn_livetv, ConstraintSet.START, expandedMargin);
                constraintSet.setMargin(R.id.btn_movies, ConstraintSet.START, expandedMargin + defaultMarginEnd);
            } else if (buttonId == R.id.btn_movies) {
                constraintSet.setMargin(R.id.btn_movies, ConstraintSet.START, expandedMargin);
                constraintSet.setMargin(R.id.btn_movies, ConstraintSet.END, expandedMargin);
                constraintSet.setMargin(R.id.btn_livetv, ConstraintSet.START, defaultMarginStart - expandedMargin);
            } else if (buttonId == R.id.btn_shows) {
                constraintSet.setMargin(R.id.btn_shows, ConstraintSet.END, expandedMargin);
                constraintSet.setMargin(R.id.btn_movies, ConstraintSet.END, expandedMargin + defaultMarginEnd);
            }
        }

        constraintSet.applyTo(layout);
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
                Log.i(TAG, "Starting Update");
                Log.i(TAG, "Clear previous databases");
                // Clear existing data
                channelDao.clearChannels();
                movieDao.clearMovies();
                showDao.clearShows();

                // Update Live TV
                Log.i(TAG, "Fetching Channels");
                String liveTvContent = M3UFetcher.fetchM3U(BuildConfig.LIVE_TV_M3U_URL);

                // Print the first few lines of the M3U content
                Log.i(TAG, "First few lines of M3U content:");
                String[] lines = liveTvContent.split("\n");
                for (int i = 0; i < Math.min(20, lines.length); i++) {
                    Log.i(TAG, lines[i]);
                }

                Log.i(TAG, "Parsing Channels");
                List<Channel> liveTvChannels = M3UParser.parseM3UForChannels(liveTvContent);
                Log.i(TAG, "Inserting Channels into database");
                for (Channel channel : liveTvChannels) {
                    channelDao.insertChannel(channel);
                }
                Log.i(TAG, "Updated channels");
                for (int i = 0; i < Math.min(5, liveTvChannels.size()); i++) {
                    Log.i(TAG, "Channel " + (i + 1) + ": " + liveTvChannels.get(i));
                }

                // Update Movies
                Log.i(TAG, "Fetching Movies");
                String moviesContent = M3UFetcher.fetchM3U(BuildConfig.MOVIES_M3U_URL);
                Log.i(TAG, "Raw Movies M3U content:");
                Log.i(TAG, moviesContent);

                // Print the first few lines of the M3U content
                Log.i(TAG, "First few lines of Movies M3U content:");
                lines = moviesContent.split("\n");
                for (int i = 0; i < Math.min(20, lines.length); i++) {
                    Log.i(TAG, lines[i]);
                }

                Log.i(TAG, "Parsing Movies");
                List<Movie> movieChannels = M3UParser.parseM3UForMovies(moviesContent);
                Log.i(TAG, "Inserting Movies into database");
                for (Movie movie : movieChannels) {
                    movieDao.insertMovie(movie);
                }
                Log.i(TAG, "Updated Movies");

                // Update Shows
                Log.i(TAG, "Fetching Shows");
                String showsContent = M3UFetcher.fetchM3U(BuildConfig.SHOWS_M3U_URL);

                // Print the first few lines of the M3U content
                Log.i(TAG, "First few lines of Shows M3U content:");
                lines = showsContent.split("\n");
                for (int i = 0; i < Math.min(20, lines.length); i++) {
                    Log.i(TAG, lines[i]);
                }

                Log.i(TAG, "Parsing Shows");
                List<Show> showChannels = M3UParser.parseM3UForShows(showsContent);
                Log.i(TAG, "Inserting Shows into database");
                for (Show show : showChannels) {
                    showDao.insertShow(show);
                }
                Log.i(TAG, "Updated shows");

                Log.i(TAG, "Update complete");

            } catch (Exception e) {
                Log.e(TAG, "Error during update", e);
            }
        });
    }
}
