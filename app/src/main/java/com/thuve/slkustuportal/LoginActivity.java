package com.thuve.slkustuportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    Button regb,loginbtn;
    EditText sid,pwd;
    TextView forgotpwd,errortxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sid=findViewById(R.id.entersid);
        pwd=findViewById(R.id.enterpassword);

        errortxt=findViewById(R.id.error);
        forgotpwd=findViewById(R.id.forgotpwd);

        loginbtn=findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SID=sid.getText().toString(),PWD=pwd.getText().toString();
                if (valInput(SID,PWD)){
                    checkcredentials(SID, PWD);
                }
            }
        });

        regb=findViewById(R.id.regbtn);
        regb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        //TODO : Forget password ,page intent
        forgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ForgotpwdActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean valInput(String SID, String PWD){
        if(SID.isEmpty()){
            if (PWD.isEmpty()) {
                errortxt.setText("Student ID and Password fields are mandatory.");
                return false;
            }
            errortxt.setText("Student ID field cannot be empty.");
            return false;
        } else if (PWD.isEmpty()) {
            errortxt.setText("Password field cannot be empty.");
            return false;
        } else {
            return true;

        }
    }
//Note-Firebase DB Part

    private void checkcredentials(String sid, String password) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
        Query checkuserDb = reference.orderByChild("sid").equalTo(sid);

        checkuserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passwordFromDb = userSnapshot.child("password").getValue(String.class);
                        if (passwordFromDb != null && passwordFromDb.equals(password)) {
                            // Correct password, navigate to MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("sid", sid);
                            startActivity(intent);
                            finish(); // Close LoginActivity
                        } else {
                            errortxt.setText("Wrong Password");
                        }
                    }
                } else {
                    errortxt.setText("User does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errortxt.setText("Database Error: " + error.getMessage());
            }
        });
    }

}