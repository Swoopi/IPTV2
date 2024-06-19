package com.iptv.iptv2.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.iptv.iptv2.R;
import com.iptv.iptv2.dao.SubuserDAO;
import com.iptv.iptv2.models.Subuser;

public class AddSubuserActivity extends AppCompatActivity {

    private static final String TAG = "AddSubuserActivity";
    private EditText subuserNameEditText;
    private Button saveSubuserButton;
    private SubuserDAO subuserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subuser);

        subuserNameEditText = findViewById(R.id.subuserNameEditText);
        saveSubuserButton = findViewById(R.id.saveSubuserButton);
        subuserDAO = SubuserDAO.getInstance(this);

        saveSubuserButton.setOnClickListener(v -> {
            String subuserName = subuserNameEditText.getText().toString();
            if (!subuserName.isEmpty()) {
                // Save the new subuser to the database or shared preferences
                saveSubuser(subuserName);

                // Log the subuser addition
                Log.d(TAG, "Subuser added: " + subuserName);

                // Finish the activity and go back to SubuserSelectionActivity
                finish();
            }
        });
    }

    private void saveSubuser(String subuserName) {
        Subuser subuser = new Subuser(0, subuserName);
        subuserDAO.addSubuser(subuser);
    }
}
