package com.iptv.iptv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.models.Channel;
import java.util.List;
import java.util.Map;

public class ChannelGroupAdapter extends RecyclerView.Adapter<ChannelGroupAdapter.CategoryViewHolder> {

    private Context context;
    private Map<String, List<Channel>> channelMap;
    private List<String> categoryOrder;

    public ChannelGroupAdapter(Context context, Map<String, List<Channel>> channelMap, List<String> categoryOrder) {
        this.context = context;
        this.channelMap = channelMap;
        this.categoryOrder = categoryOrder;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_channel_group, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = categoryOrder.get(position);
        holder.categoryTextView.setText(category);

        ChannelAdapter channelAdapter = new ChannelAdapter(context, channelMap.get(category));
        holder.channelsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.channelsRecyclerView.setAdapter(channelAdapter);
    }

    @Override
    public int getItemCount() {
        return categoryOrder.size();
    }

    public void updateChannels(Map<String, List<Channel>> newChannelMap) {
        channelMap = newChannelMap;
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        RecyclerView channelsRecyclerView;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.category_name);
            channelsRecyclerView = itemView.findViewById(R.id.channels_recycler_view);
        }
    }
}
