package com.kclls.github;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardTest {
    Board board;

    @BeforeEach
    public void setUp (){
      board = new Board(5);
      // The board contains size^2/10 = 2 mines (as has a size of 5)
      // The board looks like so:
      // M 2 M 1 0
      // 1 2 1 1 0
      // 0 0 0 0 0
      // 0 0 0 0 0
      // 0 0 0 0 0
      //
      board.placeMine(0, 0);
      board.placeMine(0, 2);
      // Do not update numbers, some tests test this
      // board.updateBoardNumbers();
    }

    @Test
    public void boardIsCreatedToCorrectSize(){
        int size = 5;
        Board b = new Board(size);
        Assertions.assertEquals(size*size, b.squaresRemaining(), "Must have size^2 squares on the board");
    }

    @Test
    public void updateBoardNumbersDoesNotChangeSquaresNotNextToMines() {
      int prevVal = board.getMinesAround(4, 0);
      Assertions.assertEquals(0, prevVal, "Mines around should initially be zero");
      board.updateBoardNumbers();
      int newVal = board.getMinesAround(4, 0);
      Assertions.assertEquals(newVal, prevVal, "Mines around should not change");
    }

    @Test
    public void updateBoardNumbersIncrementsSquareNextToMines() {
      int prevVal = board.getMinesAround(1, 0);
      Assertions.assertEquals(0, prevVal, "Mines around should initially be zero");
      board.updateBoardNumbers();
      int newVal = board.getMinesAround(1, 0);
      Assertions.assertEquals(newVal, prevVal+1, "Mines around should increment");
    }

    @Test
    public void updateBoardNumbersIncrementsTwiceBetweenTwoMines() {
      int prevVal = board.getMinesAround(0, 1);
      Assertions.assertEquals(0, prevVal, "Mines around should initially be zero");
      board.updateBoardNumbers();
      int newVal = board.getMinesAround(0, 1);
      Assertions.assertEquals(newVal, prevVal+2, "Mines around should increase to 2");
    }

    @Test
    public void squaresShouldNotBeRevealedInitially() {
      boolean before = board.getRevealed(2,0);
      Assertions.assertFalse(before, "Square should not be revealed right after instantiation");
    }

    @Test
    public void revealSquaresRevealsTheSquare() {
      boolean before = board.getRevealed(2,0);
      Assertions.assertFalse(before, "Square should not be revealed before calling the method");
      board.revealSquares(2, 0);
      boolean after = board.getRevealed(2,0);
      Assertions.assertTrue(after, "Square should be revealed after calling the method");
    }
    @Test
    public void revealSquaresCascadesOnZero() {
      // Reveal should cascade all the way down the row of zeros
      boolean revealStart = board.getRevealed(3,0);
      boolean revealEnd = board.getRevealed(3,4);
      Assertions.assertFalse(
                revealStart && revealEnd,
                "Squares should not be revealed before calling the method"
      );
      board.revealSquares(3, 0);
      revealStart = board.getRevealed(3,0);
      revealEnd = board.getRevealed(3,4);
      Assertions.assertTrue(
                revealStart && revealEnd,
                "Square should be revealed after calling the method"
      );
    }
    
    @Test
    public void revealSquaresCascadesOntoAdjacentNumbers() {
      // Initialise board
      board.updateBoardNumbers();
      // Check starting zero and number next to it
      boolean startingZero = board.getRevealed(2,0);
      boolean adjacentNumber = board.getRevealed(1,0);
      Assertions.assertFalse(
                startingZero && adjacentNumber,
                "Squares should not be revealed before calling the method"
      );
      board.revealSquares(2, 0);
      startingZero = board.getRevealed(2,0);
      adjacentNumber = board.getRevealed(1,0);
      Assertions.assertTrue(
                startingZero && adjacentNumber,
                "Squares should be revealed after calling the method"
      );
    }

    @Test
    public void cascadingRevealDoesNotRevealMines() {
      // Initialise board
      board.updateBoardNumbers();
      // Check starting reveal against adjacent mine
      boolean startingReveal = board.getRevealed(1,0);
      boolean adjacentMine = board.getRevealed(0,0);
      Assertions.assertFalse(
                startingReveal && adjacentMine,
                "Squares should not be revealed before calling the method"
      );
      board.revealSquares(1, 0);
      startingReveal = board.getRevealed(1,0);
      adjacentMine = board.getRevealed(0,0);
      Assertions.assertTrue(startingReveal, "Initial square should be revealed");
      Assertions.assertFalse(adjacentMine, "Adjacent mine should not be revealed");
      
    }

    @Test
    public void revealSquaresDoesNotCascadeOntoNonMineFlaggedSquares() {
      // Initialise board
      board.updateBoardNumbers();
      // Check starting reveal against flagged square
      boolean startingReveal = board.getRevealed(1,0);
      board.flagSquare(2,0);
      boolean flaggedSquare = board.getRevealed(2,0);
      Assertions.assertFalse(
                startingReveal && flaggedSquare,
                "Squares should not be revealed before calling the method"
      );
      board.revealSquares(1, 0);
      startingReveal = board.getRevealed(1,0);
      flaggedSquare = board.getRevealed(2,0);
      Assertions.assertTrue(startingReveal, "Initial square should be revealed");
      Assertions.assertFalse(flaggedSquare, "Flagged square should not be revealed");
    }

}
