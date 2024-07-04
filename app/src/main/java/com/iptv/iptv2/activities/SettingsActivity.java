package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.iptv.iptv2.R;

public class SettingsActivity extends AppCompatActivity {
    private ImageButton BackButton;
    private Button AccountInfoButton;
    private Button UpdateLanguageButton;
    private Button DateTimeButton;
    private Button ClearCacheButton;
    private Button ClearSearchHistoryButton;
    //Add Button 7 whatever that might be:
    private Button LogoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BackButton = findViewById(R.id.BackButton);
        AccountInfoButton = findViewById(R.id.AccountInfoButton);
        UpdateLanguageButton = findViewById(R.id.UpdateLanguageButton);
        DateTimeButton = findViewById(R.id.DateTimeButton);
        ClearCacheButton = findViewById(R.id.ClearCacheButton);
        ClearSearchHistoryButton = findViewById(R.id.ClearSearchHistoryButton);
        LogoutButton = findViewById(R.id.LogoutButton);

        AccountInfoButton.setOnClickListener(view -> AccountInfo() );
        UpdateLanguageButton.setOnClickListener(view -> changeLanguage());
        DateTimeButton.setOnClickListener(view -> setDateTime());
        ClearCacheButton.setOnClickListener(view -> clearCache());
        ClearSearchHistoryButton.setOnClickListener(view -> ClearSearchHistory());
        BackButton.setOnClickListener(view -> finish());
        LogoutButton.setOnClickListener(view -> Logout());

    }
    private void AccountInfo(){
        //View information on the right of the buttons
    }
    private void changeLanguage(){
        //Be able to view current language and be able to change Languages
        //would have to put in the db

    }
    private void setDateTime(){
        //set Date Time on right
    }
    private void clearCache(){
        //Clear cache
    }
    private void ClearSearchHistory(){
        //Clear Search history
    }
    private void Logout(){
        //Logout completely

    }
}
