package com.iptv.iptv2.presenters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.widget.Presenter;
import com.bumptech.glide.Glide;
import com.iptv.iptv2.R;
import com.iptv.iptv2.models.Channel;

public class ChannelPresenter extends Presenter {

    private OnItemClickListener onItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Channel channel = (Channel) item;
        ChannelViewHolder channelViewHolder = (ChannelViewHolder) viewHolder;

        String channelName = channel.getName();
        channelViewHolder.title.setText(channelName);

        // Log the channel name to verify it's not null
        Log.d("ChannelPresenter", "Channel Name: " + channelName);

        Glide.with(channelViewHolder.view.getContext())
                .load(channel.getTvgLogo())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(channelViewHolder.imageView);

        channelViewHolder.view.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                Log.d("ChannelPresenter", "Item clicked: " + channel.getName());
                onItemClickListener.onItemClick(channel);
            } else {
                Log.d("ChannelPresenter", "OnItemClickListener is null");
            }
        });
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        // No-op
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
        Log.d("ChannelPresenter", "OnItemClickListener set");
    }

    public interface OnItemClickListener {
        void onItemClick(Channel channel);
    }

    public static class ChannelViewHolder extends ViewHolder {
        View view;
        TextView title;
        ImageView imageView;

        public ChannelViewHolder(View view) {
            super(view);
            this.view = view;
            title = view.findViewById(R.id.card_title);
            imageView = view.findViewById(R.id.card_image);
        }
    }
}
