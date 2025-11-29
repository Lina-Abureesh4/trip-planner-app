package com.example.trip_planner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ImageViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        ImageView imageView = findViewById(R.id.fullImage);

        Intent intent = getIntent();

        // if image_id was sent
        if (intent.hasExtra("image_id")) {
            int imageID = intent.getIntExtra("image_id", 0);
            if (imageID != 0) {
                Drawable dr = ContextCompat.getDrawable(this, imageID);
                imageView.setImageDrawable(dr);
                return;
            }
        }

        // if image_name was sent
        if (intent.hasExtra("image_name")) {
            String imageName = intent.getStringExtra("image_name");
            if (imageName != null && !imageName.isEmpty()) {
                int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                if (resId != 0) {
                    Drawable dr = ContextCompat.getDrawable(this, resId);
                    imageView.setImageDrawable(dr);
                }
            }
        }
    }
}
