package com.example.maximeperalez.memorygame;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.maximeperalez.memorygame.managers.ScoreDbManager;
import com.example.maximeperalez.memorygame.model.Score;
import com.example.maximeperalez.memorygame.ui.ScoresListViewModel;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by maxime.peralez on 06/04/2018.
 */

public class ScoresListViewModelTests {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private ScoreDbManager scoreDbManager = mock(ScoreDbManager.class);

    @Test
    public void testFetchScores() {
        // Given
        ArrayList<Score> scores = createArrayOfScores(4);
        when(scoreDbManager.getAllScores()).thenReturn(scores);
        // When
        ScoresListViewModel sut = new ScoresListViewModel(scoreDbManager);
        sut.fetchScores();
        // Then
        Assert.assertEquals(sut.getScoresLiveData().getValue(), scores);
        verify(scoreDbManager).getAllScores();
    }

    // MARK: - Private

    private ArrayList<Score> createArrayOfScores(int numberOfScores) {
        ArrayList<Score> scores = new ArrayList<>();
        for(int i = 0; i <= numberOfScores; i++) {
            scores.add(mock(Score.class));
        }
        return scores;
    }
}
