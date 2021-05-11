package com.example.helloworldnataliasapplication;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

public class MatchesCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView matchesImage;
    public TextView matchesName;
    public TextView matchesAge;
    public ImageButton likeButton;

    public MatchesCardViewHolder(@NonNull View itemView) {
        super(itemView);
        matchesImage = itemView.findViewById(R.id.matches_image);
        matchesName = itemView.findViewById(R.id.matches_name);
        matchesAge = itemView.findViewById(R.id.matches_age);
        likeButton = itemView.findViewById(R.id.like_button);
    }
}


