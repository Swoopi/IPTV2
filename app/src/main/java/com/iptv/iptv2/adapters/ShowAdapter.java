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
import com.iptv.iptv2.models.Show;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    private List<Show> showList;

    public ShowAdapter(List<Show> showList) {
        this.showList = showList;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        Show show = showList.get(position);
        holder.title.setText(show.getName());
        Glide.with(holder.imageView.getContext())
                .load(show.getTvgLogo())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public static class ShowViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            imageView = itemView.findViewById(R.id.card_image);
        }
    }
}
