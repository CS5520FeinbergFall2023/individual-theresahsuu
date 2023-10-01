package edu.northeastern.numad23fa_theresahsu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the "About Me" button by its ID
        Button aboutMeButton = findViewById(R.id.buttonAboutMe);

        // Set an OnClickListener to the button
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define your name and email
                String name = "FuHsing Hsu";
                String email = "hsu.fu-@northeastern.edu";

                // Create and display a Toast with your name and email
                Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onButtonClickyClicky(View view) {
        Intent intent = new Intent(this, ButtonClickActivity.class);
        startActivity(intent);
    }
}