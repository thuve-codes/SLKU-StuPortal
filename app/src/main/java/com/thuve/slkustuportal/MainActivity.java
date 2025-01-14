package com.thuve.slkustuportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button editProfile = findViewById(R.id.btneditProfile);

        welcomeMessage=findViewById(R.id.welcomeMessage);

        // Get data from Intent
        String sid = getIntent().getStringExtra("sid");
        if (sid != null) {
            welcomeMessage.setText("Welcome! " + sid);
            //fetchData(sid); // Fetch additional data from Firebase
        } else {
            welcomeMessage.setText("Student ID not available");
        }


        editProfile.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, EditProfile.class);
                intent.putExtra("sid", sid);
                startActivity(intent);
                finish();
            });

    }

    /*
    private void fetchData(String sid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
        Query checkUserDb = reference.orderByChild("sid").equalTo(sid);

        checkUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        emailFromDb = userSnapshot.child("email").getValue(String.class);

                        if (emailFromDb != null) {
                            emailholder.setText("Login Email ID: " + emailFromDb);
                        } else {
                            emailholder.setText("Email not available");
                        }
                    }
                } else {
                    emailholder.setText("No data found for the given Student ID");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                emailholder.setText("Error fetching data");
            }
        });
    }*/
}
