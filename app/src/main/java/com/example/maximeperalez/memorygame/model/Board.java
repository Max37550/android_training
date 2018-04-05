package com.example.maximeperalez.memorygame.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

/**
 * Created by maxime.peralez on 03/04/2018.
 */

public class Board {

    // Attributes
    private ArrayList<Card> cards;

    // MARK: - Initialization

    public Board(int numberOfCards, ArrayList<FlickImage> flickImages) {
        cards = new ArrayList<>();
        for (int i = 0; i < (numberOfCards / 2); i++) {
            Bitmap bitmap = flickImages.get(i).getBitmap();
            String id = UUID.randomUUID().toString();
            cards.add(new Card(id, bitmap));
            cards.add(new Card(id, bitmap));
        }
        shuffleCards();
    }

    // MARK: - Getters & Setters

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    // MARK: - Public

    public boolean isInteractionAllowed(int position) {
        return cards.get(position).isStillInGame();
    }

    public void updateCardVisiblity(int position, boolean isHidden) {
        cards.get(position).setHidden(isHidden);
    }

    public void removeCardFromBoard(int position) {
        cards.get(position).setStillInGame(false);
    }

    public boolean isGameDone() {
        for (Card card : cards) {
            if (card.isStillInGame()) return false;
        }
        return true;
    }

    public void storeBoard() {
        // TODO
    }

    // MARK: - Private

    private void shuffleCards() {
        long seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));
    }
}
