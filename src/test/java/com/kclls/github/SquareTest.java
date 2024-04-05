package com.kclls.github;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SquareTest {
    Square square;
    @BeforeEach
    public void setUp (){
      square = new Square();
    }

    @Test
    public void squareInitiallyIsNotMine(){
        Assertions.assertFalse(square.getMine(), "Square must not be a mine initially");
    }

    @Test
    public void squareInitiallyIsNotFlagged(){
        Assertions.assertFalse(square.getFlagged(), "Square must not flagged initially");
    }

    @Test
    public void squareInitiallyIsNotRevealed(){
        Assertions.assertFalse(square.getRevealed(), "Square must not be revealed initially");
    }

    @Test
    public void squareInitiallyHasNoMinesAround(){
        Assertions.assertEquals(square.getMinesAround(), 0, "Square must not have mines around initially");
    }

    @Test
    public void revealSquareRevealsSquare(){
        square.revealSquare();
        Assertions.assertTrue(square.getRevealed(), "Square must be revealed after call");
    }

    @Test
    public void setMinesAroundSetsMines(){
        square.setMinesAround(3);
        Assertions.assertEquals(square.getMinesAround(), 3, "Square must have three mines around");
    }

    @Test
    public void setMineSetsMine(){
        square.setMine(true);
        Assertions.assertTrue(square.getMine(), "Square must contain a mine");
    }

    @Test
    public void toggleFlagTogglesFlag(){
        square.toggleFlag();
        Assertions.assertTrue(square.getFlagged(), "Square must be toggled to true");

        square.toggleFlag();
        Assertions.assertFalse(square.getFlagged(), "Square must be toggled to false");
    }

    @Test
    public void getVisibleReturnsFForFlagged() {
      square.toggleFlag();
      Assertions.assertTrue("F".equals(square.getVisible()), "Visible must return F");
    }

    @Test
    public void getVisibleReturnsXIfNotRevealed() {
      Assertions.assertTrue("X".equals(square.getVisible()), "Visible must return X");
    }

    @Test
    public void getVisibleReturnsXForMine() {
      square.setMine(true);
      Assertions.assertTrue("X".equals(square.getVisible()), "Visible must return X");
    }

    @Test
    public void getVisibleReturnsMinesAroundIfRevealed() {
      square.revealSquare();
      square.setMinesAround(3);
      Assertions.assertTrue("3".equals(square.getVisible()), "Visible must return the number of mines around");
    }


    @Test
    public void getDebuggingVisibleReturnsFForFlagged() {
      square.toggleFlag();
      Assertions.assertTrue("F".equals(square.getDebuggingVisible()), "Debugging visible must return F");
    }

    @Test
    public void getDebuggingVisibleReturnsMForMineIfNotRevealed() {
      square.setMine(true);
      Assertions.assertTrue("M".equals(square.getDebuggingVisible()), "Debugging visible must return M");
    }

    @Test
    public void getDebuggingVisibleReturnsMForMineIfRevealed() {
      square.setMine(true);
      Assertions.assertTrue("M".equals(square.getDebuggingVisible()), "Debugging visible must return M");
    }

    @Test
    public void getDebuggingVisibleReturnsMinesAroundIfNotRevealed() {
      square.setMinesAround(3);
      Assertions.assertTrue("3".equals(square.getDebuggingVisible()), "Debugging visible must return the number of mines around");
    }

    @Test
    public void getDebuggingVisibleReturnsMinesAroundIfRevealed() {
      square.setMinesAround(3);
      Assertions.assertTrue("3".equals(square.getDebuggingVisible()), "Debugging visible must return the number of mines around");
    }
}
