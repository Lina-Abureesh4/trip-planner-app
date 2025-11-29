package com.example.trip_planner;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TripManager {
    private static TripManager instance;
    private List<Trip> trips;
    private SharedPreferences prefs;
    private Gson gson;

    private TripManager(Context context) {
        prefs = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        gson = new Gson();
        loadTrips();
    }

    public static TripManager getInstance(Context context) {
        if (instance == null) instance = new TripManager(context.getApplicationContext());
        return instance;
    }

    private void loadTrips() {
        String json = prefs.getString("trip_list", "");
        if (!json.isEmpty()) {
            Trip[] arr = gson.fromJson(json, Trip[].class);
            trips = new ArrayList<>(Arrays.asList(arr));
        } else {
            initializeSharedPreferences();
        }
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void updateTrip(Trip trip) {
        // Trip objects are mutable, so changes are automatically reflected
        saveTrips();
    }

    public void saveTrips() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("trip_list", gson.toJson(trips));
        editor.apply();
    }

    public List<Trip> getSavedTrips() {
        List<Trip> saved = new ArrayList<>();
        for (Trip t : trips) if (t.isSaved()) saved.add(t);
        return saved;
    }

    public List<Trip> getBookedTrips() {
        List<Trip> booked = new ArrayList<>();
        for (Trip t : trips) if (t.isBooked()) booked.add(t);
        return booked;
    }

    private void initializeSharedPreferences() {
        trips = new ArrayList<>();

        trips.add(new Trip(
                "Jaffa",
                "During your trip to Jaffa, you can enjoy a scenic walk through the Old Jaffa Port with its boats, cafés, and sea views, then wander the stone alleys of the Old City filled with art galleries and boutique shops. The vibrant Flea Market offers vintage pieces and handmade items, while Clock Tower Square serves as a lively central spot surrounded by restaurants and cafés. You can also relax at Jaffa or Ajami Beach, which is perfect for a break or watching the sunset. The area offers a variety of seafood restaurants, cozy local cafés, and quick bites like falafel and ice cream, making it suitable for different budgets.",
                R.drawable.jafa,
                150,
                "Culture, History, Seaside",
                false,
                false,
                new Date(125, 11, 6) // 6 December 2025 (year = year - 1900)
        ));

        trips.add(new Trip(
                "Haifa, Mount Carmel & Bay area",
                "Discover Haifa — a city blending hillside charm and coastal serenity. Stroll through lush terraced gardens, enjoy panoramic sea and bay views, explore historic neighborhoods and lively urban quarters, and relax in cafés or by the waterfront.",
                R.drawable.haifa,
                130,
                "Culture, Urban, Gardens, Views",
                false,
                false,
                new Date(125, 11, 5) // 5 December 2025
        ));

        trips.add(new Trip(
                "Old City, Jerusalem",
                "Explore the historic Old City of Jerusalem, a vibrant area filled with culture, religion, and history. Visit sacred sites, bazaars, and experience everyday life.",
                R.drawable.quds,
                150,
                "Culture, Religion, History",
                false,
                false,
                new Date(125, 11, 8) // 8 December 2025
        ));

        trips.add(new Trip(
                "Wadi Auja",
                "Discover the lush valley of Wadi Auja, a scenic natural area known for its springs, greenery, and hiking trails. Perfect for nature lovers seeking fresh air and peaceful landscapes.",
                R.drawable.wadi_auja,
                55,
                "Nature, Hiking",
                false,
                false,
                new Date(125, 11, 10) // 10 December 2025
        ));

        trips.add(new Trip(
                "Khirbet Qumran, near the Dead Sea",
                "Explore the archaeological site of Qumran, famous for the discovery of the Dead Sea Scrolls. Ideal for students, researchers, and history enthusiasts.",
                R.drawable.qumran,
                55,
                "Archaeology, Education, History",
                false,
                false,
                new Date(125, 11, 12) // 12 December 2025
        ));

        trips.add(new Trip(
                "Battir",
                "Explore the famous Battir terraces, a UNESCO-listed cultural landscape showcasing ancient agricultural techniques and stunning rural scenery. Perfect for families, school groups, and photographers.",
                R.drawable.battir,
                80,
                "Agritourism, Landscape, Culture",
                false,
                false,
                new Date(125, 11, 14) // 14 December 2025
        ));

        String json = gson.toJson(trips);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("trip_list", json);
        editor.apply();

    }
}

