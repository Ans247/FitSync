package com.example.fitsync;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_tip extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private TextInputEditText etTipTitle, etTipDescription;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize Views
        etTipTitle = findViewById(R.id.etTipTitle);
        etTipDescription = findViewById(R.id.etTipDescription);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Save button click listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHealthTip();
            }
        });

        // Cancel button click listener
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity
            }
        });
    }

    private void saveHealthTip() {
        // Get input values
        String tipTitle = etTipTitle.getText().toString().trim();
        String tipDescription = etTipDescription.getText().toString().trim();

        // Check if fields are empty
        if (tipTitle.isEmpty() || tipDescription.isEmpty()) {
            Toast.makeText(this, "Please enter both title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a unique key for the new health tip
        String tipId = mDatabase.child("health_tips").push().getKey();

        // Create HealthTip object
        HealthTip healthTip = new HealthTip(tipId, tipTitle, tipDescription);

        // Save to Firebase Database
        mDatabase.child("health_tips").child(tipId).setValue(healthTip)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(add_tip.this, "Health tip added successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_tip.this, "Failed to add health tip: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
