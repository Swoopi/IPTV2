package com.iptv.iptv2.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.iptv.iptv2.R;
import com.iptv.iptv2.dao.UserDao;
import com.iptv.iptv2.models.User;
import com.iptv.iptv2.utils.AppConstants;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton, signUpButton;
    private UserDao userDao;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        userDao = UserDao.getInstance(this);
        sharedPreferences = getSharedPreferences(AppConstants.PREFS_NAME, MODE_PRIVATE);

        // Check if the user is already logged in
        checkLoginState();

        // Focus on the username EditText when the activity starts
        usernameEditText.requestFocus();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                User user = userDao.getUser(username, password);

                if (user != null) {
                    Log.d("LoginActivity", "LOGIN SUCCESSFUL");

                    // Save login state and username to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(AppConstants.KEY_IS_LOGGED_IN, true);
                    editor.putString(AppConstants.KEY_USERNAME, username);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, SubuserSelectionActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Add hover effect
        addHoverEffect(loginButton);
        addHoverEffect(signUpButton);
    }

    private void checkLoginState() {
        boolean isLoggedIn = sharedPreferences.getBoolean(AppConstants.KEY_IS_LOGGED_IN, false);
        if (isLoggedIn) {
            // User is logged in, navigate to SubuserSelectionActivity
            Intent intent = new Intent(LoginActivity.this, SubuserSelectionActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void addHoverEffect(View view) {
        view.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });
    }
}
