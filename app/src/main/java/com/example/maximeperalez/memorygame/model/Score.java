package com.example.maximeperalez.memorygame.model;

import android.support.annotation.NonNull;

/**
 * Created by maxime.peralez on 05/04/2018.
 */

public class Score implements Comparable {

    // Attributes
    private int numberOfFlipsDone;
    private long gameDuration; // in seconds
    private int rankingValue;

    // MARK: - Initialization

    public Score(int numberOfFlipsDone, long gameDuration) {
        this.numberOfFlipsDone = numberOfFlipsDone;
        this.gameDuration = gameDuration;

        //TODO: The ranking value should be determined based on the number of flips done in the given duration
        rankingValue = (int) gameDuration * numberOfFlipsDone;
    }

    // MARK: - Getters & Setters

    public int getNumberOfFlipsDone() {
        return numberOfFlipsDone;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public int getRankingValue() {
        return rankingValue;
    }

    // MARK: - Overridden methods

    @Override
    public String toString() {
        return "You did " + String.valueOf(numberOfFlipsDone)
                + " flips in " + String.valueOf(gameDuration) + " seconds";
    }

    // MARK: - Comparable

    @Override
    public int compareTo(@NonNull Object o) {
        int otherRankingValue = ((Score) o).rankingValue;
        if (rankingValue < otherRankingValue)
            return -1;
        else if (rankingValue > otherRankingValue)
            return 1;
        else return 0;
    }
}
