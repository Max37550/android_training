package com.example.maximeperalez.memorygame.enums;

/**
 * Created by maxime.peralez on 05/04/2018.
 */

public enum DifficultyLevel {
    EASY, NORMAL, HARD;

    public int boardDimension() {
        switch (this) {
            case EASY: return 2;
            case NORMAL: return 4;
            case HARD: return 6;
            default: return 4;
        }
    }
}
