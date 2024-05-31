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
import com.iptv.iptv2.models.Show;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    private Context context;
    private List<Show> shows;

    public ShowAdapter(Context context, List<Show> shows) {
        this.context = context;
        this.shows = shows;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_item, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        Show show = shows.get(position);
        holder.titleTextView.setText(show.getName());

        if (show.getTvgLogo() != null && !show.getTvgLogo().isEmpty()) {
            Glide.with(context)
                    .load(show.getTvgLogo())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(holder.logoImageView);
        } else {
            holder.logoImageView.setImageResource(R.drawable.error);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlaybackActivity.class);
            intent.putExtra(PlaybackActivity.CHANNEL_URL, show.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public static class ShowViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView logoImageView;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.show_name);
            logoImageView = itemView.findViewById(R.id.show_logo);
        }
    }
}
