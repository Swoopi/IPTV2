package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iptv.iptv2.R;
import com.iptv.iptv2.adapters.ChannelAdapter;
import com.iptv.iptv2.dao.ChannelDao;
import com.iptv.iptv2.models.Channel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LiveTVActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView liveTvRecyclerView;
    private ChannelDao channelDao;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tv);

        backButton = findViewById(R.id.backButton);
        liveTvRecyclerView = findViewById(R.id.live_tv_recycler_view);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        channelDao = ChannelDao.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        liveTvRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loadLiveTvChannels();
    }

    private void loadLiveTvChannels() {
        executorService.submit(() -> {
            List<Channel> channels = channelDao.getAllChannels();
            runOnUiThread(() -> {
                ChannelAdapter adapter = new ChannelAdapter(channels);
                liveTvRecyclerView.setAdapter(adapter);
            });
        });
    }
}
