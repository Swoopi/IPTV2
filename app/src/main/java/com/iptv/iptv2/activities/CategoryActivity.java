package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.iptv.iptv2.R;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        TextView textView = findViewById(R.id.category_text);
        String category = getIntent().getStringExtra("CATEGORY");

        if (category != null) {
            textView.setText("Selected Category: " + category);
        }
    }
}
