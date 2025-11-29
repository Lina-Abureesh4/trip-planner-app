package com.example.trip_planner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class CardActivity extends AppCompatActivity {

    private TextView txtDestination, txtDescription, txtBudget, txtCategory, txtDate;
    private ImageView imgTrip;
    private Button btnBook, btnComment;
    private EditText edtComment;
    private ImageView btnBack, btnSave;
    private SharedPreferences prefs;
    private Gson gson;
    private List<Trip> tripsFromPrefs;
    private RecyclerView recycler;
    private List<Trip> savedTrips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.card_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getAllTrips();

        SharedPreferences prefs = getSharedPreferences("comments_prefs", MODE_PRIVATE);
        Gson gson = new Gson();

        txtDestination = findViewById(R.id.txtDestination);
        txtDescription = findViewById(R.id.txtDescription);
        txtBudget = findViewById(R.id.txtBudget);
        txtCategory = findViewById(R.id.txtCategory);
        txtDate = findViewById(R.id.txtDate);
        imgTrip = findViewById(R.id.imgTrip);
        btnBack = findViewById(R.id.btnBack);
        btnBook = findViewById(R.id.btnBook);
        btnSave = findViewById(R.id.btnSave);
        btnComment = findViewById(R.id.btnComment);
        edtComment = findViewById(R.id.edtComment);

        Trip clickedTrip = (Trip) getIntent().getSerializableExtra("trip");
        Trip trip = getTripFromTripManager(clickedTrip);

        txtDestination.setText(trip.getDestination());
        txtDescription.setText(trip.getDescription());
        txtBudget.setText("₪ " + String.valueOf(trip.getBudgetPerIndividual()));
        txtCategory.setText(trip.getCategory());

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        txtDate.setText(sdf.format(trip.getDate()));

        imgTrip.setImageResource(trip.getImageID());

        if (trip.isSaved()) {
            btnSave.setImageResource(R.drawable.ic_save_black);
        } else {
            btnSave.setImageResource(R.drawable.ic_save);
        }

        if (trip.isBooked()) {
            btnBook.setText("Cancel Booking");
        } else {
            btnBook.setText("Book Trip");
        }

        btnSave.setOnClickListener(v -> {
            if(trip.isSaved()){
                trip.setSaved(false);
                Drawable dr2 = ContextCompat.getDrawable(this, R.drawable.ic_save);
                btnSave.setImageDrawable(dr2);
            } else {
                trip.setSaved(true);
                Drawable dr2 = ContextCompat.getDrawable(this, R.drawable.ic_save_black);
                btnSave.setImageDrawable(dr2);
            }
        });

        btnBook.setOnClickListener(v -> {
            if(trip.isBooked()){
                trip.setBooked(false);
                btnBook.setText("Book Trip");
            } else {
                trip.setBooked(true);
                btnBook.setText("Cancel Booking");
            }
        });

        btnComment.setOnClickListener(v -> {
            if (edtComment.getText().toString().isEmpty()) {
                Toast.makeText(this, "Comment field is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            Comment comment = new Comment(edtComment.getText().toString(), trip.getId());
            String json = gson.toJson(comment);
            prefs.edit().putString("last_comment", json).apply();
            Toast.makeText(this, "Comment saved", Toast.LENGTH_SHORT).show();
            edtComment.setText("");
        });

        btnBack.setOnClickListener(v -> {
            saveTripsToPrefs();
            finish();
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        getAllTrips();
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

    private Trip getTripFromTripManager(Trip clickedTrip) {
        for(Trip trip: tripsFromPrefs){
            if(trip.equals(clickedTrip))
                return trip;
        }

        return null;
    }
}