package com.example.maximeperalez.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.maximeperalez.memorygame.enums.DifficultyLevel;

public class MainMenuActivity extends AppCompatActivity {

    // Outlets
    private Button mPlayButton;
    private RadioGroup mDifficultyLevelRadioGroup;

    // Constants
    public static final String EXTRA_DIFFICULTY_LEVEL = "DIFFICULTY_LEVEL_KEY";

    // State
    private DifficultyLevel difficultyLevel = DifficultyLevel.NORMAL;

    // MARK: - Life cycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define play button main action
        mPlayButton = findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(view -> showBoard());

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
            }
        });
    }

    // MARK: - Private

    private void showBoard() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY_LEVEL, difficultyLevel);
        startActivity(intent);
    }
}
