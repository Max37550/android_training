package com.example.maximeperalez.memorygame.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.maximeperalez.memorygame.managers.ScoreDbManager;
import com.example.maximeperalez.memorygame.model.Score;

import java.util.ArrayList;

/**
 * Created by maxime.peralez on 05/04/2018.
 */

public class ScoresListViewModel extends AndroidViewModel {

    // Managers
    private ScoreDbManager scoreDbManager;

    // Model
    MutableLiveData<ArrayList<Score>> scoresLiveData = new MutableLiveData<>();

    // MARK: - Initialization

    ScoresListViewModel(Application application) {
        super(application);

        // TODO: Dependency injection
        scoreDbManager = new ScoreDbManager(application);
    }

    // MARK: - Public

    public void fetchScores() {
        ArrayList<Score> scores = scoreDbManager.getAllScores();
        scoresLiveData.setValue(scores);
    }
}
