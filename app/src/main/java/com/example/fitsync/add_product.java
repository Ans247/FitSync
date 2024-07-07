package com.example.fitsync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_product extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private TextInputEditText etProductName, etProductDescription, etQuantity, etImage;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize Views
        etProductName = findViewById(R.id.etProductName);
        etProductDescription = findViewById(R.id.etProductDescription);
        etQuantity = findViewById(R.id.etQuantity);
        etImage = findViewById(R.id.etImage);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Save button click listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });

        // Cancel button click listener
        btnCancel.setOnClickListener(v-> {

            startActivity(new Intent(add_product.this, AdminHome.class));
            finish();
        });
    }

    private void saveProduct() {
        // Get input values
        String productName = etProductName.getText().toString().trim();
        String productDescription = etProductDescription.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String imageUrl = etImage.getText().toString().trim();

        // Create a unique key for the new product
        String productId = mDatabase.child("products").push().getKey();

        // Create Product object
        Product product = new Product(productName, productDescription, quantity,imageUrl );

        // Save to Firebase Database
        mDatabase.child("products").child(productId).setValue(product);


    }
}

