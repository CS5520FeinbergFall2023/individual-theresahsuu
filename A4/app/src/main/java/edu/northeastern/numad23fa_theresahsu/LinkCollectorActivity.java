package edu.northeastern.numad23fa_theresahsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity {
    private static final String LINKS_KEY = "links_key";
    private List<Link> links = new ArrayList<>(); // List of links
    private LinkAdapter adapter;
    private EditText linkNameEditText;
    private EditText linkURLEditText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        // Initialize RecyclerView and set its adapter
        recyclerView = findViewById(R.id.recyclerViewLinks);
        adapter = new LinkAdapter(links);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Initialize EditText views
        linkNameEditText = findViewById(R.id.editTextLinkName);
        linkURLEditText = findViewById(R.id.editTextLinkURL);

        // Initialize FloatingActionButton and set its click listener
        FloatingActionButton fabAddLink = findViewById(R.id.fabAddLink);
        fabAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event to add a new link
                showLinkInputDialog();
            }
        });

        // Implement swipe to delete functionality
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Handle the logic to add a new link (name and URL)
    private void addNewLink(String name, String url) {
        links.add(new Link(name, url));
        adapter.notifyDataSetChanged();
    }

    // Show a dialog or bottom sheet to input link information
    private void showLinkInputDialog() {
        // You can create a dialog or bottom sheet here to input link info
        // For simplicity, we'll use a Snackbar to demonstrate
        String name = linkNameEditText.getText().toString();
        String url = linkURLEditText.getText().toString();

        if (!name.isEmpty() && !url.isEmpty()) {
            // Add the new link to the list
            addNewLink(name, url);

            // Clear the EditText fields
            linkNameEditText.setText("");
            linkURLEditText.setText("");

            // Show a Snackbar to inform the user
            Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "Link added successfully", Snackbar.LENGTH_SHORT);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle undo action if needed
                    // This is optional and can be customized as per your requirements
                    if (!links.isEmpty()) {
                        // Remove the last added link to undo
                        links.remove(links.size() - 1);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            snackbar.show();
        } else {
            Snackbar.make(findViewById(R.id.coordinatorLayout), "Please enter both name and URL", Snackbar.LENGTH_SHORT).show();
        }
    }

    // Handle link item click to open the URL
    public void onLinkItemClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        if (position >= 0 && position < links.size()) {
            String url = links.get(position).getUrl();
            if (!url.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            } else {
                Snackbar.make(findViewById(R.id.coordinatorLayout), "Empty URL", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    // Implement swipe to delete functionality
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (position >= 0 && position < links.size()) {
                // Remove the swiped link
                links.remove(position);
                adapter.notifyItemRemoved(position);

                // Show a Snackbar with an action button to undo the deletion
                Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "Link deleted", Snackbar.LENGTH_SHORT);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Restore the deleted link
                        if (position >= 0 && position <= links.size()) {
                            links.add(position, adapter.getRemovedLink());
                            adapter.notifyItemInserted(position);
                        }
                    }
                });
                snackbar.show();
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the links list state
        ArrayList<Parcelable> linkParcelables = new ArrayList<>();
        for (Link link : links) {
            linkParcelables.add((Parcelable) link);
        }
        outState.putParcelableArrayList(LINKS_KEY, linkParcelables);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the links list state
        if (savedInstanceState.containsKey(LINKS_KEY)) {
            ArrayList<Parcelable> restoredLinks = savedInstanceState.getParcelableArrayList(LINKS_KEY);
            if (restoredLinks != null) {
                links.clear();
                for (Parcelable parcelable : restoredLinks) {
                    if (parcelable instanceof Link) {
                        links.add((Link) parcelable);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}