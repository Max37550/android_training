package com.example.maximeperalez.memorygame.data;

import android.provider.BaseColumns;

/**
 * Created by maxime.peralez on 05/04/2018.
 */

public class ScoreContract {

    public static final class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "score";
        public static final String COLUMN_NUMBER_OF_FLIPS = "numberOfFlips";
        public static final String COLUMN_GAME_DURATION = "gameDuration";
    }
}
