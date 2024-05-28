package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.adapters.ShowAdapter;
import com.iptv.iptv2.dao.ShowDao;
import com.iptv.iptv2.models.Show;
import java.util.List;

public class ShowsActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView recyclerView;
    private ShowDao showDao;
    private ShowAdapter showAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);

        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.shows_recycler_view);

        showDao = ShowDao.getInstance(this);
        List<Show> shows = showDao.getAllShows();
        showAdapter = new ShowAdapter(shows);

        backButton.setOnClickListener(v -> finish());

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(showAdapter);
    }
}
