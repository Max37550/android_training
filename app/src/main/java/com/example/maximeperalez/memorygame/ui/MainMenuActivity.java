package com.example.maximeperalez.memorygame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.maximeperalez.memorygame.R;
import com.example.maximeperalez.memorygame.enums.DifficultyLevel;

public class MainMenuActivity extends AppCompatActivity {

    // Outlets
    private Button mPlayButton;
    private Button mShowScoresButton;
    private RadioGroup mDifficultyLevelRadioGroup;
    private SeekBar mCustomBoardSlider;
    private TextView mCustomBoardTextView;

    // Constants
    public static final String EXTRA_DIFFICULTY_LEVEL = "DIFFICULTY_LEVEL_KEY";
    public static final String EXTRA_CUSTOM_BOARD_DIMENSION = "CUSTOM_BOARD_DIMENSION_KEY";
    private final int customBoardMinimumValue = 2;

    // State
    private DifficultyLevel difficultyLevel = DifficultyLevel.NORMAL;

    // MARK: - Life cycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get slider
        mCustomBoardTextView = findViewById(R.id.custom_board_text_view);
        mCustomBoardSlider = findViewById(R.id.custom_board_slider);
        updateSlider(6);
        mCustomBoardSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSlider(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Define play button main action
        mPlayButton = findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(view -> showBoard());

        // Set up show scores button
        mShowScoresButton = findViewById(R.id.scores_list_button);
        mShowScoresButton.setOnClickListener(view -> showListOfScores());

        // Set up radio buttons
        mDifficultyLevelRadioGroup = findViewById(R.id.radioGroup);

        // Add listener to radio group
        mDifficultyLevelRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.easy_radio_button:
                    difficultyLevel = DifficultyLevel.EASY;
                    break;
                case R.id.normal_radio_button:
                    difficultyLevel = DifficultyLevel.NORMAL;
                    break;
                case R.id.hard_radio_button:
                    difficultyLevel = DifficultyLevel.HARD;
                    break;
                case R.id.custom_radio_button:
                    difficultyLevel = DifficultyLevel.CUSTOM;
                    break;
            }
            updateCustomDifficultyElementsVisibility();
        });
    }

    // MARK: - Private

    private void showBoard() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY_LEVEL, difficultyLevel);
        if (difficultyLevel == DifficultyLevel.CUSTOM) {
            intent.putExtra(EXTRA_CUSTOM_BOARD_DIMENSION, mCustomBoardSlider.getProgress() + customBoardMinimumValue);
        }
        startActivity(intent);
    }

    private void showListOfScores() {
        Intent intent = new Intent(this, ScoresListActivity.class);
        startActivity(intent);
    }

    private void updateCustomDifficultyElementsVisibility() {
        if (difficultyLevel == DifficultyLevel.CUSTOM) {
            mCustomBoardSlider.setVisibility(View.VISIBLE);
            mCustomBoardTextView.setVisibility(View.VISIBLE);
        } else {
            mCustomBoardSlider.setVisibility(View.INVISIBLE);
            mCustomBoardTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void updateSlider(int progress) {
        progress = (Math.round(progress / 2) * 2);
        mCustomBoardSlider.setProgress(progress);
        int dimension = progress + customBoardMinimumValue;
        mCustomBoardTextView.setText(String.format("%d x %d", dimension, dimension));
    }
}
