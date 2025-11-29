package com.example.trip_planner;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TripCardAdapter extends RecyclerView.Adapter<TripCardAdapter.ViewHolder> {

    private List<Trip> trips;

    public TripCardAdapter(List<Trip> trips) {
        this.trips = trips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_card,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CardView cardView = holder.cardView;

        // ------- Load Views -------
        ImageView imageView = cardView.findViewById(R.id.imgDestination);
        TextView txtDestination = cardView.findViewById(R.id.txtDestination);
        TextView txtDate = cardView.findViewById(R.id.txtDate);
        TextView txtPrice = cardView.findViewById(R.id.txtPrice);
        ImageButton btnSave = cardView.findViewById(R.id.btnSave);

        Trip trip = trips.get(position);


        // ------- Set Image -------
        Drawable dr = ContextCompat.getDrawable(cardView.getContext(), trip.getImageID());
        imageView.setImageDrawable(dr);

        // ------- Set Text -------
        txtDestination.setText(trip.getDestination());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        txtDate.setText(sdf.format(trip.getDate()));

        txtPrice.setText("₪ " + trip.getBudgetPerIndividual());


        // ------- Set initial save icon (IMPORTANT for RecyclerView recycling) -------
        if (trip.isSaved()) {
            btnSave.setImageResource(R.drawable.ic_save_black);
        } else {
            btnSave.setImageResource(R.drawable.ic_save);
        }


        // ------- Save button logic -------
        btnSave.setOnClickListener(v -> {
            boolean newState = !trip.isSaved();
            trip.setSaved(newState);

            btnSave.setImageResource(newState
                    ? R.drawable.ic_save_black
                    : R.drawable.ic_save);
        });

        // ------- Card click: open CardActivity -------
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CardActivity.class);
            intent.putExtra("trip", trip);  // position of the clicked trip in TripManager
            v.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}
