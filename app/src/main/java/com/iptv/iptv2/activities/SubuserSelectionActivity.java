package com.iptv.iptv2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iptv.iptv2.R;
import com.iptv.iptv2.adapters.SubuserAdapter;
import com.iptv.iptv2.dao.SubuserDAO;
import com.iptv.iptv2.models.Subuser;

import java.util.ArrayList;
import java.util.List;

public class SubuserSelectionActivity extends AppCompatActivity {

    private static final String TAG = "SubuserSelectionActivity";
    private ListView subuserListView;
    private Button addSubuserButton;
    private SubuserDAO subuserDAO;
    private ArrayList<String> subuserNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subuser_selection);

        subuserListView = findViewById(R.id.subuserListView);
        addSubuserButton = findViewById(R.id.addSubuserButton);
        subuserDAO = SubuserDAO.getInstance(this);
        Log.i(TAG, "SubuserDAO initialized");

        loadSubusers();

        // Set up click listener for the ListView
        subuserListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSubuser = subuserNames.get(position);
            // Save the selected subuser to shared preferences or database
            saveSelectedSubuser(selectedSubuser);
            // Navigate to MainActivity
            Intent intent = new Intent(SubuserSelectionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Set up click listener for the add subuser button
        addSubuserButton.setOnClickListener(v -> {
            // Navigate to AddSubuserActivity
            Intent intent = new Intent(SubuserSelectionActivity.this, AddSubuserActivity.class);
            startActivity(intent);
        });
    }

    private void loadSubusers() {
        try {
            // Load subusers from the database
            List<Subuser> subusers = subuserDAO.getAllSubusers();
            subuserNames = new ArrayList<>();
            for (Subuser subuser : subusers) {
                subuserNames.add(subuser.getName());
            }

            // Log the loaded subusers
            Log.d(TAG, "Loaded subusers: " + subuserNames);

            // Create an adapter for the ListView
            SubuserAdapter adapter = new SubuserAdapter(this, subuserNames);
            subuserListView.setAdapter(adapter);

        } catch (Exception e) {
            Log.e(TAG, "Error loading subusers", e);
            Toast.makeText(this, "Error loading subusers", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSelectedSubuser(String subuser) {
        // Save the selected subuser to shared preferences or database
    }
}
