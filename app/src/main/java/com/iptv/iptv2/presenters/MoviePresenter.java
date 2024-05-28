package com.iptv.iptv2.presenters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.leanback.widget.Presenter;
import com.bumptech.glide.Glide;
import com.iptv.iptv2.R;
import com.iptv.iptv2.models.Movie;

public class MoviePresenter extends Presenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;
        View view = viewHolder.view;
        TextView title = view.findViewById(R.id.card_title);
        ImageView imageView = view.findViewById(R.id.card_image);

        title.setText(movie.getName());
        Glide.with(view.getContext())
                .load(movie.getTvgLogo())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(imageView);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        // No-op
    }
}
