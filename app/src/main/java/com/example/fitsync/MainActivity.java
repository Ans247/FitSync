package com.example.fitsync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    FragmentManager manager;

    Fragment loginFrag, signupFrag, adminLoginFrag;
    View loginFragView, signupFragView, adminLoginFragView;
    TextView tvLogin, tvSignup, tvAdmin;

    // hooks for signup
    TextInputEditText etUsernameS, etPasswordS, etCPasswordS;
    Button btnSignup, btnCancelS;

    // hooks of login
    TextInputEditText etUsernameL, etPasswordL;
    Button btnLogin, btnCancelL;

    //hooks of admin login
    TextInputEditText etUsernameA, etPasswordA;
    Button btnLoginA, btnCancelA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        init();

        tvSignup.setOnClickListener(v -> {
            if (signupFrag != null && loginFrag != null) {
                manager.beginTransaction()
                        .hide(loginFrag)
                        .hide(adminLoginFrag)
                        .show(signupFrag)
                        .commit();
            }
        });

        tvLogin.setOnClickListener(v -> {
            if (signupFrag != null && loginFrag != null) {
                manager.beginTransaction()
                        .show(loginFrag)
                        .hide(signupFrag)
                        .hide(adminLoginFrag)
                        .commit();
            }
        });

        tvAdmin.setOnClickListener(v -> {
            if (signupFrag != null && loginFrag != null) {
                manager.beginTransaction()
                        .show(adminLoginFrag)
                        .hide(signupFrag)
                        .hide(loginFrag)
                        .commit();
            }

        });

        btnSignup.setOnClickListener(v -> {
            String username = Objects.requireNonNull(etUsernameS.getText()).toString().trim();
            String password = Objects.requireNonNull(etPasswordS.getText()).toString();
            String cPassword = Objects.requireNonNull(etCPasswordS.getText()).toString();

            if (username.isEmpty() || password.isEmpty() || cPassword.isEmpty()) {
                Toast.makeText(MainActivity.this, "Something is missing", Toast.LENGTH_SHORT).show();
            } else {
                if (password.equals(cPassword)) {
                    mAuth.createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    Toast.makeText(MainActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                    manager.beginTransaction()
                                            .show(loginFrag)
                                            .hide(signupFrag)
                                            .commit();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(MainActivity.this, "Password mis-matched", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancelS.setOnClickListener(v -> finish());


        btnLogin.setOnClickListener(v -> {
            String username = Objects.requireNonNull(etUsernameL.getText()).toString().trim();
            String password = Objects.requireNonNull(etPasswordL.getText()).toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Something is missing", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Log in success
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                // startActivity(new Intent(MainActivity.this, Home.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnLoginA.setOnClickListener(v -> {
            String username = Objects.requireNonNull(etUsernameA.getText()).toString().trim();
            String password = Objects.requireNonNull(etPasswordA.getText()).toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Something is missing", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Log in success
                                Toast.makeText(MainActivity.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                                 //startActivity(new Intent(MainActivity.this, AdminHome.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Admin Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, proceed to home activity
            //startActivity(new Intent(MainActivity.this, Home.class));
            finish();
        }
    }


    private void init() {
        manager = getSupportFragmentManager();
        adminLoginFrag=manager.findFragmentById(R.id.adminLogin);
        loginFrag = manager.findFragmentById(R.id.loginfrag);
        signupFrag = manager.findFragmentById(R.id.signupfrag);


        if (adminLoginFrag != null) {
            adminLoginFragView = adminLoginFrag.getView();
        }
        if (loginFrag != null) {
            loginFragView = loginFrag.getView();
        }
        if (signupFrag != null) {
            signupFragView = signupFrag.getView();
        }
        if(adminLoginFragView != null ) {

            etUsernameA = adminLoginFragView.findViewById(R.id.etUsernameA);
            etPasswordA = adminLoginFragView.findViewById(R.id.etPasswordA);
            btnLoginA =   adminLoginFragView.findViewById(R.id.btnLoginA);
            btnCancelA = adminLoginFragView.findViewById(R.id.btnCancelA);
        }
        // Perform null checks before accessing views inside fragments
        if (signupFragView != null) {
            tvLogin = signupFragView.findViewById(R.id.tvLogin);
            etUsernameS = signupFragView.findViewById(R.id.etUsername);
            etPasswordS = signupFragView.findViewById(R.id.etPassword);
            etCPasswordS = signupFragView.findViewById(R.id.etCPassword);
            btnSignup = signupFragView.findViewById(R.id.btnSignup);
            btnCancelS = signupFragView.findViewById(R.id.btnCancel);
        }
        if (loginFragView != null) {
            tvSignup = loginFragView.findViewById(R.id.tvSignup);
            etUsernameL = loginFragView.findViewById(R.id.etUsername);
            etPasswordL = loginFragView.findViewById(R.id.etPassword);
            btnLogin = loginFragView.findViewById(R.id.btnLogin);
            btnCancelL = loginFragView.findViewById(R.id.btnCancel);
            tvAdmin =loginFragView.findViewById(R.id.tvAdmin);
        }


        manager.beginTransaction()
                .hide(signupFrag)
                .hide(adminLoginFrag)
                .commit();
    }
}
