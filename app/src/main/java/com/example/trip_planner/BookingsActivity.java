package com.example.trip_planner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingsActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private Gson gson;
    private List<Trip> tripsFromPrefs;
    private RecyclerView recycler;
    private List<Trip> bookedTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bookings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bookings_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getBookedTrips();
        setRecyclerView();

        ImageButton homeImgBtn = findViewById(R.id.homeImgBtn);
        ImageButton savedImgBtn = findViewById(R.id.savedImgBtn);
        ImageButton bookingsImgBtn = findViewById(R.id.bookingsImgBtn);
        ImageButton searchImgBtn = findViewById(R.id.searchImgBtn);

        // Home Activity
        homeImgBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        // Saved Activity
        savedImgBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, SavedItemsActivity.class));
        });

        // Bookings Activity
        bookingsImgBtn.setOnClickListener(v -> {

        });

        // Search Activity
        searchImgBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
        });
    }

    private void getBookedTrips(){
        tripsFromPrefs = TripManager.getInstance(this).getTrips();
        bookedTrips = TripManager.getInstance(this).getBookedTrips();
    }
    private void setRecyclerView(){
        recycler = findViewById(R.id.trips_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        TripCardAdapter adapter = new TripCardAdapter(tripsFromPrefs);
        recycler.setAdapter(adapter);
    }

    private void updateRecyclerView() {
        getBookedTrips();
        TripCardAdapter newAdapter = new TripCardAdapter(bookedTrips);
        recycler.setAdapter(newAdapter);
    }

    @Override
    public void onStart(){
        super.onStart();
        updateRecyclerView();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveTripsToPrefs();
    }

    public void saveTripsToPrefs() {
        TripManager.getInstance(this).saveTrips();
    }
}