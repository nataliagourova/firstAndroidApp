package com.example.helloworldnataliasapplication;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Adapter used to show a simple grid of products.
 */

public class MatchesCardRecyclerViewAdapter extends RecyclerView.Adapter<MatchesCardViewHolder> {

    private static final double METERS_IN_MILE = 1609.34;
    private Consumer<MatchEntry> matchLikedListener;
    private Context appContext;
    private List<MatchEntry> fullMatchesList;
    private List<MatchEntry> filteredMatchesList;
    private ImageRequester imageRequester;
    private Location location;
    private double matchRadiusMeters;

    MatchesCardRecyclerViewAdapter(Context appContext) {
        this.appContext = appContext;
        this.fullMatchesList = new ArrayList<>();
        this.filteredMatchesList = new ArrayList<>();
        imageRequester = ImageRequester.getInstance(appContext);
        this.matchLikedListener = null;
    }

    public void setMatchLikedListener(Consumer<MatchEntry> callback) {
        this.matchLikedListener = callback;
    }

    public void setMatches(List<MatchEntry> matches) {
        this.fullMatchesList = new ArrayList<>(matches);
        this.filteredMatchesList = filterMatchesByRange(matches);
        notifyDataSetChanged();
    }

    private List<MatchEntry> filterMatchesByRange(List<MatchEntry> matches) {
        if (location == null) {
            return new ArrayList<>(matches);
        }
        return matches
                .stream()
                .filter(this::matchWithinRadius)
                .collect(Collectors.toList());
    }

    private boolean matchWithinRadius(MatchEntry match) {
        Location matchLocation = new Location("Match location");
        matchLocation.setLatitude(Double.parseDouble(match.lat));
        matchLocation.setLongitude(Double.parseDouble(match.longitude));
        return matchLocation.distanceTo(location) < matchRadiusMeters;
    }

    @NonNull
    @Override
    public MatchesCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_card, parent, false);
        return new MatchesCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesCardViewHolder holder, int position) {
        if (filteredMatchesList != null && position < filteredMatchesList.size()) {
            MatchEntry match = filteredMatchesList.get(position);
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
        return filteredMatchesList.size();
    }

    public void filterMatches(Location location, double matchRadiusMiles) {
        this.location = location;
        this.matchRadiusMeters = matchRadiusMiles * METERS_IN_MILE;
        this.filteredMatchesList = filterMatchesByRange(this.fullMatchesList);
        notifyDataSetChanged();
    }
}
