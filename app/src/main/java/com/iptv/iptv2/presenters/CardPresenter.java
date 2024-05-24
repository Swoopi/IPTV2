package com.iptv.iptv2.presenters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.iptv.iptv2.R;
import com.iptv.iptv2.models.Channel;

public class CardPresenter extends Presenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Channel channel = (Channel) item;
        ImageView imageView = viewHolder.view.findViewById(R.id.card_image);
        TextView textView = viewHolder.view.findViewById(R.id.card_text);

        textView.setText(channel.getName());
        Glide.with(viewHolder.view.getContext())
                .load(channel.getTvgLogo())
                .error(ContextCompat.getDrawable(viewHolder.view.getContext(), R.drawable.default_background))
                .into(imageView);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        // Clean up resources
    }
}
