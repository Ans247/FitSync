package com.example.fitsync;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.navigation.NavigationView;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.primaryButton));

        // Set user details in the header
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            TextView tvName = navigationView.getHeaderView(0).findViewById(R.id.tvNameH);
            TextView tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tvEmailH);
            tvName.setText(user.getDisplayName());
            tvEmail.setText(user.getEmail());
        }

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // Load a banner ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.optTips) {
            startActivity(new Intent(Home.this, HealthTipsList.class));
            finish();
        } else if (id == R.id.optProduct) {
            startActivity(new Intent(Home.this, ProductList.class));
            finish();

        } else if (id == R.id.optReminder) {
            startActivity(new Intent(Home.this, SetReminder.class));
            finish();
        } else if (id == R.id.optAssistant) {
            startActivity(new Intent(Home.this, Assistant.class));
            finish();
        } else if (id == R.id.optSettings) {
            // Handle the "Settings" action
        } else if (id == R.id.optLogOut) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Home.this, MainActivity.class));
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

