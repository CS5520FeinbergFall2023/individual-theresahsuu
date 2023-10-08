package edu.northeastern.numad23fa_theresahsu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        String name = "FuHsing Hsu";
        String email = "hsu.fu-@northeastern.edu";

        TextView nameTextView = findViewById(R.id.textViewName);
        TextView emailTextView = findViewById(R.id.textViewEmail);

        nameTextView.setText("Name: " + name);
        emailTextView.setText("Email: " + email);
    }
}