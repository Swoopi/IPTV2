package com.iptv.iptv2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.iptv.iptv2.R;
import com.iptv.iptv2.activities.PlaybackActivity;
import com.iptv.iptv2.models.Movie;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.titleTextView.setText(movie.getName());

        if (movie.getTvgLogo() != null && !movie.getTvgLogo().isEmpty()) {
            Glide.with(context)
                    .load(movie.getTvgLogo())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(holder.logoImageView);
        } else {
            holder.logoImageView.setImageResource(R.drawable.error);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlaybackActivity.class);
            intent.putExtra(PlaybackActivity.CHANNEL_URL, movie.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void updateMovies(List<Movie> newMovies) {
        movies = newMovies;
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView logoImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.movie_name);
            logoImageView = itemView.findViewById(R.id.movie_logo);
        }
    }
}
