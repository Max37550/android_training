package com.example.maximeperalez.memorygame.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.maximeperalez.memorygame.managers.ScoreDbManager;
import com.example.maximeperalez.memorygame.model.Score;

import java.util.ArrayList;

/**
 * Created by maxime.peralez on 05/04/2018.
 */

public class ScoresListViewModel extends ViewModel {

    // Managers
    private ScoreDbManager scoreDbManager;

    // Model
    private MutableLiveData<ArrayList<Score>> scoresLiveData = new MutableLiveData<>();

    // MARK: - Initialization

    public ScoresListViewModel(ScoreDbManager scoreDbManager) {
        this.scoreDbManager = scoreDbManager;
    }

    // MARK: - Getters & Setters

    public MutableLiveData<ArrayList<Score>> getScoresLiveData() {
        return scoresLiveData;
    }

    // MARK: - Public

    public void fetchScores() {
        ArrayList<Score> scores = scoreDbManager.getAllScores();
        scoresLiveData.setValue(scores);
    }
}
