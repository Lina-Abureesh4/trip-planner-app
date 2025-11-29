package com.example.trip_planner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Trip> tripsFromPrefs;
    private RecyclerView recycler;
    ImageButton btnExpand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getAllTrips();
        setRecyclerView();

        ImageButton homeImgBtn = findViewById(R.id.homeImgBtn);
        ImageButton savedImgBtn = findViewById(R.id.savedImgBtn);
        ImageButton bookingsImgBtn = findViewById(R.id.bookingsImgBtn);
        ImageButton searchImgBtn = findViewById(R.id.searchImgBtn);
        btnExpand = findViewById(R.id.btnExpand);

        // Saved Activity
        savedImgBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, SavedItemsActivity.class));
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

        btnExpand.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
            finish();
        });
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

    public void getAllTrips(){
        tripsFromPrefs = TripManager.getInstance(this).getTrips();
    }

    public void saveTripsToPrefs() {
        TripManager.getInstance(this).saveTrips();
    }

    private void setRecyclerView(){
        recycler = findViewById(R.id.trips_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TripCardAdapter adapter = new TripCardAdapter(tripsFromPrefs);
        recycler.setAdapter(adapter);
    }

    private void updateRecyclerView() {
        getAllTrips();
        TripCardAdapter newAdapter = new TripCardAdapter(tripsFromPrefs);
        recycler.setAdapter(newAdapter);
    }
}