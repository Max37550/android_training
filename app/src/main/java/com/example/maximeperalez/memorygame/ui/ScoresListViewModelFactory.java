package com.example.maximeperalez.memorygame.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.maximeperalez.memorygame.managers.ScoreDbManager;

/**
 * Created by maxime.peralez on 06/04/2018.
 */

public class ScoresListViewModelFactory implements ViewModelProvider.Factory {

    private final ScoreDbManager scoreDbManager;

    ScoresListViewModelFactory(ScoreDbManager scoreDbManager) {
        this.scoreDbManager = scoreDbManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ScoresListViewModel(scoreDbManager);
    }
}