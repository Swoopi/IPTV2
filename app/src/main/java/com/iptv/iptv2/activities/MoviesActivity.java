package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.adapters.MovieAdapter;
import com.iptv.iptv2.adapters.MovieCategoryAdapter;
import com.iptv.iptv2.dao.MovieDao;
import com.iptv.iptv2.models.Movie;
import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText searchEditText;
    private RecyclerView moviesRecyclerView;
    private RecyclerView categoriesRecyclerView;
    private MovieDao movieDao;
    private MovieAdapter movieAdapter;
    private MovieCategoryAdapter movieCategoryAdapter;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        backButton = findViewById(R.id.backButton);
        searchEditText = findViewById(R.id.searchEditText);
        moviesRecyclerView = findViewById(R.id.movies_recycler_view);
        categoriesRecyclerView = findViewById(R.id.categories_recycler_view);

        movieDao = MovieDao.getInstance(this);
        movies = movieDao.getAllMovies();
        movieAdapter = new MovieAdapter(this, movies);

        List<String> categories = getCategoriesFromMovies(movies);
        movieCategoryAdapter = new MovieCategoryAdapter(this, categories, movieAdapter);

        backButton.setOnClickListener(v -> finish());

        setupRecyclerView();
        setupSearch();
    }

    private List<String> getCategoriesFromMovies(List<Movie> movies) {
        List<String> categories = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getCategories() != null) {
                for (String category : movie.getCategories()) {
                    if (!categories.contains(category)) {
                        categories.add(category);
                    }
                }
            }
        }
        return categories;
    }

    private void setupRecyclerView() {
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        moviesRecyclerView.setAdapter(movieAdapter);

        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        categoriesRecyclerView.setAdapter(movieCategoryAdapter);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter movies as the user types
                filterMovies(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed after text is changed
            }
        });
    }

    private void filterMovies(String query) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }
        movieAdapter.updateMovies(filteredMovies);
    }
}
