package com.example.fitsync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;



public class AdminHome extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        mAuth = FirebaseAuth.getInstance();


        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        Button btnLogOut = findViewById(R.id.btnLogOut);
        Button btnAddHealthTip= findViewById(R.id.btnAddHealthTip);

        btnAddHealthTip.setOnClickListener(v -> {
            startActivity(new Intent(AdminHome.this, add_tip.class));
            finish();

        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminHome.this, add_product.class));
                finish();

            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }


    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(AdminHome.this, MainActivity.class));
        finish();
    }
}

