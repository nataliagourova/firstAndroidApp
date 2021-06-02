package com.example.helloworldnataliasapplication.viewmodels;

import com.example.helloworldnataliasapplication.MatchEntry;
import com.example.helloworldnataliasapplication.datamodels.FirebaseMatchesModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FirebaseMatchesViewModel {

    private FirebaseMatchesModel matchesModel;

    public FirebaseMatchesViewModel() {
        matchesModel = new FirebaseMatchesModel();
    }

    public void saveMatchLike(MatchEntry match) {
        matchesModel.saveMatchLike(match);
    }

    public void getMatches(Consumer<ArrayList<MatchEntry>> responseCallback) {
        matchesModel.getMatches(
                (QuerySnapshot querySnapshot) -> {
                    if (querySnapshot == null) {
                        return;
                    }

                    ArrayList<MatchEntry> matchItems = new ArrayList<>();
                    for (DocumentSnapshot matchSnapshot : querySnapshot.getDocuments()) {
                        MatchEntry match = matchSnapshot.toObject(MatchEntry.class);
                        assert match != null;
                        match.uid = matchSnapshot.getId();
                        matchItems.add(match);
                    }
                    responseCallback.accept(matchItems);
                },
                (databaseError -> System.out.println("Error reading Todo Items: " + databaseError))
        );
    }

    public void stopListeners() {
        matchesModel.stopListeners();
    }
}
