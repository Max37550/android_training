package com.example.maximeperalez.memorygame.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.Handler;

import com.example.maximeperalez.memorygame.managers.FlickManager;
import com.example.maximeperalez.memorygame.managers.ImageDownloader;
import com.example.maximeperalez.memorygame.managers.ScoreDbManager;
import com.example.maximeperalez.memorygame.model.Board;
import com.example.maximeperalez.memorygame.model.FlickImage;
import com.example.maximeperalez.memorygame.model.Score;

import java.util.ArrayList;

/**
 * Created by maxime.peralez on 04/04/2018.
 */

public class BoardViewModel extends AndroidViewModel {

    // Managers
    private final ImageDownloader imageDownloader;
    private final ScoreDbManager scoreDbManager;

    // Data Binding
    private final MutableLiveData<Board> boardLiveData = new MutableLiveData<>();
    private final LiveData<ArrayList<FlickImage>> flickImagesLiveData;

    // State
    private int mNumberOfItems = 0;
    private final MutableLiveData<Boolean> isUserInteractionEnabledLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGameDoneLiveData = new MutableLiveData<>();

    // Game attributes
    private int previousSelectedCardPosition = -1;
    private String previousSelectedTag = null;

    // Score attributes
    private final MutableLiveData<Integer> numberOfFlipsLiveData = new MutableLiveData<>();
    private long gameDuration = 0;

    // MARK: - Initialization

    public BoardViewModel(Application application) {
        super(application);

        // TODO: Dependency Injection
        imageDownloader = new FlickManager();
        scoreDbManager = new ScoreDbManager(application);

        // Starting score
        numberOfFlipsLiveData.setValue(0);
        // Default values
        isGameDoneLiveData.setValue(false);
        isUserInteractionEnabledLiveData.setValue(true);

        // Data binding
        flickImagesLiveData = Transformations.map(imageDownloader.getFlickImagesLiveData(), flickImages -> {
            Board board = new Board(mNumberOfItems, flickImages);
            boardLiveData.setValue(board);
            return flickImages;
        });
    }

    // MARK: - Setters & Getters

    public MutableLiveData<Board> getBoardLiveData() {
        return boardLiveData;
    }

    public MutableLiveData<Boolean> getIsUserInteractionEnabledLiveData() {
        return isUserInteractionEnabledLiveData;
    }

    public MutableLiveData<Integer> getNumberOfFlipsLiveData() {
        return numberOfFlipsLiveData;
    }

    public MutableLiveData<Boolean> getIsGameDoneLiveData() {
        return isGameDoneLiveData;
    }

    public LiveData<ArrayList<FlickImage>> getFlickImages() {
        return flickImagesLiveData;
    }

    public void setGameDuration(long gameDuration) {
        this.gameDuration = gameDuration;
    }

    // MARK: - Services

    public void fetchImages(int numberOfImages) {
        mNumberOfItems = numberOfImages;
        imageDownloader.downloadImages(numberOfImages);
    }

    // MARK: - Game logic

    public void handleInteractionWithCard(final String tag, final int position) {
        Board board = boardLiveData.getValue();

        if (!board.isInteractionAllowed(position) || position == previousSelectedCardPosition) {
            return;
        }

        if (previousSelectedTag == null && previousSelectedCardPosition == -1) {
            // Store the attributes of the card taps by the user
            previousSelectedTag = tag;
            previousSelectedCardPosition = position;
            // Update UI
            board.updateCardVisiblity(position, false);
            boardLiveData.setValue(board);
            // Exit
            return;
        }

        // Show both cards during like 2 sec
        isUserInteractionEnabledLiveData.setValue(false);
        board.updateCardVisiblity(position, false);
        boardLiveData.setValue(board);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            resetBoard(tag, position);
            isUserInteractionEnabledLiveData.setValue(true);
        }, 2000);
    }

    private void resetBoard(String tag, int position) {
        Board board = boardLiveData.getValue();

        // Determine if the user taps two cards with the same pictures
        if (previousSelectedTag == tag) {
            // Correct answer
            board.removeCardFromBoard(position);
            board.removeCardFromBoard(previousSelectedCardPosition);
            increaseScore();
        } else {
            // Wrong answer
            board.updateCardVisiblity(position, true);
            board.updateCardVisiblity(previousSelectedCardPosition, true);
        }

        previousSelectedTag = null;
        previousSelectedCardPosition = -1;
        boardLiveData.setValue(board);

        if (board.isGameDone()) finalizeGame();
    }

    private void finalizeGame() {
        // Store score
        Score score = new Score(numberOfFlipsLiveData.getValue(), gameDuration);
        scoreDbManager.storeNewScore(score);
        // Notify observers that the game is done
        isGameDoneLiveData.setValue(true);
    }

    private void increaseScore() {
        int oldNumberOfFlips = numberOfFlipsLiveData.getValue();
        numberOfFlipsLiveData.setValue(oldNumberOfFlips + 1);
    }
}