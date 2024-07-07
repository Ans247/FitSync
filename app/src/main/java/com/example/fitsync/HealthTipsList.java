package com.example.fitsync;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class HealthTipsList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HealthTipAdapter adapter;
    private List<HealthTip> healthTipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips_list);

        recyclerView = findViewById(R.id.recyclerHealthList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        healthTipList = new ArrayList<>();
        adapter = new HealthTipAdapter(this, healthTipList);
        recyclerView.setAdapter(adapter);

        // Fetch health tips from Firebase
        DatabaseReference healthTipsRef = FirebaseDatabase.getInstance().getReference().child("health_tips");
        healthTipsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                healthTipList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HealthTip healthTip = dataSnapshot.getValue(HealthTip.class);
                    if (healthTip != null) {
                        healthTipList.add(healthTip);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}
