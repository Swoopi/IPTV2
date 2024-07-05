package com.iptv.iptv2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.iptv.iptv2.R;
import com.iptv.iptv2.adapters.SubuserAdapter;
import com.iptv.iptv2.dao.SubuserDAO;
import com.iptv.iptv2.models.Subuser;
import com.iptv.iptv2.utils.AppConstants;
import java.util.List;

public class SubuserSelectionActivity extends AppCompatActivity {

    private static final String TAG = "SubuserSelectionActivity";
    private RecyclerView subuserRecyclerView;
    private Button addSubuserButton;
    private Button logoutButton;
    private SubuserDAO subuserDAO;
    private List<Subuser> subusers;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subuser_selection);

        subuserRecyclerView = findViewById(R.id.subuserRecyclerView);
        addSubuserButton = findViewById(R.id.addSubuserButton);
        logoutButton = findViewById(R.id.logoutButton);
        subuserDAO = SubuserDAO.getInstance(this);
        sharedPreferences = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);

        loadSubusers();

        addSubuserButton.setOnClickListener(v -> {
            Intent intent = new Intent(SubuserSelectionActivity.this, AddSubuserActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            // Clear login state
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(AppConstants.KEY_IS_LOGGED_IN, false);
            editor.remove(AppConstants.KEY_USERNAME);
            editor.apply();

            // Navigate back to LoginActivity
            Intent intent = new Intent(SubuserSelectionActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadSubusers() {
        try {
            subusers = subuserDAO.getAllSubusers();
            Log.d(TAG, "Loaded subusers: " + subusers);

            SubuserAdapter adapter = new SubuserAdapter(this, subusers);
            subuserRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            subuserRecyclerView.setAdapter(adapter);

        } catch (Exception e) {
            Log.e(TAG, "Error loading subusers", e);
            Toast.makeText(this, "Error loading subusers", Toast.LENGTH_SHORT).show();
        }
    }
}
