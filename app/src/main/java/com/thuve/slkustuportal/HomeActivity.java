package com.thuve.slkustuportal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thuve.slkustuportal.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private String emailFromDb,usernameFromDb;
    private TextView sidholder, emailholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);

        // Initialize TextViews from navigation header
        View headerView = binding.navView.getHeaderView(0);
        sidholder = headerView.findViewById(R.id.sidholder);
        emailholder = headerView.findViewById(R.id.emailholder);

        // Get data from Intent
        String sid = getIntent().getStringExtra("sid");
        if (sid != null) {
            sidholder.setText("Login Student ID: " + sid);
            fetchData(sid); // Fetch additional data from Firebase
        } else {
            sidholder.setText("Student ID not available");
        }

        // Set up FAB action
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:info@slku.com")); // Only email apps should handle this
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from SLKU Portal");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear SLKU Team,\n\n");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send Email"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Snackbar.make(view, "No email client installed.", Snackbar.LENGTH_LONG)
                            .setAnchorView(R.id.fab).show();
                }
            }
        });


        // Set up Navigation components
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_personalnfo)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
