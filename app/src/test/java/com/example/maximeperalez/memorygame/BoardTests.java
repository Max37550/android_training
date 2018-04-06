package com.example.maximeperalez.memorygame;

import android.graphics.Bitmap;

import com.example.maximeperalez.memorygame.model.Board;
import com.example.maximeperalez.memorygame.model.Card;
import com.example.maximeperalez.memorygame.model.FlickImage;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by maxime.peralez on 06/04/2018.
 */

public class BoardTests {

    @Mock
    private Bitmap bitmap;

    @Test
    public void testCardsAreCreatedAtInitialization() {
        // Given
        int numberOfCards = 8;
        ArrayList<FlickImage> flickImages = createArrayOfFlickImages(numberOfCards);
        // When
        Board board = new Board(numberOfCards, flickImages);
        // Then
        Assert.assertEquals(board.getCards().size(), numberOfCards);
    }

    @Test
    public void testIsInteractionAllowed_True() {
        // Given
        Board board = initializeBoard(4);
        board.getCards().get(0).setStillInGame(true);
        // When
        boolean isInteractionAllowed = board.isInteractionAllowed(0);
        // Then
        Assert.assertTrue(isInteractionAllowed);
    }

    @Test
    public void testIsInteractionAllowed_False() {
        // Given
        Board board = initializeBoard(4);
        board.getCards().get(0).setStillInGame(false);
        // When
        boolean isInteractionAllowed = board.isInteractionAllowed(0);
        // Then
        Assert.assertFalse(isInteractionAllowed);
    }

    @Test
    public void testUpdateCardVisibility() {
        // Given
        Board board = initializeBoard(4);
        // When
        board.updateCardVisiblity(2, false);
        board.updateCardVisiblity(3, true);
        // Then
        Assert.assertFalse(board.getCards().get(2).isHidden());
        Assert.assertTrue(board.getCards().get(3).isHidden());
    }

    @Test
    public void testRemoveCardFromBoard() {
        // Given
        Board board = initializeBoard(4);
        // When
        board.removeCardFromBoard(3);
        // Then
        Assert.assertFalse(board.getCards().get(3).isStillInGame());
    }

    @Test
    public void testIsGameDone_True() {
        // Given
        Board board = initializeBoard(4);
        for (Card card : board.getCards()) {
            card.setStillInGame(false);
        }
        // When
        boolean isGameDone = board.isGameDone();
        // Then
        Assert.assertTrue(isGameDone);
    }

    @Test
    public void testIsGameDone_False() {
        // Given
        Board board = initializeBoard(4);
        // When
        boolean isGameDone = board.isGameDone();
        // Then
        Assert.assertFalse(isGameDone);
    }

    // MARK: - Private

    private Board initializeBoard(int numberOfCards) {
        ArrayList<FlickImage> flickImages = createArrayOfFlickImages(numberOfCards);
        return new Board(numberOfCards, flickImages);
    }

    private ArrayList<FlickImage> createArrayOfFlickImages(int numberOfImages) {
        ArrayList<FlickImage> flickImages = new ArrayList<>();
        for(int i = 0; i < numberOfImages; i++) {
            FlickImage flickImage = mock(FlickImage.class);
            when(flickImage.getBitmap()).thenReturn(bitmap);
            flickImages.add(flickImage);
        }
        return flickImages;
    }
}
