package com.thuve.slkustuportal;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private String emailFromDb,usernameFromDb;
    private TextView sidholder, emailholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_login);

        // Get data from Intent
        String sid = getIntent().getStringExtra("sid");
        if (sid != null) {
            sidholder.setText("Login Student ID: " + sid);
            fetchData(sid); // Fetch additional data from Firebase
        } else {
            sidholder.setText("Student ID not available");
        }




    }



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
    }
}
