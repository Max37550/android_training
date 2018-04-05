package com.example.maximeperalez.memorygame.model;

import android.graphics.Bitmap;

/**
 * Created by maxime.peralez on 03/04/2018.
 */

public class Card {

    // Attributes
    private String id;
    private Bitmap bitmap;
    private boolean isStillInGame = true;
    private boolean isHidden = true;

    // MARK: - Initialization

    Card(String id, Bitmap bitmap) {
        this.id = id;
        this.bitmap = bitmap;
    }

    // MARK: Getters & Setters

    public String getId() {
        return id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isStillInGame() {
        return isStillInGame;
    }

    public void setStillInGame(boolean stillInGame) {
        isStillInGame = stillInGame;
    }
}
