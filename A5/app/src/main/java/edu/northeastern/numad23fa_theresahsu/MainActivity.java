package edu.northeastern.numad23fa_theresahsu;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutMeButton = findViewById(R.id.buttonAboutMe);
        Button linkCollectorButton = findViewById(R.id.buttonLinkCollector);
        Button findPrimesButton = findViewById(R.id.findPrimesButton);

        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutMeIntent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(aboutMeIntent);
            }
        });

        linkCollectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkCollectorIntent = new Intent(MainActivity.this, LinkCollectorActivity.class);
                startActivity(linkCollectorIntent);
            }
        });

        // Find Primes button click event
        findPrimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the "Find Primes" button click event
                openPrimeNumberActivity();
            }
        });
    }

    public void onButtonClickyClicky(View view) {
        Intent intent = new Intent(this, ButtonClickActivity.class);
        startActivity(intent);
    }

    // Method to open the PrimeNumberActivity
    private void openPrimeNumberActivity() {
        Intent intent = new Intent(this, PrimeNumberActivity.class);
        startActivity(intent);
    }
}