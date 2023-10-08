package edu.northeastern.numad23fa_theresahsu;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {

    private List<Link> linkList;
    private Link removedLink; // Corrected variable name

    public LinkAdapter(List<Link> linkList) {
        this.linkList = linkList;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_link, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        Link link = linkList.get(position);
        holder.nameTextView.setText(link.getName());
        holder.urlTextView.setText(link.getUrl());
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    // Method to retrieve the removed link
    public Link getRemovedLink() {
        return removedLink;
    }

    public class LinkViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView urlTextView;

        public LinkViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textLinkName);
            urlTextView = itemView.findViewById(R.id.textLinkURL);

            // Handle tapping a link to launch the URL
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Link link = linkList.get(position);
                    String url = link.getUrl();

                    // Launch the URL in a web browser
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
