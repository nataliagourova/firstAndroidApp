package com.example.helloworldnataliasapplication.datamodels;

import com.example.helloworldnataliasapplication.MatchEntry;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FirebaseMatchesModel {

    private FirebaseFirestore db;
    private List<ListenerRegistration> listeners;

    public FirebaseMatchesModel() {
        db = FirebaseFirestore.getInstance();
        listeners = new ArrayList<>();
    }

    public void getMatches(
            Consumer<QuerySnapshot> dataChangedCallback,
            Consumer<FirebaseFirestoreException> dataErrorCallback) {

        listeners.add(
            db.collection("matches")
              .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        dataErrorCallback.accept(e);
                    }

                    dataChangedCallback.accept(queryDocumentSnapshots);
                }));
    }

    public void saveMatchLike(MatchEntry item) {
        DocumentReference matchDocRef = db.collection("matches").document(item.uid);
        Map<String, Object> data = new HashMap<>();
        data.put("like", item.liked);
        matchDocRef.update(data);
    }

    public void stopListeners() {
        // Clear all the listeners onPause
        listeners.forEach(ListenerRegistration::remove);
    }
}
