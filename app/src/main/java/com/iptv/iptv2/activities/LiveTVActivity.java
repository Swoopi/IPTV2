package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.adapters.ChannelAdapter;
import com.iptv.iptv2.dao.ChannelDao;
import com.iptv.iptv2.models.Channel;
import java.util.List;

public class LiveTVActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView recyclerView;
    private ChannelDao channelDao;
    private ChannelAdapter channelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tv);

        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.live_tv_recycler_view);

        channelDao = ChannelDao.getInstance(this);
        List<Channel> channels = channelDao.getAllChannels();
        channelAdapter = new ChannelAdapter(channels);

        backButton.setOnClickListener(v -> finish());

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(channelAdapter);
    }
}
