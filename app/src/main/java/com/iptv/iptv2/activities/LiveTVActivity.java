package com.iptv.iptv2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.adapters.ChannelGroupAdapter;
import com.iptv.iptv2.dao.ChannelDao;
import com.iptv.iptv2.models.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveTVActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText searchEditText;
    private Button movieButton;
    private Button showsButton;
    private RecyclerView liveTvRecyclerView;
    private ChannelDao channelDao;
    private ChannelGroupAdapter channelGroupAdapter;
    private List<Channel> channels;
    private List<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tv);

        backButton = findViewById(R.id.backButton);
        searchEditText = findViewById(R.id.searchEditText);
        liveTvRecyclerView = findViewById(R.id.live_tv_recycler_view);
        movieButton = findViewById(R.id.movieButton);
        showsButton = findViewById(R.id.showsButton);

        channelDao = ChannelDao.getInstance(this);
        channels = channelDao.getAllChannels();
        categories = getCategoriesFromChannels(channels);

        channelGroupAdapter = new ChannelGroupAdapter(this, groupChannelsByCategory(channels), categories);

        backButton.setOnClickListener(v -> finish());

        backButton.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                backButton.setImageResource(R.drawable.back_icon_hover);
            } else {
                backButton.setImageResource(R.drawable.back_icon);
            }
        });

        movieButton.setOnClickListener(v -> {
            Intent intent = new Intent(LiveTVActivity.this, MoviesActivity.class);
            startActivity(intent);
        });

        movieButton.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                movieButton.animate();
            } else {
                // No action needed
            }
        });

        showsButton.setOnClickListener(v -> {
            Intent intent = new Intent(LiveTVActivity.this, ShowsActivity.class);
            startActivity(intent);
        });

        setupRecyclerView();
        setupSearch();
    }

    private List<String> getCategoriesFromChannels(List<Channel> channels) {
        List<String> categories = new ArrayList<>();
        for (Channel channel : channels) {
            if (channel.getCategories() != null) {
                for (String category : channel.getCategories()) {
                    if (!categories.contains(category)) {
                        categories.add(category);
                    }
                }
            }
        }
        return categories;
    }

    private Map<String, List<Channel>> groupChannelsByCategory(List<Channel> channels) {
        Map<String, List<Channel>> channelMap = new HashMap<>();
        for (Channel channel : channels) {
            if (channel.getCategories() != null) {
                for (String category : channel.getCategories()) {
                    if (!channelMap.containsKey(category)) {
                        channelMap.put(category, new ArrayList<>());
                    }
                    channelMap.get(category).add(channel);
                }
            }
        }
        // Ensure every category has a non-null list
        for (String category : categories) {
            if (!channelMap.containsKey(category)) {
                channelMap.put(category, new ArrayList<>());
            }
        }
        return channelMap;
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        liveTvRecyclerView.setLayoutManager(layoutManager);
        liveTvRecyclerView.setAdapter(channelGroupAdapter);

        liveTvRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { // check if scrolling down
                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                    recyclerView.findViewHolderForAdapterPosition(lastVisiblePosition).itemView.requestFocus();
                }
            }
        });
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter channels as the user types
                if (charSequence.length() > 3) {
                    filterChannels(charSequence.toString());
                } else {
                    // If the query is less than 4 characters, show the original list
                    channelGroupAdapter.updateChannels(groupChannelsByCategory(channels));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed after text is changed
            }
        });
    }

    private void filterChannels(String query) {
        List<Channel> filteredChannels = new ArrayList<>();
        for (Channel channel : channels) {
            if (channel.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredChannels.add(channel);
            }
        }
        channelGroupAdapter.updateChannels(groupChannelsByCategory(filteredChannels));
    }
}
