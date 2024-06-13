package com.iptv.iptv2.activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.adapters.CategoryAdapter;
import com.iptv.iptv2.adapters.ChannelAdapter;
import com.iptv.iptv2.dao.ChannelDao;
import com.iptv.iptv2.models.Channel;
import java.util.ArrayList;
import java.util.List;

public class LiveTVActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText searchEditText;
    private Button movieButton;
    private Button showsButton;
    private RecyclerView liveTvRecyclerView;
    private RecyclerView categoriesRecyclerView;
    private ChannelDao channelDao;
    private ChannelAdapter channelAdapter;
    private CategoryAdapter categoryAdapter;
    private List<Channel> channels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tv);

        backButton = findViewById(R.id.backButton);
        searchEditText = findViewById(R.id.searchEditText);
        liveTvRecyclerView = findViewById(R.id.live_tv_recycler_view);
        categoriesRecyclerView = findViewById(R.id.categories_recycler_view);
        movieButton = findViewById(R.id.movieButton);
        showsButton = findViewById(R.id.showsButton);

        channelDao = ChannelDao.getInstance(this);
        channels = channelDao.getAllChannels();
        channelAdapter = new ChannelAdapter(this, channels);

        List<String> categories = getCategoriesFromChannels(channels);
        categoryAdapter = new CategoryAdapter(this, categories, channelAdapter);

        backButton.setOnClickListener(v -> finish());

        movieButton.setOnClickListener(v -> {
            Intent intent = new Intent(LiveTVActivity.this, MoviesActivity.class);
            startActivity(intent);
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

    private void setupRecyclerView() {
        liveTvRecyclerView.setLayoutManager(new GridLayoutManager(this, 6)); // 6 columns
        liveTvRecyclerView.setAdapter(channelAdapter);

        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        categoriesRecyclerView.setAdapter(categoryAdapter);
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
                filterChannels(charSequence.toString());
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
        channelAdapter.updateChannels(filteredChannels);
    }
}
