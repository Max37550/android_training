package com.example.maximeperalez.memorygame;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maximeperalez.memorygame.data.ScoreContract;
import com.example.maximeperalez.memorygame.managers.ScoreDbManager;
import com.example.maximeperalez.memorygame.model.Score;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by maxime.peralez on 09/04/2018.
 */

@RunWith(RobolectricTestRunner.class)
public class ScoreDbManagerTests {

    private String tableName = ScoreContract.ScoreEntry.TABLE_NAME;
    private String numberOfFlipsColumn = ScoreContract.ScoreEntry.COLUMN_NUMBER_OF_FLIPS;
    private String gameDurationColumn = ScoreContract.ScoreEntry.COLUMN_GAME_DURATION;

    @Mock
    private SQLiteDatabase mockDb = mock(SQLiteDatabase.class);

    @InjectMocks
    private ScoreDbManager sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockDb.insert(any(), any(), any())).thenReturn((long) 1);
    }

    @Test
    public void testStoreNewScore() {
        // Given
        Score mockScore = mock(Score.class);
        when(mockScore.getNumberOfFlipsDone()).thenReturn(11);
        when(mockScore.getGameDuration()).thenReturn((long) 400);
        // When
        sut.storeNewScore(mockScore);
        // Then
        ArgumentCaptor<ContentValues> argumentCaptor = ArgumentCaptor.forClass(ContentValues.class);
        verify(mockDb, times(1)).insert(eq(tableName), any(), argumentCaptor.capture());
        ContentValues contentValues = argumentCaptor.getValue();
        Assert.assertEquals(11, contentValues.get(numberOfFlipsColumn));
        Assert.assertEquals((long) 400, contentValues.get(gameDurationColumn));
    }

    @Test
    public void testGetAllScores() {
        // Given
        Cursor mockCursor = mock(Cursor.class);
        when(mockDb.query(eq(tableName), any(), any(), any(), any(), any(), any())).thenReturn(mockCursor);
        when(mockCursor.moveToFirst()).thenReturn(true);
        when(mockCursor.isAfterLast()).thenReturn(false, false, false, true);
        when(mockCursor.moveToLast()).thenReturn(true);
        when(mockCursor.getColumnIndex(numberOfFlipsColumn)).thenReturn(1);
        when(mockCursor.getColumnIndex(gameDurationColumn)).thenReturn(2);
        when(mockCursor.getInt(1)).thenReturn(16);
        when(mockCursor.getInt(2)).thenReturn(320);
        // When
        ArrayList<Score> scores = sut.getAllScores();
        // Then
        Assert.assertEquals(3, scores.size());
        Assert.assertEquals(16, scores.get(0).getNumberOfFlipsDone());
        Assert.assertEquals(320, scores.get(0).getGameDuration());
    }
}
