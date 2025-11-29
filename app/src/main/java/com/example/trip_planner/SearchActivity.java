package com.example.trip_planner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.DatePickerDialog;
import com.google.gson.Gson;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private Gson gson;
    private List<Trip> tripsFromPrefs;
    private RecyclerView recycler;
    Button edtTripDate;
    ImageButton btnShowFilter;
    Button btnSearchTrips;

    RadioGroup radioGroup;
    RadioButton rbBooked, rbUnbooked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getAllTrips();
        setRecyclerView();

        edtTripDate = findViewById(R.id.edtTripDate);
        manageDatePicker();

        ImageButton homeImgBtn = findViewById(R.id.homeImgBtn);
        ImageButton savedImgBtn = findViewById(R.id.savedImgBtn);
        ImageButton bookingsImgBtn = findViewById(R.id.bookingsImgBtn);
        ImageButton searchImgBtn = findViewById(R.id.searchImgBtn);
        btnShowFilter = findViewById(R.id.btnShowFilter);
        btnSearchTrips = findViewById(R.id.btnSearchTrips);

        radioGroup = findViewById(R.id.radio_group);
        rbBooked = findViewById(R.id.rb_booked);
        rbUnbooked = findViewById(R.id.rb_unbooked);
        rbUnbooked.setSelected(true);


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
            startActivity(new Intent(this, BookingsActivity.class));
        });

        // Search Activity
        searchImgBtn.setOnClickListener(v -> {
        });

        btnSearchTrips.setOnClickListener(v -> filterTripsByDate());
    }

    public void manageDatePicker(){
        // Open DatePicker when clicked
        edtTripDate.setOnClickListener(v -> {
            // Get current date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Create DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Update EditText with selected date
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        edtTripDate.setText(date);
                    }, year, month, day);

            // Show the picker
            datePickerDialog.show();
        });
    }

//    private void filterTripsByDate() {
//        String selectedDateStr = edtTripDate.getText().toString().trim();
//        if (selectedDateStr.isEmpty()) {
//            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Parse selected date from EditText
//        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy"); // day/month/year
//        Date selectedDate;
//        try {
//            selectedDate = sdf.parse(selectedDateStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Calendar calSelected = Calendar.getInstance();
//        calSelected.setTime(selectedDate);
//
//        ArrayList<Trip> filteredTrips = new ArrayList<>();
//        for (Trip trip : tripsFromPrefs) {
//            Date tripDate = trip.getDate();
//            Calendar calTrip = Calendar.getInstance();
//            calTrip.setTime(tripDate);
//
//            // Compare day, month, year
//            if (calTrip.get(Calendar.DAY_OF_MONTH) == calSelected.get(Calendar.DAY_OF_MONTH) &&
//                    calTrip.get(Calendar.MONTH) == calSelected.get(Calendar.MONTH) &&
//                    calTrip.get(Calendar.YEAR) == calSelected.get(Calendar.YEAR)) {
//                filteredTrips.add(trip);
//            }
//        }
//
//        // Update RecyclerView
//        TripCardAdapter newAdapter = new TripCardAdapter(filteredTrips);
//        recycler.setAdapter(newAdapter);
//    }

    private void filterTripsByDate() {
        String selectedDateStr = edtTripDate.getText().toString().trim();
        if (selectedDateStr.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse selected date
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        Date selectedDate;
        try {
            selectedDate = sdf.parse(selectedDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Determine filter type
        Boolean filterBooked = null; // null = no booking filter
        if (rbBooked.isChecked()) filterBooked = true;
        else if (rbUnbooked.isChecked()) filterBooked = false;

        Calendar calSelected = Calendar.getInstance();
        calSelected.setTime(selectedDate);

        ArrayList<Trip> filteredTrips = new ArrayList<>();

        for (Trip trip : tripsFromPrefs) {

            // DATE MATCH
            Calendar calTrip = Calendar.getInstance();
            calTrip.setTime(trip.getDate());
            boolean dateMatch =
                    calTrip.get(Calendar.DAY_OF_MONTH) == calSelected.get(Calendar.DAY_OF_MONTH) &&
                            calTrip.get(Calendar.MONTH) == calSelected.get(Calendar.MONTH) &&
                            calTrip.get(Calendar.YEAR) == calSelected.get(Calendar.YEAR);

            if (!dateMatch) continue;

            // BOOKING MATCH (if selected)
            if (filterBooked != null) {
                if (trip.isBooked() != filterBooked) continue;
            }

            filteredTrips.add(trip);
        }

        recycler.setAdapter(new TripCardAdapter(filteredTrips));
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
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        TripCardAdapter adapter = new TripCardAdapter(tripsFromPrefs);
        recycler.setAdapter(adapter);
    }

    private void updateRecyclerView() {
        getAllTrips();
        TripCardAdapter newAdapter = new TripCardAdapter(tripsFromPrefs);
        recycler.setAdapter(newAdapter);
    }

}