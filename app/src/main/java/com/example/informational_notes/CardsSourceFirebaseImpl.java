package com.example.informational_notes;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CardsSourceFirebaseImpl implements CardSource {

    public static final String COLLECTION = "CARDS";
    public static final String TAG = "CardsSourceFirebaseImpl";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collection = db.collection(COLLECTION);
    private List<CardData> cards = new ArrayList<>();

    @Override
    public CardSource init(CardSourceResponse response) {

        collection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cards = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CardData data = document.toObject(CardData.class);
                            data.setId(document.getId());
                            cards.add(data);
                        }
                        response.initialized(this);
                        Log.d(TAG, "success" + cards.size());
                    } else {
                        Log.e(TAG, "failed", task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "failed", e);
                });

        return this;
    }

    @Override
    public CardData getCardData(int position) {
        return cards.get(position);
    }

    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public void deleteCardData(int position) {
        collection.document(cards.get(position).getId()).delete();
        cards.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        collection.document(cardData.getId()).set(cardData);
        cards.set(position, cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        collection.add(cardData).addOnSuccessListener(documentReference ->
                cardData.setId(documentReference.getId()));
        cards.add(cardData);
    }

    @Override
    public void clearCardData() {
        for (CardData cardData : cards) {
            collection.document(cardData.getId()).delete();
        }
        cards.clear();
    }
}
