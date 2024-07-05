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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.iptv.iptv2.R;
import com.iptv.iptv2.activities.MainActivity;
import com.iptv.iptv2.models.Subuser;
import java.util.List;

public class SubuserAdapter extends RecyclerView.Adapter<SubuserAdapter.SubuserViewHolder> {

    private Context context;
    private List<Subuser> subusers;

    public SubuserAdapter(Context context, List<Subuser> subusers) {
        this.context = context;
        this.subusers = subusers;
    }

    @NonNull
    @Override
    public SubuserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subuser, parent, false);
        return new SubuserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubuserViewHolder holder, int position) {
        Subuser subuser = subusers.get(position);
        holder.subuserNameTextView.setText(subuser.getName());

        // Load image using Glide or set a default image
        Glide.with(context)
                .load(subuser.getImageUrl()) // Assuming your Subuser model has an imageUrl field
                .placeholder(R.drawable.default_avatar) // Default image
                .transform(new CircleCrop())
                .into(holder.subuserImageView);

        holder.itemView.setOnClickListener(v -> {
            // Handle subuser click
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("SUBUSER_NAME", subuser.getName());
            context.startActivity(intent);
        });

        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                holder.itemView.setBackgroundResource(R.drawable.circle_outline_selector);
            } else {
                holder.itemView.setBackgroundResource(0); // Remove the background
            }
        });
    }

    @Override
    public int getItemCount() {
        return subusers.size();
    }

    public static class SubuserViewHolder extends RecyclerView.ViewHolder {
        ImageView subuserImageView;
        TextView subuserNameTextView;

        public SubuserViewHolder(@NonNull View itemView) {
            super(itemView);
            subuserImageView = itemView.findViewById(R.id.subuserImageView);
            subuserNameTextView = itemView.findViewById(R.id.subuserNameTextView);
        }
    }
}
