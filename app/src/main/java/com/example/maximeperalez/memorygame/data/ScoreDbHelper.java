package com.example.maximeperalez.memorygame.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maxime.peralez on 05/04/2018.
 */

public class ScoreDbHelper extends SQLiteOpenHelper {

    // Constants
    private static final String DATABASE_NAME = "score.db";
    private static final int DATABASE_VERSION = 1;

    // MARK: - Initialization

    public ScoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // MARK: - Overridden methods

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SCORE_TABLE = "CREATE TABLE " + ScoreContract.ScoreEntry.TABLE_NAME + " ("
                + ScoreContract.ScoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ScoreContract.ScoreEntry.COLUMN_NUMBER_OF_FLIPS + " INTEGER NOT NULL, "
                + ScoreContract.ScoreEntry.COLUMN_GAME_DURATION + " INTEGER NOT NULL"
                + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScoreContract.ScoreEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
