package com.iptv.iptv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.models.Channel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<String> categories;
    private ChannelAdapter channelAdapter;
    private List<Channel> allChannels;

    public CategoryAdapter(Context context, List<String> categories, ChannelAdapter channelAdapter) {
        this.context = context;
        this.categories = categories;
        this.channelAdapter = channelAdapter;
        this.allChannels = channelAdapter.getChannels();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = categories.get(position);
        holder.categoryTextView.setText(category);
        holder.itemView.setOnClickListener(v -> filterChannelsByCategory(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private void filterChannelsByCategory(String category) {
        List<Channel> filteredChannels = new ArrayList<>();
        for (Channel channel : allChannels) {
            if (channel.getCategories().contains(category)) {
                filteredChannels.add(channel);
            }
        }
        channelAdapter.updateChannels(filteredChannels);
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.category_name);
            categoryTextView.setTextColor(itemView.getContext().getResources().getColor(android.R.color.white)); // Set text color to white
        }
    }
}