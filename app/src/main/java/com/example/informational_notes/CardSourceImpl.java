package com.example.informational_notes;

import android.content.Context;

public class CardSourceImpl implements CardSource {

    private final Context context;
    private CardData[] cards;


    public CardSourceImpl(Context context) {
        this.context = context;
        cards = new CardData[]{
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
                )
        };
    }


        @Override
        public CardData getCardData ( int position){
            return cards[position];
        }

        @Override
        public int size () {
            return cards.length;
        }
    }
