package com.example.helloworldnataliasapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Adapter used to show a simple grid of products.
 */

public class MatchesCardRecyclerViewAdapter extends RecyclerView.Adapter<MatchesCardViewHolder> {

    private Consumer<MatchEntry> matchLikedListener;
    private Context appContext;
    private List<MatchEntry> matchesList;
    private ImageRequester imageRequester;

    MatchesCardRecyclerViewAdapter(Context appContext) {
        this.appContext = appContext;
        this.matchesList = new ArrayList<>();
        imageRequester = ImageRequester.getInstance(appContext);
        this.matchLikedListener = null;
    }

    public void setMatchLikedListener(Consumer<MatchEntry> callback) {
        this.matchLikedListener = callback;
    }

    public void setMatches(List<MatchEntry> matches) {
        this.matchesList = new ArrayList<>(matches);
        notifyDataSetChanged();
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
            imageRequester.setImageFromUrl(holder.matchesImage, match.imageUrl);
            int likeButtonImage =
                    match.liked
                            ? R.drawable.ic_baseline_favorite_24
                            : R.drawable.ic_baseline_favorite_border_24;
            holder.likeButton.setImageResource(likeButtonImage);
            holder.likeButton.setOnClickListener(v -> {

                if (this.matchLikedListener == null) {
                    Toast.makeText(appContext,
                            "Listener not set, liked " + match.name + "!!!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                match.liked = !match.liked;
                int likeImage =
                        match.liked
                                ? R.drawable.ic_baseline_favorite_24
                                : R.drawable.ic_baseline_favorite_border_24;
                holder.likeButton.setImageResource(likeImage);
                this.matchLikedListener.accept(match);
            });
        }
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
