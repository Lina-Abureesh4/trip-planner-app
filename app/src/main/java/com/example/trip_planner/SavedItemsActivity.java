package com.example.trip_planner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SavedItemsActivity extends AppCompatActivity {

    private List<Trip> tripsFromPrefs;
    private RecyclerView recycler;
    private List<Trip> savedTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.saved_items_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSavedTrips();
        setRecyclerView();

        ImageButton homeImgBtn = findViewById(R.id.homeImgBtn);
        ImageButton savedImgBtn = findViewById(R.id.savedImgBtn);
        ImageButton bookingsImgBtn = findViewById(R.id.bookingsImgBtn);
        ImageButton searchImgBtn = findViewById(R.id.searchImgBtn);

        // Home Activity
        homeImgBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        // Bookings Activity
        bookingsImgBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, BookingsActivity.class));
            finish();
        });

        // Search Activity
        searchImgBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
            finish();
        });
    }
    private void getSavedTrips() {
        tripsFromPrefs = TripManager.getInstance(this).getTrips();
        savedTrips = TripManager.getInstance(this).getSavedTrips();
    }

    private void setRecyclerView(){
        recycler = findViewById(R.id.trips_recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        TripCardAdapter adapter = new TripCardAdapter(tripsFromPrefs);
        recycler.setAdapter(adapter);
    }

    private void updateRecyclerView() {
        getSavedTrips();
        TripCardAdapter newAdapter = new TripCardAdapter(savedTrips);
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