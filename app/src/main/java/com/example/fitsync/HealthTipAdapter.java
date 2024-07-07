package com.example.fitsync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HealthTipAdapter extends RecyclerView.Adapter<HealthTipAdapter.HealthTipViewHolder> {

    private Context context;
    private List<HealthTip> healthTipList;

    public HealthTipAdapter(Context context, List<HealthTip> healthTipList) {
        this.context = context;
        this.healthTipList = healthTipList;
    }

    @NonNull
    @Override
    public HealthTipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_health_tip, parent, false);
        return new HealthTipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthTipViewHolder holder, int position) {
        HealthTip healthTip = healthTipList.get(position);
        holder.tvTipTitle.setText(healthTip.getTitle());
        holder.tvTipDescription.setText(healthTip.getDescription());
    }

    @Override
    public int getItemCount() {
        return healthTipList.size();
    }

    public static class HealthTipViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipTitle, tvTipDescription;
        CardView cardView;

        public HealthTipViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipTitle = itemView.findViewById(R.id.tvTipTitle);
            tvTipDescription = itemView.findViewById(R.id.tvTipDescription);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
