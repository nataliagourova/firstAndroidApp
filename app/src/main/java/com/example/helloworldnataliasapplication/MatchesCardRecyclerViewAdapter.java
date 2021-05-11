package com.example.helloworldnataliasapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */

public class MatchesCardRecyclerViewAdapter extends RecyclerView.Adapter<MatchesCardViewHolder> {

    private Context appContext;
    private List<MatchEntry> matchesList;
    private ImageRequester imageRequester;

    MatchesCardRecyclerViewAdapter(Context appContext, List<MatchEntry> matchesList) {
        this.appContext = appContext;
        this.matchesList = matchesList;
        imageRequester = ImageRequester.getInstance(appContext);
    }

    @NonNull
    @Override
    public MatchesCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_card, parent, false);
        return new MatchesCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesCardViewHolder holder, int position) {
        if (matchesList != null && position < matchesList.size()) {
            MatchEntry match = matchesList.get(position);
            holder.matchesName.setText(match.name);
            holder.matchesAge.setText(match.age + " years old");
            imageRequester.setImageFromUrl(holder.matchesImage, match.url);
            holder.likeButton.setOnClickListener(v -> {
                Toast.makeText(appContext, "Liked " + match.name + "!!!!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
