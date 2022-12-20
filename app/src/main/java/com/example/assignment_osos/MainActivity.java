package com.example.assignment_osos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final static String ID = "123";
    private final static String PASS = "123";
    private static final String MESSAGE_ID = "message_prefs";
    EditText user_name;
    EditText password;
    Button login_button;

    protected Boolean isValid() {
        return (user_name.getText().toString().equals(ID) && password.getText().toString().equals(PASS));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeCall", "YES");

        if (FirstTime.equals("NO")) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }

        user_name = findViewById(R.id.user_name_text_view);
        password = findViewById(R.id.password_text_view);
        login_button = findViewById(R.id.button);


        login_button.setOnClickListener(view -> {
            if (isValid()) {
                Log.d("XYZ", "okk");
                preferences.edit().putString("FirstTimeCall", "NO").apply();
                Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}