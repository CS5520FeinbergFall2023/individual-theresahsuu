package edu.northeastern.numad23fa_theresahsu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private Location lastLocation;
    private double totalDistance = 0.0;

    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView distanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latitudeTextView = findViewById(R.id.latitudeTextView);
        longitudeTextView = findViewById(R.id.longitudeTextView);
        distanceTextView = findViewById(R.id.distanceTextView);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button resetDistanceButton = findViewById(R.id.resetDistanceButton);
        resetDistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the total distance
                totalDistance = 0.0;
                updateDistanceDisplay();
            }
        });

        if (checkLocationPermission()) {
            // Request location updates
            requestLocationUpdates();
        }
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed to get the last known location
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        if (lastLocation != null) {
                            float distance = lastLocation.distanceTo(location);
                            totalDistance += distance;
                            updateDistanceDisplay();
                        }
                        lastLocation = location;
                        updateLocationDisplay();
                    }
                }
            });
        } else {
            // Handle the case where permissions are not granted
            // You can display a message or request permissions again
            Toast.makeText(this, "Location permission is required to show your location.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLocationDisplay() {
        if (lastLocation != null) {
            latitudeTextView.setText("Latitude: " + lastLocation.getLatitude());
            longitudeTextView.setText("Longitude: " + lastLocation.getLongitude());
        }
    }

    private void updateDistanceDisplay() {
        distanceTextView.setText("Total Distance Traveled: " + totalDistance + " meters");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, request location updates
                requestLocationUpdates();
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Location permission is required for this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}