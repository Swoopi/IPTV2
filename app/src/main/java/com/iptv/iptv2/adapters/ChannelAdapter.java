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
import com.iptv.iptv2.models.Channel;
import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    private Context context;
    private List<Channel> channels;

    public ChannelAdapter(Context context, List<Channel> channels) {
        this.context = context;
        this.channels = channels;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.channel_item, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Channel channel = channels.get(position);
        holder.titleTextView.setText(channel.getName());

        if (channel.getTvgLogo() != null && !channel.getTvgLogo().isEmpty()) {
            Glide.with(context)
                    .load(channel.getTvgLogo())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(holder.logoImageView);
        } else {
            holder.logoImageView.setImageResource(R.drawable.error); // Set a default image when the URL is null
        }

        holder.itemView.setOnClickListener(v -> {
            // Handle the click event, e.g., start PlaybackActivity with the channel URL
            Intent intent = new Intent(context, PlaybackActivity.class);
            intent.putExtra("CHANNEL_URL", channel.getUrl());
            context.startActivity(intent);
        });

        // Add focus change listener to change background on focus
        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                holder.itemView.setBackgroundResource(R.drawable.channel_item_hover);
                // Ensure the focus remains within the same row
                if (position == channels.size() - 1) {
                    holder.itemView.setNextFocusRightId(holder.itemView.getId());
                }
            } else {
                holder.itemView.setBackgroundResource(R.drawable.channel_item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public void updateChannels(List<Channel> newChannels) {
        channels = newChannels;
        notifyDataSetChanged();
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView logoImageView;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.channel_name);
            logoImageView = itemView.findViewById(R.id.channel_logo);
        }
    }
}
