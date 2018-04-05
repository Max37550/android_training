package com.example.maximeperalez.memorygame;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.GridView;

import com.example.maximeperalez.memorygame.adapters.ImageAdapter;
import com.example.maximeperalez.memorygame.enums.DifficultyLevel;
import com.example.maximeperalez.memorygame.viewmodel.BoardViewModel;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    // Outlets
    private GridView mGridView;
    private ProgressDialog mProgressDialog;

    // View model
    private BoardViewModel mViewModel;

    // GridView State
    private static int mNumberOfRows;
    private static int mNumberOfColumns;
    private static int mNumberOfItems;

    // Timer
    private long numberOfSeconds = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            numberOfSeconds += 1;
            int minutes = (int) numberOfSeconds / 60;

            String formattedTime = String.format("%d:%02d", minutes, numberOfSeconds % 60);
            getSupportActionBar().setSubtitle(String.valueOf(formattedTime));

            timerHandler.postDelayed(this, 1000);
        }
    };

    // MARK: - Life cycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // Get difficulty level and initialize attributes needed by the game
        initializeGridViewAttributes();

        // Initialize view model
        mViewModel = ViewModelProviders.of(this).get(BoardViewModel.class);

        // Data binding
        mViewModel.getBoardLiveData().observe(this, board -> {
            updateBoard();
        });
        mViewModel.getNumberOfFlipsLiveData().observe(this, numberOfFlips -> {
            showScore();
        });
        mViewModel.getIsUserInteractionEnabledLiveData().observe(this, isUserInteractionEnabled -> {
            if (isUserInteractionEnabled) {
                enableUserInteractions();
            } else disableUserInteractions();
        });
        mViewModel.getIsGameDoneLiveData().observe(this, isGameDone -> {
            if (isGameDone)
                finalizeGame();
        });
        mViewModel.getFlickImages().observe(this, flickImages -> {
            startGame();
        });

        // Set up GridView
        mGridView = findViewById(R.id.gridview);
        mGridView.setNumColumns(mNumberOfColumns);
        mGridView.post(() -> fetchImages());

        // Handle interaction with items
        mGridView.setOnItemClickListener((parent, v, position, id) -> {
            String newTag = (String) v.getTag();
            mViewModel.handleInteractionWithCard(newTag, position);
        });
    }

    // MARK: - Private

    private void initializeGridViewAttributes() {
        DifficultyLevel difficultyLevel = (DifficultyLevel) getIntent().getSerializableExtra(MainMenuActivity.EXTRA_DIFFICULTY_LEVEL);
        int boardDimension = difficultyLevel.boardDimension();
        mNumberOfItems = boardDimension * boardDimension;
        mNumberOfRows = boardDimension;
        mNumberOfColumns = boardDimension;
    }

    private void startGame() {
        hideLoadingScreen();
        // Set up board
        updateBoard();
        // Start timer
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private void updateBoard() {
        mGridView.setAdapter(new ImageAdapter(this, mViewModel.getBoardLiveData().getValue(), getItemWidth(), getItemHeight()));
        mGridView.invalidateViews();
    }

    private void showScore() {
        String formattedScore = String.format(getString(R.string.flips_counter), mViewModel.getNumberOfFlipsLiveData().getValue());
        getSupportActionBar().setTitle(formattedScore);
    }

    private void finalizeGame() {
        onBackPressed();
        // TODO: Show popup with score or something
    }

    private void fetchImages() {
        showLoadingScreen();
        mViewModel.fetchImages(mNumberOfItems);
    }

    private void showLoadingScreen() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading");
        mProgressDialog.setMessage("Preparing board...");
        mProgressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
        mProgressDialog.show();
    }

    private void hideLoadingScreen() {
        mProgressDialog.dismiss();
    }

    private int getItemWidth() {
        return mGridView.getWidth() / mNumberOfColumns;
    }

    private int getItemHeight() {
        return (mGridView.getMeasuredHeight() / mNumberOfRows);
    }

    // TODO: Find a better way to enable/disable user interactions.

    private void disableUserInteractions() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void enableUserInteractions() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
