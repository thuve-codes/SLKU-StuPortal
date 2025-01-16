package com.thuve.slkustuportal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {

    EditText cpass,npass,cnpass;
    Button resetbtn,delbtn;
    TextView sidplace,errortxt;
    ImageView backButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editprofile);
        String sid = getIntent().getStringExtra("sid");
        String pwd = getIntent().getStringExtra("pwd");

        cpass=findViewById(R.id.edit_current_password);
        npass=findViewById(R.id.edit_new_password);
        cnpass=findViewById(R.id.edit_renew_password);

        resetbtn=findViewById(R.id.button_reset_password);
        delbtn=findViewById(R.id.button_delete_account);

        errortxt=findViewById(R.id.errortxt);

        backButton = findViewById(R.id.backButton);

        sidplace=findViewById(R.id.loginsidplace);
        sidplace.setText("Your Logged in Student ID is "+sid);

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpassword=cpass.getText().toString();
                String npassword=npass.getText().toString();
                String cnpassword=cnpass.getText().toString();
                if (TextUtils.isEmpty(cpassword) || TextUtils.isEmpty(npassword) || TextUtils.isEmpty(cnpassword)) {
                    errortxt.setText("All fields are required!");
                    return;
                }
                if (!cpassword.equals(pwd)) {
                    errortxt.setText("Current password is incorrect!");
                    // Toast.makeText(EditProfile.this, "", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!npass.getText().toString().equals(cnpass.getText().toString())) {
                    errortxt.setText("New passwords do not match!");
                    return;
                }

                if(!storepassdb(sid,cnpassword)) {
                    Toast.makeText(EditProfile.this, "Password successfully updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfile.this, MainActivity.class);
                    intent.putExtra("pwd", cnpassword);

                    startActivity(intent);
                }
            }
        });
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sid != null) {
                    // Get Firebase database reference
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Students");

                    // Delete the user account from the database
                    databaseReference.child(sid).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProfile.this, "Account deleted successfully!", Toast.LENGTH_SHORT).show();

                            // Redirect to login or main screen
                            Intent intent = new Intent(EditProfile.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Close the current activity
                        } else {
                            Toast.makeText(EditProfile.this, "Failed to delete account. Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(EditProfile.this, "Invalid Student ID. Cannot delete account.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, MainActivity.class);

                startActivity(intent);
            }
        });

    }



    private boolean storepassdb(String sid,String password) {
        // Get Firebase database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Students");


        // Create a flag to track success
        final boolean[] isSuccessful = {false};

        // Check if `sid` is not null
        if (sid != null) {
            // Update password in the database
            databaseReference.child(sid).child("password").setValue(password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            isSuccessful[0] = true; // Password updated successfully
                        } else {

                            Toast.makeText(EditProfile.this, "Error updating password. Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(EditProfile.this, "Invalid Student ID. Password not updated.", Toast.LENGTH_SHORT).show();
        }

        return isSuccessful[0];
    }

}
