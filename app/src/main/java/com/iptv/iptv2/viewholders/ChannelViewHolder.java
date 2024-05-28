package com.iptv.iptv2.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.widget.Presenter;

import com.iptv.iptv2.R;

public class ChannelViewHolder extends Presenter.ViewHolder {
    public TextView title;
    public ImageView imageView;

    public ChannelViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.title);
        imageView = view.findViewById(R.id.image);
    }
}
