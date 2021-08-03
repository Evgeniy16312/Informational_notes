package com.example.informational_notes;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardSourceImpl implements CardSource {

    private List<CardData> cards;

    public CardSourceImpl(Context context) {
        cards = new ArrayList<>(Arrays.asList(
                new CardData(
                        context.getResources().getString(R.string.title1),
                        context.getResources().getString(R.string.description1),
                        R.drawable.karakurt_spider,
                        false
                ),
                new CardData(
                        context.getResources().getString(R.string.title2),
                        context.getResources().getString(R.string.description2),
                        R.drawable.red_backed_spider,
                        false
                ),
                new CardData(
                        context.getResources().getString(R.string.title3),
                        context.getResources().getString(R.string.description3),
                        R.drawable.sidney_spider,
                        false
                ),
                new CardData(
                        context.getResources().getString(R.string.title4),
                        context.getResources().getString(R.string.description4),
                        R.drawable.sand_spider,
                        false
                ),
                new CardData(
                        context.getResources().getString(R.string.title5),
                        context.getResources().getString(R.string.description5),
                        R.drawable.brasil_spider,
                        false
                )));
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
        cards.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        cards.set(position, cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        cards.add(cardData);
    }

    @Override
    public void clearCardData() {
        cards.clear();
    }
}
