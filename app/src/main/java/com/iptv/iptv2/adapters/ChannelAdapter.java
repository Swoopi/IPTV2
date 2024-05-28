package com.iptv.iptv2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.iptv.iptv2.R;
import com.iptv.iptv2.models.Channel;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    private List<Channel> channelList;

    public ChannelAdapter(List<Channel> channelList) {
        this.channelList = channelList;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Channel channel = channelList.get(position);
        holder.title.setText(channel.getName());
        Glide.with(holder.imageView.getContext())
                .load(channel.getTvgLogo())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            imageView = itemView.findViewById(R.id.card_image);
        }
    }
}
