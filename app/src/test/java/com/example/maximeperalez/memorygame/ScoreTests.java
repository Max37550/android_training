package com.example.maximeperalez.memorygame;

import com.example.maximeperalez.memorygame.model.Score;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by maxime.peralez on 06/04/2018.
 */

public class ScoreTests {

    @Test
    public void testGetNumberOfFlipsDone() {
        // Given
        int numberOfFlipsDone = 11;
        // When
        Score score = new Score(numberOfFlipsDone, 100);
        // Then
        Assert.assertEquals(score.getNumberOfFlipsDone(), numberOfFlipsDone);
    }

    @Test
    public void testGetGameDuration() {
        // Given
        long gameDuration = 75;
        // When
        Score score = new Score(2, gameDuration);
        // Then
        Assert.assertEquals(score.getGameDuration(), gameDuration);
    }

    @Test
    public void testGetRankingValue() {
        // Given
        int numberOfFlipsDone = 6;
        long gameDuration = 75;
        // When
        Score score = new Score(numberOfFlipsDone, gameDuration);
        // Then
        Assert.assertEquals(score.getRankingValue(), numberOfFlipsDone * gameDuration);
    }

    @Test
    public void testToString() {
        // Given
        int numberOfFlipsDone = 12;
        long gameDuration = 155;
        // When
        Score score = new Score(numberOfFlipsDone, gameDuration);
        // Then
        Assert.assertEquals(score.toString(), "You did 12 flips in 155 seconds");
    }

    @Test
    public void testCompareTo_Equal() {
        // Given
        Score otherScore = new Score(8, 110);
        // When
        Score score = new Score(8, 110);
        // Then
        Assert.assertEquals(0, score.compareTo(otherScore));
    }

    @Test
    public void testCompareTo_Superior() {
        // Given
        Score otherScore = new Score(8, 110);
        // When
        Score score = new Score(10, 110);
        // Then
        Assert.assertEquals(1, score.compareTo(otherScore));
    }

    @Test
    public void testCompareTo_Inferior() {
        // Given
        Score otherScore = new Score(8, 110);
        // When
        Score score = new Score(6, 110);
        // Then
        Assert.assertEquals(-1, score.compareTo(otherScore));
    }
}
