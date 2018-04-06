package com.example.maximeperalez.memorygame.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.maximeperalez.memorygame.managers.ImageDownloader;
import com.example.maximeperalez.memorygame.managers.ScoreDbManager;

/**
 * Created by maxime.peralez on 06/04/2018.
 */

public class BoardViewModelFactory implements ViewModelProvider.Factory {

    private final ImageDownloader imageDownloader;
    private final ScoreDbManager scoreDbManager;

    BoardViewModelFactory(ImageDownloader imageDownloader, ScoreDbManager scoreDbManager) {
        this.imageDownloader = imageDownloader;
        this.scoreDbManager = scoreDbManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BoardViewModel(imageDownloader, scoreDbManager);
    }
}
