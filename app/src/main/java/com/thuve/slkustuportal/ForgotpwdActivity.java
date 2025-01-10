package com.thuve.slkustuportal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgotpwdActivity extends AppCompatActivity {
    Button resetpwd;
    EditText sid,email;
    TextView errortxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpwd);

        sid=findViewById(R.id.entersid);
        email=findViewById(R.id.email);


        resetpwd=findViewById(R.id.resetpwd);
        errortxt=findViewById(R.id.errortxt);
        resetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checksidwithemail(sid.getText().toString(),email.getText().toString());
            }
        });
    }
    private void showCustomAlertDialog() {// Create and configure the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Password Reset")
                .setMessage("An email with a password reset link has been sent to your registered email address. Please check your inbox (and spam folder, if necessary).")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // You can perform any additional action here if needed, for example, navigating the user to another screen
                        Intent intent =new Intent(ForgotpwdActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setCancelable(false); // Prevent dialog from being dismissed by tapping outside

        // Create and show the dialog
        builder.create().show();
    }





    private void checksidwithemail(String sid, String email) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
        Query checkuserDb = reference.orderByChild("sid").equalTo(sid);

        checkuserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String emailfromdb = userSnapshot.child("email").getValue(String.class);
                        if (emailfromdb != null && emailfromdb.equals(email)) {
                            showCustomAlertDialog();
                        } else {
                            errortxt.setText("The email address provided does not match the SID in our records. Please verify and try again.");
                        }
                    }
                } else {
                    errortxt.setText("The provided StudentID does not exist in our records. Please check and try again");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errortxt.setText("Database Error: " + error.getMessage());
            }
        });
    }
}