package com.example.informational_notes;

public interface CardSource {

    CardSource init (CardSourceResponse response);

    CardData getCardData(int position);

    int size();

    void deleteCardData(int position);

    void updateCardData(int position, CardData cardData);

    void addCardData(CardData cardData);

    void clearCardData();
}
