package edu.northeastern.numad23fa_theresahsu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class PrimeNumberActivity extends AppCompatActivity {

    private TextView currentNumberTextView;
    private TextView latestPrimeTextView;
    private Button findPrimesButton;
    private Button terminateSearchButton;
    private Switch pacifierSwitch;

    private boolean isSearching = false;
    private int currentNumber = 3;
    private int latestPrime = 0;

    private WorkerThread workerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_number);

        currentNumberTextView = findViewById(R.id.textViewCurrentNumber);
        latestPrimeTextView = findViewById(R.id.textViewLatestPrime);
        findPrimesButton = findViewById(R.id.buttonFindPrimes);
        terminateSearchButton = findViewById(R.id.buttonTerminateSearch);
        pacifierSwitch = findViewById(R.id.checkBoxPacifier);

        if (savedInstanceState != null) {
            isSearching = savedInstanceState.getBoolean("isSearching");
            currentNumber = savedInstanceState.getInt("currentNumber");
            latestPrime = savedInstanceState.getInt("latestPrime");
            pacifierSwitch.setChecked(savedInstanceState.getBoolean("pacifierChecked"));
        }

        updateUI();

        findPrimesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSearching) {
                    startPrimeNumberSearch();
                }
            }
        });

        terminateSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminatePrimeNumberSearch();
            }
        });

        pacifierSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nothing to do here, just to test UI responsiveness
            }
        });
    }

    private void startPrimeNumberSearch() {
        workerThread = new WorkerThread();
        workerThread.start();
        isSearching = true;
        updateUI();
    }

    private void terminatePrimeNumberSearch() {
        if (workerThread != null) {
            workerThread.interrupt();
        }
        isSearching = false;
        updateUI();
    }

    private void updateUI() {
        currentNumberTextView.setText("Current Number: " + currentNumber);
        latestPrimeTextView.setText("Latest Prime Found: " + latestPrime);
        findPrimesButton.setEnabled(!isSearching);
        terminateSearchButton.setEnabled(isSearching);
        pacifierSwitch.setEnabled(!isSearching);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isSearching", isSearching);
        outState.putInt("currentNumber", currentNumber);
        outState.putInt("latestPrime", latestPrime);
        outState.putBoolean("pacifierChecked", pacifierSwitch.isChecked());
    }

    // Custom worker thread to find prime numbers
    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (isSearching) {
                if (isPrime(currentNumber)) {
                    latestPrime = currentNumber;
                }
                currentNumber += 2; // Increment by two to check odd numbers

                // Update UI from the worker thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });

                try {
                    Thread.sleep(100); // Adjust the sleep time as needed
                } catch (InterruptedException e) {
                    // Thread interrupted (search terminated)
                    break;
                }
            }
        }
    }

    // Check if a number is prime
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}