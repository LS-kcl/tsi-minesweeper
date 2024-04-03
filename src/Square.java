public class Square {
  boolean flagged;
  boolean isMine;
  boolean revealed;
  int minesAround;

  public Square() {
    // Initialise to defaults
    flagged = false;
    isMine = false;
    revealed = false;
    minesAround = 0;
  }

  public void toggleFlag() {
    flagged = !flagged;
  }

  /*
   * Reveals a square and returns if it was a mine
   */
  public boolean checkSquare() {
    // Do not reveal if flagged
    if (flagged) {
      return false;
    }
    revealed = true;
    return isMine;
  }

  public boolean getMine() {
    return isMine;
  }

  public void setMine(boolean value) {
    isMine = value;
  }
}
