package com.example.maximeperalez.memorygame.managers;

import android.arch.lifecycle.MutableLiveData;

import com.example.maximeperalez.memorygame.model.FlickImage;

import java.util.ArrayList;

/**
 * Created by maxime.peralez on 04/04/2018.
 */

public interface ImageDownloader {

    // Data binding
    MutableLiveData<ArrayList<FlickImage>> getFlickImagesLiveData();

    // API caller
    void downloadImages(int numberOfImages);
}
