package com.thuve.slkustuportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    TextView loginbtn,Errortxt;
    EditText sid,un,email,pwd,rpwd;
    CheckBox terms;
    Button createbtn;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sid=findViewById(R.id.sid);
        un=findViewById(R.id.un);
        email=findViewById(R.id.email);
        pwd=findViewById(R.id.pwd);
        rpwd=findViewById(R.id.rpwd);
        terms=findViewById(R.id.terms);

        Errortxt=findViewById(R.id.Errortxt);

        createbtn=findViewById(R.id.createbtn);

        RadioGroup resetOptionGroup = findViewById(R.id.resetOptionGroup);


        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("Students");

                String SID = sid.getText().toString(),
                        UN = un.getText().toString(),
                        EMAIL = email.getText().toString(),
                        PWD = pwd.getText().toString(),
                        RPWD = rpwd.getText().toString();

                // Determine which RadioButton is selected
                String selectedFaculty = "";
                int selectedId = resetOptionGroup.getCheckedRadioButtonId();

                if (selectedId == R.id.Computing) {
                    selectedFaculty = "Computing";
                } else if (selectedId == R.id.Business) {
                    selectedFaculty = "Business";
                } else if (selectedId == R.id.Medicine) {
                    selectedFaculty = "Medicine";
                } else if (selectedId == R.id.Engineering) {
                    selectedFaculty = "Engineering";
                }


                // Create DBHelper object
                DBHelper helperClass = new DBHelper(PWD, UN, EMAIL,SID,selectedFaculty);

                if(valInputs(SID,UN,EMAIL,PWD,RPWD)){
                    // Save to Firebase
                    myRef.child(SID).setValue(helperClass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Your account has been created! Please log in to continue.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Close SignupActivity
                        } else {
                            Toast.makeText(SignupActivity.this, "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });





        loginbtn=findViewById(R.id.loginText);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean valInputs(String SID, String UN, String EMAIL, String PWD, String RPWD) {
        // Clear the error text
        Errortxt.setText("");

        // Check each field one by one and set the first error encountered
        if (SID == null || SID.trim().isEmpty()) {
            Errortxt.setText("Please enter your Student ID to proceed.");
            return false;
        } else if (UN == null || UN.trim().isEmpty()) {
            Errortxt.setText("Please provide a Username to create your account.");
            return false;
        } else if (EMAIL == null || EMAIL.trim().isEmpty()) {
            Errortxt.setText("Email is required for account verification.");
            return false;
        } else if (PWD == null || PWD.trim().isEmpty()) {
            Errortxt.setText("A Password is required to secure your account.");
            return false;
        } else if (RPWD == null || RPWD.trim().isEmpty()) {
            Errortxt.setText("Please confirm your Password to proceed.");
            return false;
        } else if (!PWD.equals(RPWD)) {
            Errortxt.setText("Passwords do not match. Please try again.");
            return false;
        }else if(!terms.isChecked()){
                Errortxt.setText("Please accept the Terms and Conditions to proceed.");
                return false;
        } else {
            // Proceed if all inputs are valid
            return true;
        }
    }

}