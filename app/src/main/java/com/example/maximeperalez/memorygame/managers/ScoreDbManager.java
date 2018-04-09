package com.example.maximeperalez.memorygame.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maximeperalez.memorygame.data.ScoreContract;
import com.example.maximeperalez.memorygame.model.Score;

import java.util.ArrayList;

/**
 * Created by maxime.peralez on 05/04/2018.
 */

public class ScoreDbManager {

    // Database
    private SQLiteDatabase mDb;

    // MARK: - Initialization

    public ScoreDbManager(SQLiteDatabase sqLiteDatabase) {
        this.mDb  = sqLiteDatabase;
    }

    // MARK: - Public

    public void storeNewScore(Score score) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ScoreContract.ScoreEntry.COLUMN_NUMBER_OF_FLIPS, score.getNumberOfFlipsDone());
        contentValues.put(ScoreContract.ScoreEntry.COLUMN_GAME_DURATION, score.getGameDuration());
        mDb.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, contentValues);
    }

    public ArrayList<Score> getAllScores() {
        Cursor cursor = mDb.query(ScoreContract.ScoreEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Score> scores = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // Create score object
            int numberOfFlips = cursor.getInt(cursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_NUMBER_OF_FLIPS));
            int gameDuration = cursor.getInt(cursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_GAME_DURATION));
            Score score = new Score(numberOfFlips, gameDuration);
            scores.add(score);
        }

        return scores;
    }
}
