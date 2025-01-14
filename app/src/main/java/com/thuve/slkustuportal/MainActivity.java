package com.thuve.slkustuportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button editProfile = findViewById(R.id.btneditProfile);

        editProfile.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, EditProfile.class);
                startActivity(intent);
            });

    }
}
