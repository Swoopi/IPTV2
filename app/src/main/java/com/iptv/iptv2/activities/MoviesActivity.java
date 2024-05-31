package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.adapters.MovieAdapter;
import com.iptv.iptv2.dao.MovieDao;
import com.iptv.iptv2.models.Movie;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView recyclerView;
    private MovieDao movieDao;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.movies_recycler_view);

        movieDao = MovieDao.getInstance(this);
        List<Movie> movies = movieDao.getAllMovies();
        movieAdapter = new MovieAdapter(this, movies);

        backButton.setOnClickListener(v -> finish());

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(movieAdapter);
    }
}
