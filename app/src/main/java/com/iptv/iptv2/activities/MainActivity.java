package com.iptv.iptv2.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.iptv.iptv2.R;
import com.iptv.iptv2.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment())
                    .commitNow();
        }
    }
}
