package com.iptv.iptv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieCategoryAdapter extends RecyclerView.Adapter<MovieCategoryAdapter.MovieCategoryViewHolder> {

    private Context context;
    private List<String> categories;
    private MovieAdapter movieAdapter;
    private List<Movie> allMovies;

    public MovieCategoryAdapter(Context context, List<String> categories, MovieAdapter movieAdapter) {
        this.context = context;
        this.categories = categories;
        this.movieAdapter = movieAdapter;
        this.allMovies = movieAdapter.getMovies();
    }

    @NonNull
    @Override
    public MovieCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new MovieCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCategoryViewHolder holder, int position) {
        String category = categories.get(position);
        holder.categoryTextView.setText(category);
        holder.itemView.setOnClickListener(v -> filterMoviesByCategory(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private void filterMoviesByCategory(String category) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : allMovies) {
            if (movie.getCategories().contains(category)) {
                filteredMovies.add(movie);
            }
        }
        movieAdapter.updateMovies(filteredMovies);
    }

    static class MovieCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;

        MovieCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.category_name);
            categoryTextView.setTextColor(itemView.getContext().getResources().getColor(android.R.color.white)); // Set text color to white
        }
    }
}
