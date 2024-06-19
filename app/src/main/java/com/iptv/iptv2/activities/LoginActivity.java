package com.iptv.iptv2.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
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
public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton, signUpButton;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        userDao = UserDao.getInstance(this);

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
