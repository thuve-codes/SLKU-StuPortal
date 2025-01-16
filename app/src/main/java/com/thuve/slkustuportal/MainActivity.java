package com.thuve.slkustuportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private TextView welcomeMessage;
    Button assen, exam, course, grade,signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button editProfile = findViewById(R.id.btneditProfile);

        welcomeMessage=findViewById(R.id.welcomeMessage);

        // Get data from Intent
        String sid = getIntent().getStringExtra("sid");
        String pwd = getIntent().getStringExtra("pwd");
        if (sid != null) {
            welcomeMessage.setText("Welcome! " + sid);
            //fetchData(sid); // Fetch additional data from Firebase
        } else {
            welcomeMessage.setText("Student ID not available");
        }


        editProfile.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, EditProfile.class);
                intent.putExtra("sid", sid);
                intent.putExtra("pwd", pwd);
                startActivity(intent);
                finish();
            });

        exam=findViewById(R.id.btnExams);
        exam.setOnClickListener(v -> {

            //setContentView(R.layout.activity_exams);
            Intent intent = new Intent(MainActivity.this, Exams.class);
            startActivity(intent);
        });
        grade=findViewById(R.id.btnGrades);
        grade.setOnClickListener(v -> {

            //setContentView(R.layout.activity_grades);
            //onBackPress();
            Intent intent = new Intent(MainActivity.this, Grades.class);
            startActivity(intent);
        });
        assen=findViewById(R.id.btnAssignments);
        assen.setOnClickListener(v -> {

            //setContentView(R.layout.activity_assignment);
            Intent intent = new Intent(MainActivity.this, Assignments.class);
            startActivity(intent);
        });
        course=findViewById(R.id.btnCourseMaterials);
        course.setOnClickListener(v -> {

            //setContentView(R.layout.activity_coursemet);
            Intent intent = new Intent(MainActivity.this, CourseMaterials.class);
            startActivity(intent);
        });

        signout=findViewById(R.id.btnsignout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
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
