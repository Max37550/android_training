package com.example.maximeperalez.memorygame;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.os.Handler;

import com.example.maximeperalez.memorygame.managers.ImageDownloader;
import com.example.maximeperalez.memorygame.managers.ScoreDbManager;
import com.example.maximeperalez.memorygame.model.Board;
import com.example.maximeperalez.memorygame.model.Score;
import com.example.maximeperalez.memorygame.ui.BoardViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by maxime.peralez on 06/04/2018.
 */

public class BoardViewModelTests {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private ScoreDbManager scoreDbManager = mock(ScoreDbManager.class);

    @Mock
    private ImageDownloader imageDownloader = mock(ImageDownloader.class);

    @Mock
    private Board board = mock(Board.class);

    @Mock
    private Handler handler;

    @Before
    public void setUp() {
        handler = getMockHandler();
    }

    @Test
    public void testInitializationIsCorrect() {
        // When
        BoardViewModel sut = new BoardViewModel(imageDownloader, scoreDbManager);
        // Then
        Assert.assertEquals((long) sut.getNumberOfFlipsLiveData().getValue(), 0);
        Assert.assertFalse(sut.getIsGameDoneLiveData().getValue());
        Assert.assertTrue(sut.getIsUserInteractionEnabledLiveData().getValue());
    }

    @Test
    public void testBoardIsCreatedWhenImagesHaveBeenDownloaded() {
        // TODO
    }

    @Test
    public void testFetchImages() {
        // Given
        BoardViewModel sut = new BoardViewModel(imageDownloader, scoreDbManager);
        // When
        sut.fetchImages(8);
        // Then
        verify(imageDownloader, times(1)).downloadImages(8);
    }

    @Test
    public void testHandleInteractionWithCard_CorrectAnswer() {
        // Given
        when(board.isInteractionAllowed(anyInt())).thenReturn(true);
        BoardViewModel sut = initializeViewModel(board);
        // When
        sut.handleInteractionWithCard("TAG", 5, handler);
        sut.handleInteractionWithCard("TAG", 7, handler);
        // Then
        verify(board).removeCardFromBoard(5);
        verify(board).removeCardFromBoard(7);
        Assert.assertEquals((long) sut.getNumberOfFlipsLiveData().getValue(), 1);
    }

    @Test
    public void testHandleInteractionWithCard_WrongAnswer() {
        // Given
        when(board.isInteractionAllowed(anyInt())).thenReturn(true);
        BoardViewModel sut = initializeViewModel(board);
        // When
        sut.handleInteractionWithCard("TAG", 3, handler);
        sut.handleInteractionWithCard("DIFFERENT_TAG", 10, handler);

        // Then
        verify(board).updateCardVisiblity(3, true);
        verify(board).updateCardVisiblity(10, true);
    }

    @Test
    public void testHandleInteractionWithCard_NoPreviousSelection() {
        // Given
        when(board.isInteractionAllowed(2)).thenReturn(true);
        BoardViewModel sut = initializeViewModel(board);
        // When
        sut.handleInteractionWithCard("TAG", 2, handler);
        // Then
        verify(board).updateCardVisiblity(2, false);
    }

    @Test
    public void testHandleInteractionWithCardAlreadyFound() {
        // Given
        when(board.isInteractionAllowed(1)).thenReturn(false);
        BoardViewModel sut = initializeViewModel(board);
        // When
        sut.handleInteractionWithCard("TAG", 1, handler);
        // Then
        verify(board, never()).removeCardFromBoard(anyInt());
        verify(board, never()).updateCardVisiblity(anyInt(), anyBoolean());
    }

    @Test
    public void testHandleInteractionWithCard_TwiceTheSameCard() throws NoSuchFieldException {
        // Given
        when(board.isInteractionAllowed(1)).thenReturn(true);
        BoardViewModel sut = initializeViewModel(board);
        // When
        sut.handleInteractionWithCard("TAG", 1, handler);
        sut.handleInteractionWithCard("TAG", 1, handler);
        // Then
        verify(board, times(1)).updateCardVisiblity(anyInt(), anyBoolean());
    }

    @Test
    public void testHandleInteractionWithCard_GameDone() {
        // Given
        when(board.isInteractionAllowed(anyInt())).thenReturn(true);
        when(board.isGameDone()).thenReturn(true);
        BoardViewModel sut = initializeViewModel(board);
        // When
        sut.handleInteractionWithCard("TAG", 5, handler);
        sut.handleInteractionWithCard("TAG", 7, handler);
        // Then
        verify(scoreDbManager).storeNewScore(any(Score.class));
        Assert.assertTrue(sut.getIsGameDoneLiveData().getValue());
    }

    // MARK: - Private

    private BoardViewModel initializeViewModel(Board board) {
        BoardViewModel sut = new BoardViewModel(imageDownloader, scoreDbManager);
        sut.getBoardLiveData().setValue(board);
        return sut;
    }

    private Handler getMockHandler() {
        Handler handler = mock(Handler.class);
        when(handler.postDelayed(any(Runnable.class), anyLong())).thenAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        });
        return handler;
    }
}